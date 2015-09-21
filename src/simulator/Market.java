package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.balance.PeerInfo;

public class Market {
	
	private Simulator simulator;
	
	public Market(Simulator simulator){
		this.simulator = simulator;
	}
	
	public void performDonationToPeersWithTransitiveCredit(Collaborator provider, List<Peer> peersInvolvedInTheIndirectCredit) {
		
		//peersInvolvedInTheIndirectCredit	//the first peer is the "transitive one"/idle and the second is the consumer
		
		//assume peer A is the consumer, B is idle, and C is the provider
		
		
//		double donated = performDonation(provider, peersInvolvedInTheIndirectCredit.get(0));
		
			
	}
	
	//the provider should donate the amount specified in resources, but only when resource >0	
	public double performDonation(Collaborator provider, List<Peer> peersInvolvedInTheIndirectCredit){	
		
		double resources = getAmountToDonate(provider, peersInvolvedInTheIndirectCredit, -1);
		
		//
		
		//we already know the interactions exists				
		double donated = updatePeersInteraction(provider, peersInvolvedInTheIndirectCredit);	//update the value donated in providers interaction
		updateConsumersInteraction(provider, consumer, donated);							//now we update the interaction values of consumer
		
			
		//since the interactions already exist, simply update them
		updateBalance(provider, consumer, provider.getInteractions().get(provider.getInteractions().indexOf(new Interaction(consumer, 0, 1))));
		if(!(consumer instanceof FreeRider))											//free riders don't use balance for nothing
			updateBalance(provider, consumer, consumer.getInteractions().get(consumer.getInteractions().indexOf(new Interaction(provider, 0, 1))));
		
		sortBalances(provider, consumer);												//sort the balances in both provider and consumer
		
		provider.getDonatedHistory()[simulator.getCurrentStep()] += donated;			//update the donated amount (from provider) in this step
		consumer.getConsumedHistory()[simulator.getCurrentStep()] += donated;			//update the consumed amount (from consumer) in this step
		
		return donated;
	}
	
	private double getAmountToDonate(Collaborator provider, List<Peer> peersInvolvedInTheIndirectCredit, double resources) {
		Peer consumer = null, B = null;					//peer A, the consumer, and peer B, the idle one
		double transitiveCredit = 0;
		if(peersInvolvedInTheIndirectCredit.size()==1)
			consumer = peersInvolvedInTheIndirectCredit.get(0);
		else{
			//TODO change it to assign this values dynamically from the list
			B = peersInvolvedInTheIndirectCredit.get(0);
			consumer = peersInvolvedInTheIndirectCredit.get(1);
			transitiveCredit = provider.getBalances().get(provider.getBalances().indexOf(new PeerInfo(B.getId()))).getBalance();
		}
		
		double valueToBeDonated = 0;
		if(resources==-1){																	//if resources==-1 the provider should donate as much as possible			
			double freeResources = Math.max(0,provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep());	//TODO how do we deal with this bug			
			valueToBeDonated = Math.max(0,Math.min(freeResources, consumer.getDemand()));									//TODO how do we deal with this bug
		}
		//this is for NilBalance peers: donate the specified in the division among all NilBalance peers
		else{						
			if(!(consumer instanceof FreeRider))
				valueToBeDonated = Math.min(consumer.getDemand(), resources);				//TODO isn't this specified before
			else
				valueToBeDonated = resources;
		}	
		
		//here we limit the valueToBeDonated by the MaximumCapacity the controller sets or by the amount of transitive credit
		if(simulator.isFdNof()){
			double maxToBeDonated = 0;
			if(simulator.isTransitivity() && peersInvolvedInTheIndirectCredit.size()>1){
				maxToBeDonated = transitiveCredit;											//limit the free resources to the amount of transitive credit
			}
			else{
				int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 1));
				Interaction interaction = provider.getInteractions().get(index);			//retrieve the interaction object with its history				
				double fairness = Simulator.getFairness(interaction.getConsumed(), interaction.getDonated());				
				if(fairness<0) 
					maxToBeDonated = provider.getMaxCapacityToSupply();		//global
				else
					maxToBeDonated = interaction.getMaxCapacityToSupply();	//pairwise
			}
			valueToBeDonated = Math.min(valueToBeDonated, maxToBeDonated);
		}
		
		// TODO Auto-generated method stub
		return valueToBeDonated;
	}
	
	private double updatePeerInteraction(Collaborator provider, List<Peer> peersInvolvedInTheIndirectCredit, double resources){
		Peer consumer = null, B = null;					//peer A, the consumer, and peer B, the idle one
		if(peersInvolvedInTheIndirectCredit.size()==1)
			consumer = peersInvolvedInTheIndirectCredit.get(0);
		else{
			//TODO change it to assign this values dynamically from the list
			B = peersInvolvedInTheIndirectCredit.get(0);
			consumer = peersInvolvedInTheIndirectCredit.get(1);
		}
		
		int index = provider.getInteractions().indexOf(new Interaction(B, 0, 1));
		Interaction interactionProviderIdle = provider.getInteractions().get(index);	//retrieve the interaction object with its history
		interactionProviderIdle.donate(resources);
		
		index = B.getInteractions().indexOf(new Interaction(provider, 0, 1));
		Interaction interactionIdleProvider = provider.getInteractions().get(index);	//retrieve the interaction object with its history
		interactionIdleProvider.consume(resources);
		
		index = B.getInteractions().indexOf(new Interaction(consumer, 0, 1));
		Interaction interactionIdleConsumer = provider.getInteractions().get(index);	//retrieve the interaction object with its history
		interactionIdleConsumer.donate(resources);
		
		index = consumer.getInteractions().indexOf(new Interaction(B, 0, 1));
		Interaction interactionConsumerIdle = provider.getInteractions().get(index);	//retrieve the interaction object with its history
		interactionConsumerIdle.consume(resources);
		
		double valueToBeDonated = 0;
		if(resources==-1){													//if resources==-1 the provider should donate as much as possible			
			double freeResources = Math.max(0,provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep());	//TODO how do we deal with this bug
			valueToBeDonated = Math.max(0,Math.min(freeResources, consumer.getDemand()));									//TODO how do we deal with this bug
		}
		//this is for NilBalance peers: donate the specified in the division among all NilBalance peers
		else{						
			if(!(consumer instanceof FreeRider))
				valueToBeDonated = Math.min(consumer.getDemand(), resources);									//TODO isn't this specified before
			else
				valueToBeDonated = resources;
		}	
		
		//here we limit the valueToBeDonated by the MaximumCapacity the controller sets		
		if(simulator.isFdNof()){
			double maxToBeDonated = 0;			
			double fairness = Simulator.getFairness(interaction.getConsumed(), interaction.getDonated());				
			if(fairness<0) 
				maxToBeDonated = provider.getMaxCapacityToSupply();		//global
			else
				maxToBeDonated = interaction.getMaxCapacityToSupply();	//pairwise
			
//			PeerInfo peerBalance = new PeerInfo(consumer.getId());
//			peerBalance = provider.getBalances().get(provider.getBalances().indexOf(peerBalance));
//			//if the peer has balance>maxToBeDonated, then the balance must be the maxToBeDonated
//			maxToBeDonated = Math.max(maxToBeDonated, peerBalance.getBalance());	 
//			maxToBeDonated = Math.min(provider.getInitialCapacity(), maxToBeDonated);
			valueToBeDonated = Math.min(valueToBeDonated, maxToBeDonated);
		}
		
		interaction.donate(valueToBeDonated);
		provider.setResourcesDonatedInCurrentStep(provider.getResourcesDonatedInCurrentStep()+valueToBeDonated);				
		return valueToBeDonated;
	}

	//remember that free rider are always zero credit
	public void performDonationToPeersWithNilBalance(Collaborator provider) {							
			
		List <Integer> consumingPeers = new ArrayList<Integer>();	//consuming peers that didn't consume
		consumingPeers.addAll(simulator.getConsumersList());					
		
		List <Integer> peersWithNilBalance = new ArrayList<Integer>();	//peers with balance==0 relative to the collaborator
		Collections.sort(provider.getBalances());						//assure the balance order
		for(int i = provider.getBalances().size()-1; i >= 0 ; i--){
			PeerInfo peerInfo = provider.getBalances().get(i);			
			if(peerInfo.getBalance()<=0 && consumingPeers.contains(PeerComunity.peers[peerInfo.getId()]))	//note that we also add free riders here
					peersWithNilBalance.add(provider.getBalances().get(i).getId());
		}
		
		//add the peers who want to consume but which the provider didnt interact before
		for(int idConsumer : consumingPeers){
			if(!peersWithNilBalance.contains(idConsumer))
				peersWithNilBalance.add(idConsumer);
		}
		
		//here we know who is zero credit and wants to consume		
		while(peersWithNilBalance.size()>0 && provider.getResourcesDonatedInCurrentStep()<(provider.getMaxCapacityToSupply()-0.0000000001)){	//TODO what should we do with this bug
			double smallestDemand = Double.MAX_VALUE;
			for(int idConsumer : peersWithNilBalance)
				smallestDemand = smallestDemand < PeerComunity.peers[idConsumer].getDemand()? smallestDemand : PeerComunity.peers[idConsumer].getDemand();
			
			double resourcesForPeersWithZeroCredit = provider.getMaxCapacityToSupply() - provider.getResourcesDonatedInCurrentStep();
			double howMuchShouldEachPeerReceive = resourcesForPeersWithZeroCredit/peersWithNilBalance.size();			
			double howMuchWillEachPeerReceiveInThisRound = Math.min(smallestDemand, howMuchShouldEachPeerReceive);
			
			List <Integer> peersWithNilBalanceAux = new ArrayList<Integer>();
			peersWithNilBalanceAux.addAll(peersWithNilBalance);
			for(int id : peersWithNilBalanceAux){
				double donated = performDonation(provider, PeerComunity.peers[id], howMuchWillEachPeerReceiveInThisRound);
				removePeerIfFullyConsumed(PeerComunity.peers[id]);
				//if donated is 0 then the fdnof constrained the amount of resources this peer should receive and therefore he already consumed all there were to him
				if(PeerComunity.peers[id].getDemand()<=0 || donated<0.0000000001)	
					peersWithNilBalance.remove((Integer)id);
			}
		}		
	}
	
	//-1 because the peer has balance, then provider should donate as much as possible
	public double performDonation(Collaborator provider, Peer consumer){
		return performDonation(provider, consumer, -1);
	}
		
	//the provider should donate the amount specified in resources, but only when resource >0	
	public double performDonation(Collaborator provider, Peer consumer, double resources){	
			
		if(!provider.getInteractions().contains(new Interaction(consumer, 0, 1))){	//just to retrieve the real interaction by comparison			
			//creates an interaction for the provider and for the consumer
			Interaction providersInteraction = new Interaction(consumer, provider.getInitialCapacity(), simulator.getNumSteps());
			provider.getInteractions().add(providersInteraction);
			provider.getBalances().add(new PeerInfo(consumer.getId()));
			if(!(consumer instanceof FreeRider)){
				Interaction consumersInteraction = new Interaction(provider, consumer.getInitialCapacity(), simulator.getNumSteps());
				consumer.getInteractions().add(consumersInteraction);
				consumer.getBalances().add(new PeerInfo(provider.getId()));
			}
		}			
		
		double donated = updateProvidersInteraction(provider, consumer, resources);		//update the value donated in providers interaction
		updateConsumersInteraction(provider, consumer, donated);						//now we update the interaction values of consumer
		
			
		//since the interactions already exist, simply update them
		updateBalance(provider, consumer, provider.getInteractions().get(provider.getInteractions().indexOf(new Interaction(consumer, 0, 1))));
		if(!(consumer instanceof FreeRider))											//free riders don't use balance for nothing
			updateBalance(provider, consumer, consumer.getInteractions().get(consumer.getInteractions().indexOf(new Interaction(provider, 0, 1))));
		
		sortBalances(provider, consumer);												//sort the balances in both provider and consumer
		
		provider.getDonatedHistory()[simulator.getCurrentStep()] += donated;			//update the donated amount (from provider) in this step
		consumer.getConsumedHistory()[simulator.getCurrentStep()] += donated;			//update the consumed amount (from consumer) in this step
		
		return donated;
	}
	
	private double updateProvidersInteraction(Collaborator provider, Peer consumer, double resources){
		
		int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 1));
		Interaction interaction = provider.getInteractions().get(index);	//retrieve the interaction object with its history
		
		
		double valueToBeDonated = 0;
		if(resources==-1){													//if resources==-1 the provider should donate as much as possible			
			double freeResources = Math.max(0,provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep());	//TODO how do we deal with this bug
			valueToBeDonated = Math.max(0,Math.min(freeResources, consumer.getDemand()));									//TODO how do we deal with this bug
		}
		//this is for NilBalance peers: donate the specified in the division among all NilBalance peers
		else{						
			if(!(consumer instanceof FreeRider))
				valueToBeDonated = Math.min(consumer.getDemand(), resources);									//TODO isn't this specified before
			else
				valueToBeDonated = resources;
		}	
		
		//here we limit the valueToBeDonated by the MaximumCapacity the controller sets		
		if(simulator.isFdNof()){
			double maxToBeDonated = 0;			
			double fairness = Simulator.getFairness(interaction.getConsumed(), interaction.getDonated());				
			if(fairness<0) 
				maxToBeDonated = provider.getMaxCapacityToSupply();		//global
			else
				maxToBeDonated = interaction.getMaxCapacityToSupply();	//pairwise
			
//			PeerInfo peerBalance = new PeerInfo(consumer.getId());
//			peerBalance = provider.getBalances().get(provider.getBalances().indexOf(peerBalance));
//			//if the peer has balance>maxToBeDonated, then the balance must be the maxToBeDonated
//			maxToBeDonated = Math.max(maxToBeDonated, peerBalance.getBalance());	 
//			maxToBeDonated = Math.min(provider.getInitialCapacity(), maxToBeDonated);
			valueToBeDonated = Math.min(valueToBeDonated, maxToBeDonated);
		}
		
		interaction.donate(valueToBeDonated);
		provider.setResourcesDonatedInCurrentStep(provider.getResourcesDonatedInCurrentStep()+valueToBeDonated);				
		return valueToBeDonated;
	}
	
	private void updateConsumersInteraction(Collaborator provider, Peer consumer, double consumed){
		if(!(consumer instanceof FreeRider)){
			int index = consumer.getInteractions().indexOf(new Interaction(provider, 0, 1));
			Interaction interaction = consumer.getInteractions().get(index);		//retrieve the interaction object with its history		
			interaction.consume(consumed);
		}		
		consumer.setDemand(Math.max(0,consumer.getDemand()-consumed));
	}
	
	private void updateBalance(Collaborator provider, Peer consumer, Interaction interaction){	//we have to call it twice: each for the interaction of each peer	
		double reputation = NetworkOfFavors.calculateBalance(interaction.getConsumed(), interaction.getDonated());
		if(interaction.getPeerB() == consumer){
			int consumerIndex = provider.getBalances().indexOf(new PeerInfo(consumer.getId()));
			provider.getBalances().get(consumerIndex).setBalance(reputation);
		}
		else{
			int providerIndex = consumer.getBalances().indexOf(new PeerInfo(provider.getId()));
			consumer.getBalances().get(providerIndex).setBalance(reputation);
		}
	}
	
	public void removePeerIfFullyConsumed(Peer consumer){
		if(consumer.getDemand()==0){
			simulator.getConsumersList().remove((Integer)consumer.getId());			
			simulator.getConsumedPeersList().add(consumer.getId());
		}
		else if(consumer.getDemand()<0){
			Simulator.logger.finest("Consumer demand should never be smaller than consumer.getInitialCapacity(). Some sheet happened here."
					+ "We should find the origin of this bug!");
			Simulator.logger.finest("Demand: "+consumer.getDemand());
			System.exit(0);
		}	
	}
	
	public void removePeerThatDonated(Collaborator provider){
		simulator.getProvidersList().remove((Integer)provider.getId());
		simulator.getDonatedPeersList().add(provider.getId());		
	}
	
	private void sortBalances(Collaborator provider, Peer consumer){
		Collections.sort(provider.getBalances());
		Collections.sort(consumer.getBalances());
    }

}
