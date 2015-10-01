package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.Triplet;
import peer.balance.PeerInfo;

public class Market {
	
	private Simulator simulator;
	
	public Market(Simulator simulator){
		this.simulator = simulator;
	}
	
	public void performDonation(Collaborator provider, List<Triplet> consumingPeers){
		if(consumingPeers.size()==1){
			Triplet peer = consumingPeers.get(0);
			performDonation(provider, peer, -1);		//involves donation to peer with balance>0 or transitiveBalance>0			
		}
		else
			performDonationToPeersWithSameBalance(provider, consumingPeers); //donate to more than one peer	
	}
	
	//the provider should donate the amount specified in resources, but only when resource >0	
	public double performDonation(Collaborator provider, Triplet consumerTriplet, double resources){
				
		resources = getAmountToDonate(provider, consumerTriplet, resources);
		
		//update the values of interactions and the balances of each peer
		updatePeersInteraction(provider, consumerTriplet, resources);
		
		Peer[] peersInvolvedInDonation = null;
		if(consumerTriplet.getTransitivePeer()==null)
			peersInvolvedInDonation = new Peer[]{provider, consumerTriplet.getConsumer()}; 
		else
			peersInvolvedInDonation = new Peer[]{provider, consumerTriplet.getTransitivePeer(), consumerTriplet.getConsumer()};
		sortBalances(peersInvolvedInDonation);						//sort the balances of all peers
		
		//update the donated and consumed amount in this step
		provider.getDonatedHistory()[simulator.getCurrentStep()] += resources;
		if(consumerTriplet.getTransitivePeer() != null)
			provider.getDonatedByTransitivityHistory()[simulator.getCurrentStep()] += resources;
		if(consumerTriplet.getConsumer() instanceof FreeRider)
			provider.getDonatedToFreeRidersHistory()[simulator.getCurrentStep()] += resources;
		consumerTriplet.getConsumer().getConsumedHistory()[simulator.getCurrentStep()] += resources;
		
		return resources;
	}
	
	//remember that free rider are always zero credit
	public void performDonationToPeersWithSameBalance(Collaborator provider, List<Triplet> consumingPeers) {							
		List<Triplet> consumingPeersLocal = new ArrayList<Triplet>();
		consumingPeersLocal.addAll(consumingPeers);	
		for(Triplet triplet : consumingPeers){
			if(triplet.getConsumer().getDemand()==0)
				consumingPeersLocal.remove(triplet);
		}
			
		//-0.0000000001
		while(consumingPeersLocal.size()>0 && provider.getResourcesDonatedInCurrentStep()<provider.getInitialCapacity()){	//TODO what should we do with this bug
			double smallestDemand = Double.MAX_VALUE;
			for(Triplet consumerTriplet : consumingPeersLocal)
				smallestDemand = smallestDemand < consumerTriplet.getConsumer().getDemand()? smallestDemand : consumerTriplet.getConsumer().getDemand();
			
			double resourcesForPeersWithSameBalance = provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep();
			double howMuchShouldEachPeerReceive = resourcesForPeersWithSameBalance/consumingPeersLocal.size();			
			double howMuchWillEachPeerReceiveInThisRound = Math.min(smallestDemand, howMuchShouldEachPeerReceive);
			
			//The provider still has spare resources but the consumers already consumed what they needed
			if(howMuchWillEachPeerReceiveInThisRound==0)
				break;
			
			List<Triplet> consumingPeersAux = new ArrayList<Triplet>();
			consumingPeersAux.addAll(consumingPeersLocal);			
			for(Triplet consumerTriplet : consumingPeersAux){
				double donated = performDonation(provider, consumerTriplet, howMuchWillEachPeerReceiveInThisRound);
				removePeerIfFullyConsumed(consumerTriplet.getConsumer());
				if(consumerTriplet.getConsumer().getDemand()<=0 || donated<0.0000000000000001)	//if donated==0 then that debt might have already been paid
					consumingPeersLocal.remove(consumerTriplet);
			}
		}		
	}
	
	private double getAmountToDonate(Collaborator provider, Triplet consumerTriplet, double resources) {
		
		Peer consumer = consumerTriplet.getConsumer();
		
		double consumerDemand = consumer.getDemand();
		double freeResources = Math.max(0,provider.getInitialCapacity() - provider.getResourcesDonatedInCurrentStep());
		double maxToBeDonated = Math.max(0,Math.min(freeResources, consumerDemand));		
		
		//maxToBeDonated is constrained by the resources specified, if it is not -1
		if(resources!=-1)
				maxToBeDonated = Math.min(maxToBeDonated, resources);		
		
		if(simulator.isTransitivity() && consumerTriplet.getTransitivePeer()!=null){
			Peer idlePeer = consumerTriplet.getTransitivePeer();
			double gammaProviderIdle = provider.getBalances().get(provider.getBalances().indexOf(new PeerInfo(idlePeer.getId()))).getBalance();
			double gammaIdleConsumer = idlePeer.getBalances().get(idlePeer.getBalances().indexOf(new PeerInfo(consumer.getId()))).getBalance();
			double transitiveCredit = Math.min(gammaProviderIdle, gammaIdleConsumer);
			maxToBeDonated = Math.min(maxToBeDonated, transitiveCredit);	//limit the maxToBeDonated by the transitiveCredit
			return maxToBeDonated;
		}			
		
		//here we limit the maxToBeDonated by the alfa the controller sets		
		if(simulator.isFdNof()){
			double fairnessPairwise = -1;			
			int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 1));
			Interaction interaction = null;
			if(index != -1){
				interaction = provider.getInteractions().get(index);				//retrieve the interaction object with its history
				fairnessPairwise = Simulator.getFairness(interaction.getConsumed(), interaction.getDonated());		
			}
			
			if(interaction == null || fairnessPairwise<0) 
				maxToBeDonated = Math.min(maxToBeDonated, provider.getMaxCapacityToSupply());	//global
			else
				maxToBeDonated = Math.min(maxToBeDonated,interaction.getMaxCapacityToSupply());	//pairwise			
			
		}
		
		return maxToBeDonated;
	}
	
	private void updatePeersInteraction(Collaborator provider, Triplet consumerTriplet, double resources){	
		Peer consumer = consumerTriplet.getConsumer();
		Peer idlePeer = consumerTriplet.getTransitivePeer();
		
		if(idlePeer!=null){						
			//make sure all interaction exist, and if not, create them
			createInteraction(provider, idlePeer);
			createInteraction((Collaborator)idlePeer, provider);
			createInteraction((Collaborator)idlePeer, consumer);
			createInteraction((Collaborator)consumer, idlePeer);
			
			int index = provider.getInteractions().indexOf(new Interaction(idlePeer, 0, 1));
			Interaction interactionProviderIdle = provider.getInteractions().get(index);	//retrieve the interaction object with its history
			interactionProviderIdle.donate(resources);
			updateBalance(provider, idlePeer, interactionProviderIdle);
			
			index = idlePeer.getInteractions().indexOf(new Interaction(provider, 0, 1));
			Interaction interactionIdleProvider = idlePeer.getInteractions().get(index);	
			interactionIdleProvider.consume(resources);
			updateBalance((Collaborator)idlePeer, provider, interactionIdleProvider);
			
			index = idlePeer.getInteractions().indexOf(new Interaction(consumer, 0, 1));
			Interaction interactionIdleConsumer = idlePeer.getInteractions().get(index);
			interactionIdleConsumer.donate(resources);
			updateBalance((Collaborator)idlePeer, consumer, interactionIdleConsumer);
			
			index = consumer.getInteractions().indexOf(new Interaction(idlePeer, 0, 1));
			Interaction interactionConsumerIdle = consumer.getInteractions().get(index);	
			interactionConsumerIdle.consume(resources);
			updateBalance((Collaborator)consumer, idlePeer, interactionConsumerIdle);	
		}
		else{
			//make sure all interaction exists
			createInteraction(provider, consumer);
			
			int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 1));
			Interaction interactionProviderConsumer = provider.getInteractions().get(index);	
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
