package simulator;

import java.rmi.activation.UnknownObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;

public class Simulator {
	
	/**
	 * Simulation characteristics.
	 */
	private int numPeers;								//[FACTOR]: (collaborators + freeRiders)	
	private int numSteps;								//[FACTOR]: number of steps of the simulation	
	private int consumingStateProbability;				//[FACTOR]: probability of being in consuming state. Ranges (0,100).
	private double percentageCollaborators;			//[FACTOR]: percentage of peers that will be collaborators. Ranges (0,1).
	
	/**
	 * Peers characteristics.
	 */
	private double peersDemand;						//[FACTOR]: the demand the peers will try to use from other peers
	
	/**
	 * Collaborators characteristics.
	 */
	private double capacitySupplied;					//capacity of resources that peers can donate 			[begins with 1]
	private int returnLevelVerificationTime;			//times in steps to measure the necessity of supplying more or less resources. [assumed 10]
	private double changingValue;						//value added or subtracted to/from capacitySupplied	[assumed 0.05]
	
	/**
	 * Other variables used in simulation.
	 */
	private int currentStep;							//current step of simulation
	private List <Peer> consumedPeersList;				//list of all peers that have already consumed (collaborators + free riders)
	private List <Collaborator> donatedPeersList;		//list of all peers (collaborators + free riders)
	private List <Collaborator> consumersCollabList;	//list of consumers collaborators
	private List <Collaborator> donatorsList;			//list of donators
	private List <FreeRider> freeRidersList;			//list of free-riders	
	private Random randomGenerator;						//to randomly retrieve an element from ArrayList
	private int numCollaborators;						//number of collaborators
	

	/**
	 * Constructor used to configure simulation.
	 * @param numPeers	number of collaborators + free riders
	 * @param numSteps number of steps of the simulation
	 * @param consumingStateProbability probability of being in consuming state
	 * @param percentageCollaborators percentage of peers that will be collaborators
	 * @param peersDemand the demand the peers will try to use from other peers
	 * @param capacitySupplied capacity of resources that peers can donate
	 * @param returnLevelVerificationTime times in steps to measure the necessity of supplying more or less resources
	 * @param changingValue value added or subtracted to/from capacitySupplied
	 */
	public Simulator(int numPeers, int numSteps, int consumingStateProbability, double percentageCollaborators,
			int peersDemand, 
			double capacitySupplied, int returnLevelVerificationTime, double changingValue) {
		super();
		this.numPeers = numPeers;
		this.numSteps = numSteps;
		this.consumingStateProbability = consumingStateProbability;
		this.percentageCollaborators = percentageCollaborators;
		this.peersDemand = peersDemand;
		this.capacitySupplied = capacitySupplied;
		this.returnLevelVerificationTime = returnLevelVerificationTime;
		this.changingValue = changingValue;
		this.currentStep = 0;
		this.consumedPeersList = new ArrayList<Peer> ();
		this.donatedPeersList = new ArrayList<Collaborator> ();
		this.consumersCollabList = new ArrayList<Collaborator> ();
		this.donatorsList = new ArrayList<Collaborator> ();
		this.freeRidersList = new ArrayList<FreeRider> ();
		this.randomGenerator = new Random();
	}

	/**
	 * Create the peers. When they're collaborators we decide (calculate) if the peer will begin consuming or not
	 * based in consumingStateProbability. The free riders are always in consuming state, despite they achieve
	 * or not success.
	 */
	public void setupSimulation(){
		//creating the peers (collaborators + freeRiders)
		
		numCollaborators = (int) Math.ceil(this.numPeers*this.percentageCollaborators);
		
		for(int i = 0; i < numCollaborators; i++){
			//based in consumingStateProbability, we decide if the peer will begin consuming or not
			boolean beginsConsuming = (this.randomGenerator.nextInt(100)+1 <= this.consumingStateProbability)?true:false;
			if(beginsConsuming)
				consumersCollabList.add(new Collaborator(this.peersDemand, i+1, beginsConsuming, 0, 
						this.returnLevelVerificationTime, this.changingValue));
			else
				donatorsList.add(new Collaborator(0, i+1, beginsConsuming, this.capacitySupplied, 
						this.returnLevelVerificationTime, this.changingValue));	
		}
		
		int numFreeRiders = numPeers - numCollaborators;
		for(int i = 0; i < numFreeRiders; i++){
			//freeRiders never donates, they are always in consuming state. At least, they are always trying to consume.
			freeRidersList.add(new FreeRider(this.peersDemand, i+1+numCollaborators));	
		}		
	}
	
	/**
	 * Starts the simulation.
	 */
	public void startSimulation(){
		this.performCurrentStepDonations();
	}
	
	/**
	 * Performs all donations of the current step.
	 */
	private void performCurrentStepDonations(){
		this.currentStep++;
		
		Collaborator collab = null;
		
		/**
		 * While there is any collaborator willing to donate, try to choose one.
		 */
		while(!this.donatorsList.isEmpty()){
			
			/**
			 * If all peers who wanted to consume already consumed the quantity of resources
			 * he wanted, then there is not another possible match.
			 */
			if(!this.consumersCollabList.isEmpty())
				collab = this.choosesCollaboratorToDonate();	//randomly chooses who will donate
			else
				break;											//there is not any more consumer
			
			/**
			 * Keeps choosing consumer and donating until supply all his capacity.
			 */
			while(collab.getCapacitySupplied() > 0){	
				Peer p = choosesConsumer(collab);
				performDonation(collab, p);
			}
			
			/**
			 * Remove the donator from donatorsList, and add to donatedPeersList, in order
			 * to know who has already donated.
			 */
			this.donatorsList.remove(collab);
			this.donatedPeersList.add(collab);
		}
		
		nextStep();
	}
	
	/**
	 * Verify if the simulation ended and save the data. If it's not ended, then we redefine (calculate) if the
	 * collaborator will be in consuming state or not in the next step ( based in consumingStateProbability). 
	 */
	private void nextStep(){
		if(this.currentStep>=this.numSteps){
			//acabou... salva os dados em algum lugar
		}
		else{			
			
			/**
			 * Join all collaborators, consumers, and free riders in their lists.
			 */
			donatorsList.addAll(donatedPeersList);
			donatedPeersList.clear();
			
			for(Peer p : consumedPeersList){
				if(p instanceof FreeRider)
					freeRidersList.add((FreeRider)p);					
				else
					consumersCollabList.add((Collaborator)p);					
			}
			consumedPeersList.clear();

			
			//just to check if everything is OK until now
			if((donatorsList.size() + consumersCollabList.size())==numCollaborators)
				System.out.println("#Donators + #Consumers == #Collaborators :-)");
			
			
			/**
			 * Join all collaborators in a list, and clear donatorsList and consumersList,
			 * to fulfill them again.
			 */
			List <Collaborator> allCollaborators = new ArrayList<Collaborator>();
			allCollaborators.addAll(donatorsList);
			allCollaborators.addAll(consumersCollabList);			
			donatorsList.clear();
			consumersCollabList.clear();
			
			for(Collaborator c : allCollaborators){
				//based in consumingStateProbability, we decide if the collaborator will consume or not in the next step
				c.setConsuming((this.randomGenerator.nextInt(100)+1 <= this.consumingStateProbability)?true:false);
				if(c.isConsuming()){
					c.setDemand(this.peersDemand);
					c.setCapacitySupplied(0);
					consumersCollabList.add(c);
				}
				else{
					c.setDemand(0);
					c.setCapacitySupplied(this.capacitySupplied);
					donatorsList.add(c);	
				}
			}
			
			for(FreeRider f : freeRidersList){
				f.setConsuming(true);				//just to assure
				f.setDemand(this.peersDemand);
			}
				
			performCurrentStepDonations();
		}			
	}

	
	
	
	
	/*********************************************************************************
	 * 									Auxiliary methods.							 *
	 *********************************************************************************/
	
	/**
	 * Randomly chooses a collaborator to donate.
	 * @return the collaborator that is not consuming, randomly choosed
	 */
	private Collaborator choosesCollaboratorToDonate(){
		int index = anyPeer(this.donatorsList);
		return this.donatorsList.get(index);
	}
	
	/**
	 * Chooses a peer to consume the resources of next donation. Based on interactions history or randomly. 
	 * @param c the collaborator who will donate his resources
	 * @return the peer choosed to consume, a consuming collaborator or a free rider
	 * @throws UnknownObjectException 
	 */
	private Peer choosesConsumer(Collaborator c){
		
		/**
		 * This peer has interacted at least once, then, we should get the peer with highest reputation
		 * in the TreeMap structure. (if there is at least one that is a consumer on this step)
		 */
		if(!c.getInteractions().isEmpty()){									
			int nth = 1;						//nth peer with highest reputation
			Peer p = new Peer(0,-1, false);	//id = -1, so that we won't find it on consumedPeersList in first iteration
												//consuming must be true, so that we can enter in while loop
			/**
			 * The peer can't have already consumed (can't be on consumedPeersList) and must be in consuming state.
			 */
			while(!(!this.consumedPeersList.contains(p) && p.isConsuming())){
				p = c.getThePeerWithNthBestReputation(nth);
				if(p == null)	//we couldn't find a peer that hasn't interacted yet, and wants to consume
					break;
				nth++;
			}
			
			/**
			 * The case that we find a candidate!
			 */
			if(p!=null){			
				if(p instanceof Collaborator)
					return (Collaborator) p;
				else if(p instanceof FreeRider)
					return (FreeRider) p;
				else{
					System.out.println("####################################################################################");
					System.out.println("#################################  ERROR  ##########################################");
					System.out.println("# For some reason, the consumer (object) retrieved is not a FreeRider nor a \n" +
									   "# Collaborator... You must check what is happening!");
					System.out.println("####################################################################################");
					System.exit(0);
					return null;
				}
			}
			else{
				System.out.println("####################################################################################");
				System.out.println("#################################  ERROR  ##########################################");
				System.out.println("# For some reason, the consumer (object) retrieved was null. You must check what \n" +
								   "# is happening!");
				System.out.println("####################################################################################");
				System.exit(0);
				return null;
			}
		}
		
		/**
		 * There are 2 options we might got here:
		 * 		1: we didn't find a candidate who wants to consume, in the InteractionsHistory
		 * 		2: collaborator c has never interacted before
		 * Solution: choose randomly.
		 */	
		else{			
			List <Peer> allConsumers = new ArrayList<Peer>();
			allConsumers.addAll(consumersCollabList);
			allConsumers.addAll(freeRidersList);
			int index = anyPeer(allConsumers);
			if(allConsumers.get(index) instanceof Collaborator)			
				return (Collaborator) allConsumers.get(index);
			else if(allConsumers.get(index) instanceof FreeRider)	//lets assure
				return (FreeRider) allConsumers.get(index);
			else{
				System.out.println("####################################################################################");
				System.out.println("#################################  ERROR  ##########################################");
				System.out.println("# For some reason, the consumer (object) retrieved was null. You must check what \n" +
								   "# is happening!");
				System.out.println("####################################################################################");
				System.exit(0);
				return null;
			}		
		}
	}

	/**
	 * Performs the donation from a donator to a consumer.
	 * @param donator the collaborator peer who will donate his spare resources
	 * @param consumer the peer who will consume the resources
	 */
	private void performDonation(Collaborator donator, Peer consumer){
		
		Interaction artificialInteraction = new Interaction(donator, consumer);		//just to retrieve the real interaction by comparison
		
		double maxValueToBeConsumed = consumer.getDemand();				
		double maxValueToBeDonated = donator.getCapacitySupplied();
		
		/**
		 * The peers have already interacted.
		 */
		if(donator.getInteractions().contains(artificialInteraction) && consumer.getInteractions().contains(artificialInteraction)){
			int index = donator.getInteractions().indexOf(artificialInteraction);
			Interaction interaction = donator.getInteractions().get(index);			//retrieve the interaction object with its history
						
			/**
			 * This if-else is necessary because we don't want to duplicate the interaction (objects) of peers A and B.
			 * We have only one object "called" interaction(A,B). And we carefully deal the data when we want the
			 * object "called" interaction (B,A).
			 */
			
			/**
			 * If the donator is peerA...
			 * Performs the donation, and updates the peers reputation.
			 */
			if(donator.getPeerId() == interaction.getPeerA().getPeerId()){				
				interaction.peerADonatesValue(Math.min(maxValueToBeConsumed, maxValueToBeDonated));								
				donator.setCapacitySupplied(donator.getCapacitySupplied()-Math.min(maxValueToBeConsumed, maxValueToBeDonated));	//update the capacity supplied by donator
				consumer.setDemand(consumer.getDemand()-Math.min(maxValueToBeConsumed, maxValueToBeDonated));					//update the demand of the consumer in the current step
				
				/**
				 * Update the treeMap donator reputation, and update also the consumer reputation
				 * (if he is not a free rider). 
				 */
				donator.getPeersReputations().put(consumer, NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), true));
				if(!(consumer instanceof FreeRider))		
					consumer.getPeersReputations().put(donator, NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), true));				
			}
			/**
			 * The donator is PeerB...
			 * Performs the donation, and updates the peers reputation.
			 */
			else if(donator.getPeerId() == interaction.getPeerB().getPeerId()){
				interaction.peerBDonatesValue(Math.min(maxValueToBeConsumed, maxValueToBeDonated));
				donator.setCapacitySupplied(donator.getCapacitySupplied()-Math.min(maxValueToBeConsumed, maxValueToBeDonated));	//update the capacity supplied by donator
				consumer.setDemand(consumer.getDemand()-Math.min(maxValueToBeConsumed, maxValueToBeDonated));					//update the demand of the consumer in the current step
				
				/**
				 * Update the treeMap donator reputation, and update also the consumer reputation
				 * (if he is not a free rider). 
				 */
				donator.getPeersReputations().put(consumer, NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), true));
				if(!(consumer instanceof FreeRider))
					consumer.getPeersReputations().put(donator, NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), true));
			}
			else{
				System.out.println("####################################################################################");
				System.out.println("#################################  ERROR  ##########################################");
				System.out.println("# The interaction exists, but for some reason, I could not identify who is peer A \n" +
								   "# (the donator) or peer B (the consumer). You must check what is happening!");
				System.out.println("####################################################################################");
				System.exit(0);
				return;
			}
		}
		/**
		 * The peers never interacted.
		 */
		else{
			Interaction interaction = new Interaction(donator, consumer);
			interaction.peerADonatesValue(Math.min(maxValueToBeConsumed, maxValueToBeDonated));		
			donator.getInteractions().add(interaction);																		//adds the new interaction to the donator peer	
			consumer.getInteractions().add(interaction);																	//adds the new interaction to the consumer peer
			donator.setCapacitySupplied(donator.getCapacitySupplied()-Math.min(maxValueToBeConsumed, maxValueToBeDonated));	//update the capacity supplied by donator
			consumer.setDemand(consumer.getDemand()-Math.min(maxValueToBeConsumed, maxValueToBeDonated));					//update the demand of the consumer in the current step
			
			/**
			 * Update the treeMap donator reputation, and update also the consumer reputation
			 * (if he is not a free rider). 
			 */
			donator.getPeersReputations().put(consumer, NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), true));
			if(!(consumer instanceof FreeRider))
				consumer.getPeersReputations().put(donator, NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), true));		
		}
		
		/**
		 * If the consumer already consumed everything he wanted, then we remove 
		 * him from consumersList, and add to consumedPeersList, in order
		 * to know who has already consumed.
		 */
		if(consumer.getDemand()==0){
			if(consumer instanceof FreeRider)
				this.freeRidersList.remove(consumer);
			else
				this.consumersCollabList.remove(consumer);
			this.consumedPeersList.add(consumer);
		}
		else if(consumer.getDemand()<0){
			System.out.println("####################################################################################");
			System.out.println("#################################  ERROR  ##########################################");
			System.out.println("# Consumer demand should never be smaller than 0. Some sheet happened here. \n" +
							   "# We should find the origin of this bug!");
			System.out.println("####################################################################################");
			System.exit(0);
		}
		
	}
	
	/**
	 * Randomly chooses an item of the list.
	 * @param peersList	 the list of peers on which we will randomly choose one
	 * @return the index of item randomly choosed
	 */
    private int anyPeer(List <? extends Peer> peersList){    	
        return randomGenerator.nextInt(peersList.size());
    }
}




