package simulator;

import java.util.ArrayList;
import java.util.Arrays;
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
//			double resourcesForPeersWithZeroCredit = provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep();
			double howMuchShouldEachPeerReceive = resourcesForPeersWithZeroCredit/peersWithNilBalance.size();			
			double howMuchWillEachPeerReceiveInThisRound = Math.min(smallestDemand, howMuchShouldEachPeerReceive);
			
			List <Integer> peersWithNilBalanceAux = new ArrayList<Integer>();
			peersWithNilBalanceAux.addAll(peersWithNilBalance);
			for(int id : peersWithNilBalanceAux){
				double donated = performDonation(provider, PeerComunity.peers[id], null, howMuchWillEachPeerReceiveInThisRound);
				removePeerIfFullyConsumed(PeerComunity.peers[id]);
				//if donated is 0 then the fdnof constrained the amount of resources this peer should receive and therefore he already consumed all there were to him
//				if(PeerComunity.peers[id].getDemand()<=0 || donated<0.0000000001)	
				if(PeerComunity.peers[id].getDemand()<=0)	
					peersWithNilBalance.remove((Integer)id);
			}
		}		
	}
	
	public void performDonationToPeersWithTransitiveCredit(Collaborator provider, Peer consumer, List<Peer> peersInvolvedInTheIndirectCredit) {
		double donated = performDonation(provider, consumer, peersInvolvedInTheIndirectCredit, -1);
		provider.getDonatedByTransitivityHistory()[simulator.getCurrentStep()] += donated;
		consumer.getConsumedByTransitivityHistory()[simulator.getCurrentStep()] += donated;
	}
	
	//-1 because the peer has balance, then provider should donate as much as possible
	public double performDonation(Collaborator provider, Peer consumer){
		return performDonation(provider, consumer, null, -1);
	}
	
	//the provider should donate the amount specified in resources, but only when resource >0	
	public double performDonation(Collaborator provider, Peer consumer, List<Peer> peersInvolvedInTheIndirectCredit, double resources){	
		resources = getAmountToDonate(provider, consumer, peersInvolvedInTheIndirectCredit, resources);
		
		//update the values of interactions and the balances of each peer
		updatePeersInteraction(provider, consumer, peersInvolvedInTheIndirectCredit, resources);
		
		Peer[] peersInvolvedInDonation = null;
		if(peersInvolvedInTheIndirectCredit==null)
			peersInvolvedInDonation = new Peer[]{provider, consumer}; 
		else{
			peersInvolvedInDonation = new Peer[peersInvolvedInTheIndirectCredit.size()+2];	
			peersInvolvedInDonation[0] = provider;
			peersInvolvedInDonation[1] = consumer;
			for(int i = 0; i < peersInvolvedInTheIndirectCredit.size(); i++)
				peersInvolvedInDonation[i+2] = peersInvolvedInTheIndirectCredit.get(i);
		}
		sortBalances(peersInvolvedInDonation);						//sort the balances of all peers
		
		//update the donated and consumed amount in this step
		provider.getDonatedHistory()[simulator.getCurrentStep()] += resources;
		if(consumer instanceof FreeRider)
			provider.getDonatedToFreeRidersHistory()[simulator.getCurrentStep()] += resources;
		consumer.getConsumedHistory()[simulator.getCurrentStep()] += resources;
		
		return resources;
	}
	
	private double getAmountToDonate(Collaborator provider, Peer consumer, List<Peer> peersInvolvedInTheIndirectCredit, double resources) {
		
		double valueToBeDonated = 0;
		if(resources==-1){																	//if resources==-1 the provider should donate as much as possible			
			double freeResources = Math.max(0,provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep());				
			valueToBeDonated = Math.max(0,Math.min(freeResources, consumer.getDemand()));									
		}
		//this is for NilBalance peers: donate the specified in the division among all NilBalance peers
		else{						
			if(!(consumer instanceof FreeRider))
				return Math.min(consumer.getDemand(), resources);				
			else
				return resources;
		}	
		
		Peer B = null;					//peer A, the consumer, and peer B, the idle one
		double transitiveCredit = 0;
		if(simulator.isTransitivity() && peersInvolvedInTheIndirectCredit!=null){
			//TODO change it to assign this values dynamically from the list
			B = peersInvolvedInTheIndirectCredit.get(0);
			double gammaProviderB = provider.getBalances().get(provider.getBalances().indexOf(new PeerInfo(B.getId()))).getBalance();
			double gammaBConsumer = B.getBalances().get(B.getBalances().indexOf(new PeerInfo(consumer.getId()))).getBalance();
			transitiveCredit = Math.min(gammaProviderB, gammaBConsumer);
			valueToBeDonated = Math.min(valueToBeDonated, transitiveCredit);	//limit the value to be donated to the amount of transitive credit
			return valueToBeDonated;
		}
		
		//here we limit the valueToBeDonated by the MaximumCapacity the controller sets		
		if(simulator.isFdNof()){
			double maxToBeDonated = 0;
			int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 1));
			if(index == -1)
				maxToBeDonated = provider.getMaxCapacityToSupply();		//global
			else{
				Interaction interaction = provider.getInteractions().get(index);			//retrieve the interaction object with its history
				double fairness = Simulator.getFairness(interaction.getConsumed(), interaction.getDonated());				
				if(fairness<0) 
					maxToBeDonated = provider.getMaxCapacityToSupply();		//global
				else
					maxToBeDonated = interaction.getMaxCapacityToSupply();	//pairwise
			}
			valueToBeDonated = Math.min(valueToBeDonated, maxToBeDonated);
		}
		
		return valueToBeDonated;
	}
	
	private void updatePeersInteraction(Collaborator provider, Peer consumer, List<Peer> peersInvolvedInTheIndirectCredit, double resources){		
		Peer idleB = null;					//peer A, the consumer, and peer B, the idle one
		if(peersInvolvedInTheIndirectCredit!=null){			
			//TODO change it to assign these values dynamically from the list
			idleB = peersInvolvedInTheIndirectCredit.get(0);
			
			//make sure all interaction exist, and if not, create them
			createInteraction(provider, idleB);
			createInteraction((Collaborator)idleB, provider);
			createInteraction((Collaborator)idleB, consumer);
			createInteraction((Collaborator)consumer, idleB);
			
			int index = provider.getInteractions().indexOf(new Interaction(idleB, 0, 1));
			Interaction interactionProviderIdle = provider.getInteractions().get(index);	//retrieve the interaction object with its history
			interactionProviderIdle.donate(resources);
			updateBalance(provider, idleB, interactionProviderIdle);
			
			index = idleB.getInteractions().indexOf(new Interaction(provider, 0, 1));
			Interaction interactionIdleProvider = idleB.getInteractions().get(index);	//retrieve the interaction object with its history
			interactionIdleProvider.consume(resources);
			updateBalance((Collaborator)idleB, provider, interactionIdleProvider);
			
			index = idleB.getInteractions().indexOf(new Interaction(consumer, 0, 1));
			Interaction interactionIdleConsumer = idleB.getInteractions().get(index);	//retrieve the interaction object with its history
			interactionIdleConsumer.donate(resources);
			updateBalance((Collaborator)idleB, consumer, interactionIdleConsumer);
			
			index = consumer.getInteractions().indexOf(new Interaction(idleB, 0, 1));
			Interaction interactionConsumerIdle = consumer.getInteractions().get(index);	//retrieve the interaction object with its history
			interactionConsumerIdle.consume(resources);
			updateBalance((Collaborator)consumer, idleB, interactionConsumerIdle);			//TODO when fixing to dynamically code, note that update Balance is not necessary for free riders
		}
		else{
			//make sure all interaction exists
			createInteraction(provider, consumer);
			
			int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 1));
			Interaction interactionProviderConsumer = provider.getInteractions().get(index);	//retrieve the interaction object with its history
			interactionProviderConsumer.donate(resources);
			updateBalance(provider, consumer, interactionProviderConsumer);
			
			if(!(consumer instanceof FreeRider)){												//free riders don't use balance for nothing
				index = consumer.getInteractions().indexOf(new Interaction(provider, 0, 1));
				Interaction interactionConsumerProvider = consumer.getInteractions().get(index);	
				interactionConsumerProvider.consume(resources);
				updateBalance(provider, consumer, interactionConsumerProvider);
			}
		}
		provider.setResourcesDonatedInCurrentStep(provider.getResourcesDonatedInCurrentStep()+resources);
		consumer.setDemand(Math.max(0,consumer.getDemand()-resources));
	}
	
	private void createInteraction(Collaborator provider, Peer consumer){
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
	}	
	
	private void updateBalance(Collaborator provider, Peer consumer, Interaction interaction){	//we have to call it twice: each for the interaction of each peer	
		double balance = NetworkOfFavors.calculateBalance(interaction.getConsumed(), interaction.getDonated());
		if(interaction.getPeerB() == consumer){
			int consumerIndex = provider.getBalances().indexOf(new PeerInfo(consumer.getId()));
			provider.getBalances().get(consumerIndex).setBalance(balance);
		}
		else{
			int providerIndex = consumer.getBalances().indexOf(new PeerInfo(provider.getId()));
			consumer.getBalances().get(providerIndex).setBalance(balance);
		}
	}
	
	public void removePeerIfFullyConsumed(Peer consumer){
		if(consumer.getDemand()<0.0000000000000000001){
//		if(consumer.getDemand()==0){
			simulator.getConsumersList().remove((Integer)consumer.getId());
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
	}
	
	private void sortBalances(Peer ... peers){
		for(Peer p : peers)
			Collections.sort(p.getBalances());
    }

}
