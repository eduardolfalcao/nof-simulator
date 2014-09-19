package simulator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.reputation.PeerReputation;
import utils.WriteExcel2010;

public class Simulator {
	
	/**
	 * Simulation characteristics.
	 */
	private int numPeers;							//[FACTOR]: (collaborators + freeRiders)	
	private int numSteps;							//[FACTOR]: number of steps of the simulation	
	private double consumingStateProbability;		//[FACTOR]: probability of being in consuming state. Ranges (0,1).
	private double percentageCollaborators;		//[FACTOR]: percentage of peers that will be collaborators. Ranges (0,1).
	
	/**
	 * NoF characteristics.
	 */
	private boolean dynamic;						//[FACTOR]: if the capacity supply changes dynamicly or not
	private boolean nofWithLog;					//if the reputations will be calculated with log or with sqrt
	
	/**
	 * Peers characteristics.
	 */
	private double peersDemand;					//[FACTOR]: the demand the peers will try to use from other peers
	private double peersCapacity;
	
	/**
	 * Collaborators characteristics.
	 */
	private double capacitySupplied;				//capacity of resources that peers can donate 			[begins with 1]
	private double changingValue;					//value added or subtracted to/from capacitySupplied	[assumed 0.05]
	
	/**
	 * Other variables used in simulation.
	 */
	public static Peer peers [];					//all peers of the simulation
	
	private int currentStep;						//current step of simulation
	private List <Integer> consumedPeersList;		//list of all peers that have already consumed (collaborators + free riders)
	private List <Integer> donatedPeersList;		//list of all peers (collaborators + free riders)
	private List <Integer> consumersCollabList;		//list of consumers collaborators
	private List <Integer> donorsList;				//list of donors
	private List <Integer> freeRidersList;			//list of free-riders	
	private Random randomGenerator;					//to randomly define who is donor or consumer (besides collaborator or free rider) 
	private Random anyPeerRandomGenerator;			//to randomly retrieve an element from ArrayList (with a seed)
	private int numCollaborators;					//number of collaborators
	
	private String outputFile;						//file to export Data
	
	public final static Logger logger = Logger.getLogger(Simulator.class.getName());

	/**
	 * Constructor used to configure simulation.
	 * @param numPeers	number of collaborators + free riders
	 * @param numSteps number of steps of the simulation
	 * @param consumingStateProbability probability of being in consuming state
	 * @param percentageCollaborators percentage of peers that will be collaborators
	 * @param dynamic the context of capacity supply
	 * @param peersDemand the demand the peers will try to use from other peers
	 * @param capacitySupplied capacity of resources that peers can donate
	 * @param returnLevelVerificationTime times in steps to measure the necessity of supplying more or less resources
	 * @param changingValue value added or subtracted to/from capacitySupplied
	 * @param seed value used to calculate probability of being consumer or donor
	 */
	public Simulator(int numPeers, int numSteps, double consumingStateProbability, double percentageCollaborators, boolean dynamic, boolean nofWithLog,
			double peersDemand, double capacitySupplied, double changingValue, long seed, Level level, String outputFile) {
		super();
		this.numPeers = numPeers;
		this.numSteps = numSteps;
		this.consumingStateProbability = consumingStateProbability;
		this.percentageCollaborators = percentageCollaborators;
		this.dynamic = dynamic;
		this.nofWithLog = nofWithLog;
		this.peersDemand = peersDemand;
		this.capacitySupplied = capacitySupplied;
		this.changingValue = changingValue;
		this.currentStep = 0;
		Simulator.peers = new Peer[numPeers];
		this.consumedPeersList = new ArrayList<Integer> ();
		this.donatedPeersList = new ArrayList<Integer> ();
		this.consumersCollabList = new ArrayList<Integer> ();
		this.donorsList = new ArrayList<Integer> ();
		this.freeRidersList = new ArrayList<Integer> ();		
		this.randomGenerator = new Random(seed);
		this.anyPeerRandomGenerator = new Random(seed);
		this.outputFile = outputFile;
		
		this.peersCapacity = capacitySupplied;
		
		/* Logger setup */
		Simulator.logger.setLevel(level);
		Simulator.logger.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
	    handler.setLevel(level);
	    logger.addHandler(handler);
	}

	/**
	 * Create the peers. When they're collaborators we decide (calculate) if the peer will begin consuming or not
	 * based in consumingStateProbability. The free riders are always in consuming state, despite they achieve
	 * or not success.
	 */
	private void setupSimulation(){
		//creating the peers (collaborators + freeRiders)		
		numCollaborators = (int) Math.ceil(this.numPeers*this.percentageCollaborators);
		
		for(int i = 0; i < numCollaborators; i++){			
			//based in consumingStateProbability, we decide if the peer will begin consuming or not
			boolean beginsConsuming = (this.randomGenerator.nextInt(100)+1 <= (this.consumingStateProbability*100));
			Collaborator collab = null;
			if(beginsConsuming){
				collab = new Collaborator(this.peersDemand, i, beginsConsuming, this.capacitySupplied, this.numSteps);
				collab.getCapacitySuppliedHistory()[this.currentStep] = this.capacitySupplied;
				collab.getRequestedHistory()[this.currentStep] = this.peersDemand-this.peersCapacity;
				collab.getConsumedHistory()[this.currentStep] = 0;
				collab.setDemand(collab.getDemand()-this.peersCapacity);
				consumersCollabList.add(i);
			}				
			else{
				collab = new Collaborator(0, i, beginsConsuming, this.capacitySupplied, this.numSteps);
				collab.getCapacitySuppliedHistory()[this.currentStep] = this.capacitySupplied;
				collab.getRequestedHistory()[this.currentStep] = 0;
				donorsList.add(i);
			}
			Simulator.peers[i] = collab;
		}
		
		int numFreeRiders = numPeers - numCollaborators;
		for(int i = 0; i < numFreeRiders; i++){
			//freeRiders never donates, they are always in consuming state. At least, they are always trying to consume.
			Peer p = new FreeRider(this.peersDemand, i+numCollaborators, this.numSteps);
			p.getRequestedHistory()[this.currentStep] = this.peersDemand-this.peersCapacity;
			p.getConsumedHistory()[this.currentStep] = 0;
			p.setDemand(p.getDemand()-this.peersCapacity);
			freeRidersList.add(i+numCollaborators);
			Simulator.peers[i+numCollaborators] = p;
		}		
		
		Simulator.logger.info("Created: "+numCollaborators+" collaborators and "+numFreeRiders+" free riders.");

	}
	
	/**
	 * Starts the simulation.
	 */
	public void startSimulation(){
		Simulator.logger.info("Setting up simulation...");
		this.setupSimulation();
		Simulator.logger.info("Starting simulation...");
		this.performCurrentStepDonations();
	}
	
	/**
	 * Performs all donations of the current step.
	 */
	private void performCurrentStepDonations(){
		
		Collaborator collab = null;
		
		/**
		 * While there is any collaborator willing to donate, try to choose one.
		 */
		while(!this.donorsList.isEmpty()){			
			/**
			 * If all peers who wanted to consume already consumed the quantity of resources
			 * he wanted, then there is not another possible match.
			 */
			if(!this.consumersCollabList.isEmpty() || !this.freeRidersList.isEmpty())
				collab = this.choosesCollaboratorToDonate();	//randomly chooses who will donate
			else
				break;											//there is not any more consumer
			
			/**
			 * Keeps choosing consumer and donating until supply all his capacity.
			 */
			while(collab.getCapacitySupplied() > 0){
				/** If a donor finish the consumers list here, there's no further consumer... **/
				if(this.consumersCollabList.isEmpty() && this.freeRidersList.isEmpty())
					break;
				Peer consumer = choosesConsumer(collab);
				performDonation(collab, consumer);
				removePeerIfFullyConsumed(consumer);
			}			
			removePeerIfFullyDonated(collab);
		}		
		nextStep();
	}
	
	/**
	 * Verify if the simulation ended and save the data. If it's not ended, then we redefine (calculate) if the
	 * collaborator will be in consuming state or not in the next step ( based in consumingStateProbability). 
	 */
	private void nextStep(){
		
		Simulator.logger.fine("Step "+this.currentStep);
		
		/** Join all collaborators, consumers, and free riders in their lists. **/
		donorsList.addAll(donatedPeersList);
		donatedPeersList.clear();
		
		for(int peerId : consumedPeersList){
			if(peers[peerId] instanceof FreeRider)
				freeRidersList.add(peerId);	
			else
				consumersCollabList.add(peerId);				
		}
		consumedPeersList.clear();
		
		
		//just to check if everything is OK until now
		Simulator.logger.finest("#donors("+donorsList.size()+") + #Consumers("+consumersCollabList.size()+") == #Collaborators("+numCollaborators+") :-)");
		Simulator.logger.finest("#Collaborators("+(donorsList.size()+consumersCollabList.size())+") + #FreeRiders("+freeRidersList.size()+") = #Peers("+this.numPeers+") :-)");		
		
			
		/** Join all collaborators in a list, and clear donorsList and consumersList, to fulfill them again. **/
		List <Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(donorsList);
		allCollaborators.addAll(consumersCollabList);
		Collections.sort(allCollaborators);
		donorsList.clear();
		consumersCollabList.clear();
		
		
		/** Fulfilling consumersCollabList and donorsList with collaborators with their new consuming status. **/
		for(int collabId : allCollaborators){
			/** Based in consumingStateProbability, we decide if the collaborator will consume or not in the next step. **/
			peers[collabId].setConsuming((this.randomGenerator.nextInt(100)+1 <= (this.consumingStateProbability*100)));
			if(peers[collabId].isConsuming()){				
				if((this.currentStep+1)<this.numSteps){
					peers[collabId].setDemand(this.peersDemand);
					((Collaborator)peers[collabId]).getCapacitySuppliedHistory()[currentStep+1] = ((Collaborator)peers[collabId]).getCapacitySuppliedHistory()[currentStep];
					peers[collabId].getRequestedHistory()[this.currentStep+1] = this.peersDemand-this.peersCapacity;
					peers[collabId].getConsumedHistory()[this.currentStep+1] = 0;
					peers[collabId].setDemand(peers[collabId].getDemand()-this.peersCapacity);
					
					consumersCollabList.add(collabId);
				}
			}
			else{											//capacitySupplied is updated based on capacitySuppliedReferenceValue
				if((this.currentStep+1)<this.numSteps){
					peers[collabId].setDemand(0);
					((Collaborator)peers[collabId]).setCapacitySupplied(((Collaborator)peers[collabId]).getCapacitySuppliedReferenceValue());
					((Collaborator)peers[collabId]).getCapacitySuppliedHistory()[currentStep+1] = ((Collaborator)peers[collabId]).getCapacitySuppliedReferenceValue();
					((Collaborator)peers[collabId]).getRequestedHistory()[this.currentStep+1] = 0;
					
					donorsList.add(collabId);	
				}
			}
		}
			
		/** Refreshing Free Riders' demand. **/
		for(Integer fId : freeRidersList){
			if((this.currentStep+1)<this.numSteps){
				peers[fId].setDemand(this.peersDemand);			
				peers[fId].getRequestedHistory()[this.currentStep+1] = this.peersDemand-this.peersCapacity;
				peers[fId].getConsumedHistory()[this.currentStep+1] = 0;
				peers[fId].setDemand(peers[fId].getDemand()-this.peersCapacity);
			}
		}
			
		/** If in dynamic context update capacity supplied by collaborators. **/
		if(this.dynamic)
			this.updateCapacitySupplied();

		/** Set next step.**/
		this.currentStep++;
		if(this.currentStep<this.numSteps){
			performCurrentStepDonations();
		}
		else{
			if(Simulator.logger.isLoggable(Level.INFO))
				printSummary();			
			exportData();
		}			
	}

	
	
	
	
	
	/*********************************************************************************
	 * 									Auxiliary methods.							 *
	 *********************************************************************************/
	
	/**
	 * Randomly chooses a collaborator to donate.
	 * @return the collaborator that is not consuming, randomly choosed, or null if something strange happens
	 */
	private Collaborator choosesCollaboratorToDonate(){
		int id = this.donorsList.get(anyPeer(this.donorsList));
		return peers[id] instanceof Collaborator?(Collaborator)peers[id]:null;
	}
	
	/**
	 * Chooses a peer to consume the resources of next donation. Based on interactions history or randomly. 
	 * @param collaborator the collaborator who will donate his resources
	 * @return the peer choosed to consume, a consuming collaborator or a free rider
	 */
	private Peer choosesConsumer(Collaborator collaborator){
	
		/**
		 * This peer has interacted at least once, then, we should get the peer with highest reputation
		 * in the TreeMap structure. (if there is at least one that is a consumer on this step)
		 */
		if(!collaborator.getInteractions().isEmpty()){									
			int index = -1;								//index used to retrieve the peer. It stores the peerId.
			int nth = 1;								//nth peer with highest reputation
			
			/** Id = -1, so that we won't find it on consumedPeersList in first iteration.
			 *  consuming should be false, so that we assure entering in while loop.
			 **/
			Peer p = new Peer(0, -1, false, 0);		
			
			/** Stop searching if the peers has not consumed and is in consuming state. **/
			while(!(!this.consumedPeersList.contains((Integer)p.getPeerId()) && p.isConsuming())){
				try{
					index = collaborator.getThePeerIdWithNthBestReputation(nth);
					if(index==-1)											//we couldn't find a peer that hasn't interacted yet, and wants to consume
						break;
					p = peers[index];										//update the peer being evaluated
				}catch(ArrayIndexOutOfBoundsException ex){
					ex.printStackTrace();
					return null;
				}
				nth++;								
			}
			
			/** The case that we find a candidate (Collaborator)! **/
			if(index>-1){
				if(p instanceof Collaborator)
					return (Collaborator) p;
				else{
					Simulator.logger.severe("For some reason, the consumer (object) retrieved is not a FreeRider nor a Collaborator... You must check what is happening!");
					System.exit(0);
					return null;
				}
			}
		}
		
		/**
		 * If we got here, then we had candidates in the Reputation List but they were not in consuming state, at this turn, 
		 * or they might already have consumed in this step.
		 * Solution: choose randomly from consumersCollabList + freeRidersList.
		 */			
		List <Integer> allConsumers = new ArrayList<Integer>();
		allConsumers.addAll(this.consumersCollabList);
		allConsumers.addAll(this.freeRidersList);
		int index = anyPeer(allConsumers);
		if(peers[allConsumers.get(index)] instanceof Collaborator)			
			return (Collaborator) peers[allConsumers.get(index)];
		else if(peers[allConsumers.get(index)] instanceof FreeRider)	//lets assure
			return (FreeRider) peers[allConsumers.get(index)];
		else{
			Simulator.logger.severe("For some reason, the consumer peer id retrieved was < 0 (ArrayOutOfBounds...). You must check what is happening!");
			return null;
		}	
	}

	/**
	 * Performs the donation from a donor to a consumer.
	 * @param donor the collaborator peer who will donate his spare resources
	 * @param consumer the peer who will consume the resources
	 */
	private void performDonation(Collaborator donor, Peer consumer){
		
		Interaction artificialInteraction = new Interaction(donor, consumer);		//just to retrieve the real interaction by comparison
		
		double valueToBeDonated = Math.min(consumer.getDemand(), donor.getCapacitySupplied());	//value to be donated is the minimum between consumers demand and donors capacity
		
		/** The peers have already interacted. **/
		if(donor.getInteractions().contains(artificialInteraction) && consumer.getInteractions().contains(artificialInteraction)){
			
			int index = donor.getInteractions().indexOf(artificialInteraction);
			Interaction interaction = donor.getInteractions().get(index);			//retrieve the interaction object with its history
						
			/**
			 * This if-else is necessary because we don't want to duplicate the interaction (objects) of peers A and B.
			 * We have only one object "called" interaction(A,B). And we carefully deal the data when we want the
			 * object "called" interaction (B,A).
			 */
			
			if(donor.getPeerId() == interaction.getPeerA().getPeerId())
				interaction.peerADonatesValue(valueToBeDonated);		//update the interaction of peerA
			else if(donor.getPeerId() == interaction.getPeerB().getPeerId())
				interaction.peerBDonatesValue(valueToBeDonated);		//update the interaction of peerB
			else{
				Simulator.logger.severe("The interaction exists, but for some reason, I could not identify who is peer A (the donor) or peer B (the consumer). "
						+ "You must check what is happening!");
				System.exit(0);
				return;
			}
			
			double consumerReputation = (donor.getPeerId() == interaction.getPeerA().getPeerId()?
					NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog):
						NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog));
			
			double donorReputation = (donor.getPeerId() == interaction.getPeerA().getPeerId()?
					NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog):
						NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
			
			/** Add the treeMap donor reputation, and add also the consumer reputation (if he is not a free rider). **/
			int consumerIndex = donor.getPeersReputations().indexOf(new PeerReputation(consumer.getPeerId(), 0));
			if(consumerIndex==-1){		//Add
				donor.getPeersReputations().add(new PeerReputation(consumer.getPeerId(), consumerReputation));
				consumer.getPeersReputations().add(new PeerReputation(donor.getPeerId(), donorReputation));
			}
			/** Update the treeMap donor reputation, and update also the consumer reputation (if he is not a free rider). **/
			else{
				donor.getPeersReputations().get(consumerIndex).setReputation(consumerReputation);
				int donorIndex = consumer.getPeersReputations().indexOf(new PeerReputation(donor.getPeerId(), 0));
				if(donorIndex != -1)
					consumer.getPeersReputations().get(donorIndex).setReputation(donorReputation);
				else{
					Simulator.logger.severe("Some problems happened when updateing donorsReputation. It seems that consumer and donor already interacted "
							+ "but the consumer doesn't have any reference to donor in its reputation list."
							+ "You must check what is happening!");
					System.exit(0);
					return;
				}
				
			}
			this.sortReputations(donor, consumer);
		}
		/**
		 * The peers never interacted and the consumer is a Collaborator.
		 */
		else if(consumer instanceof Collaborator){
			Interaction interaction = new Interaction(donor, consumer);
			interaction.peerADonatesValue(valueToBeDonated);		
			donor.getInteractions().add(interaction);																		//adds the new interaction to the donor peer	
			consumer.getInteractions().add(interaction);																	//adds the new interaction to the consumer peer
			
			Simulator.logger.finest("donor: "+donor.getPeerId()+". Consumer: "+consumer.getPeerId()+".");
			Simulator.logger.finest("Donation: "+valueToBeDonated+". Reputation: "+NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
			
			/**
			 * Add the new PeerReputation to the treeMap of the donor and consumer reputation (if he is not a free rider). 
			 */
			donor.getPeersReputations().add(new PeerReputation(consumer.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog)));
			consumer.getPeersReputations().add(new PeerReputation(donor.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog)));
			
			Simulator.logger.finest("Reputação do consumidor em relação ao doador: "+NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
			Simulator.logger.finest("Reputação do doador em relação ao consumidor: "+NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog));
			this.sortReputations(donor, consumer);
		}
		/**
		 * The peers never interacted and the consumer is a Free Rider.
		 */
		else{
			Simulator.logger.finest("Consumer (FreeRider): "+consumer.getPeerId()+".\n donor reputations will not include any free rider!");
			/** To calculate the probability of success of free riders in all steps **/
			((FreeRider)consumer).getSuccessHistory()[this.currentStep] = true;
		}
		
		donor.setCapacitySupplied(donor.getCapacitySupplied()-valueToBeDonated);//update the capacity supplied by donor
		donor.getDonatedHistory()[this.currentStep] += valueToBeDonated;			//update the donated amount (from donor) in this step
		consumer.setDemand(consumer.getDemand()-valueToBeDonated);					//update the demand of the consumer in the current step
		consumer.getConsumedHistory()[this.currentStep] += valueToBeDonated;		//update the consumed amount (from consumer) in this step
		
		
	}
	
	/**
	 * If the consumer already consumed everything he wanted, then we remove him from consumersList,
	 * and add to consumedPeersList, in order to know who has already consumed.
	 * 
	 * @param consumer
	 */
	private void removePeerIfFullyConsumed(Peer consumer){
		if(consumer.getDemand()==0){
			if(consumer instanceof FreeRider)
				this.freeRidersList.remove((Integer)consumer.getPeerId());
			else
				this.consumersCollabList.remove((Integer)consumer.getPeerId());
			this.consumedPeersList.add(consumer.getPeerId());
		}
		else if(consumer.getDemand()<0){
			Simulator.logger.severe("Consumer demand should never be smaller than 0. Some sheet happened here. "
					+ "We should find the origin of this bug!");
			System.exit(0);
		}	
	}
	
	/**
	 * Remove the donor from donorsList, and add to donatedPeersList, in order to know who has
	 * already donated.
	 * 
	 * @param donor
	 */
	private void removePeerIfFullyDonated(Collaborator donor){
		if(donor.getCapacitySupplied()==0){
			this.donorsList.remove((Integer)donor.getPeerId());
			this.donatedPeersList.add(donor.getPeerId());
		}
	}
	
	/**
	 * Update the capacity supplied of all collaborators, when they consume, based on their current and last fairness.	
	 */
	private void updateCapacitySupplied(){
		
		List <Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(donorsList);
		allCollaborators.addAll(consumersCollabList);
		
		for(int i : allCollaborators){		
			
			if(Simulator.peers[i] instanceof Collaborator){		
				Collaborator collaborator = (Collaborator) Simulator.peers[i];

//				if(collaborator.getConsumedHistory()[this.currentStep]>0 || collaborator.getCapacitySupplied()==0){
//				if(collaborator.getConsumedHistory()[this.currentStep]>0){
					
					if(this.currentStep>0){
						double currentConsumed = collaborator.getCurrentConsumed(this.currentStep);
						double currentDonated = collaborator.getCurrentDonated(this.currentStep);
						double currentFairness = Simulator.getFairness(currentConsumed, currentDonated);
						
						double lastConsumed = collaborator.getCurrentConsumed(this.currentStep-1);
						double lastDonated = collaborator.getCurrentDonated(this.currentStep-1);
						double lastFairness = Simulator.getFairness(lastConsumed, lastDonated);
						
						/** If my fairness is decreasing or equal to last fairness, try changing the behavior.**/
						if(currentFairness <= lastFairness){
//						if(currentFairness <= lastFairness || currentFairness > 1){
							collaborator.setIncreasingCapacitySupplied(!collaborator.isIncreasingCapacitySupplied());
						}
						
						/** Change it's capacity in order to achiever a greater fairness. **/
						if(collaborator.isIncreasingCapacitySupplied())				
							collaborator.setCapacitySuppliedReferenceValue(Math.min(1, collaborator.getCapacitySuppliedReferenceValue()+this.changingValue));	//try to increase the current capacitySuppliedReferenceValue
						else														//if we were decreasing and it decreased my fairness, keep decreasing
							collaborator.setCapacitySuppliedReferenceValue(Math.max(0, collaborator.getCapacitySuppliedReferenceValue()-this.changingValue));	//try to decrease the current capacitySuppliedReferenceValue
							
						collaborator.setCapacitySupplied(collaborator.getCapacitySuppliedReferenceValue());
						if((this.currentStep+1)<this.numSteps)
							collaborator.getCapacitySuppliedHistory()[this.currentStep+1] = collaborator.getCapacitySuppliedReferenceValue();
					}
//				}
			}
		}	
	}
	
	/**
	 * Given the amount consumed and donated, returns the fairness.
	 * 
	 * @param consumed
	 * @param donated
	 * @return
	 */
	public static double getFairness(double consumed, double donated){
		if(donated == 0)
			return -1;
		else
			return consumed/donated;
	}
			
	/**
	 * Print simulation summary: peers, ids, ammount donated, ammount consumed, satisfaction.
	 */
	private void printSummary(){
		this.currentStep--;		
		System.out.println("\n\n\n####################Peers Satisfactions#####################");
		for(Peer peer : Simulator.peers){
			
			double currentGranted = peer.getCurrentConsumed(this.currentStep);
			double currentRequested = peer.getCurrentRequested(this.currentStep);
			double currentSatisfaction = Simulator.getFairness(currentGranted, currentRequested);
			
			if(peer instanceof Collaborator){
				Collaborator collaborator = (Collaborator) peer;				
				System.out.println("Collaborator ID=["+collaborator.getPeerId()+"] ==> Satisfatcion: "+currentSatisfaction+"; Granted: "+currentGranted+"; Requested: "+currentRequested);
			}
			else{
				FreeRider freeRider = (FreeRider) peer;
				System.out.println("FreeRider ID=["+freeRider.getPeerId()+"] ==> Satisfatcion: "+currentSatisfaction+"; Granted: "+currentGranted+"; Requested: "+currentRequested);
			}
		}
	}

	/**
	 * Export the main data of simmulation to an excel (xlsx) file.
	 */
	private void exportData(){		
		WriteExcel2010 we = new WriteExcel2010(this.outputFile, this.numSteps);
		we.setupFile();		
//		we.fulfillFairness(peers);
		we.fulfillFairnessPerSteps(peers);
//		we.fulfillConsumptionData(peers);
//		we.fulfillRequestedData(peers);
		we.fulfillSatisfactionPerSteps(peers);
//		we.fulfillDonationData(peers);
		we.fulfillfreeRiderSatisfactionsData(peers);
		we.fulfillCapacitySuppliedData(peers);
		
		we.writeFile();
	}
	
	/**
	 * Randomly chooses an item of the list.
	 * @param peersList	 the list of integers (peer ids) on which we will randomly choose one
	 * @return the index of item randomly choosed
	 */
    private int anyPeer(List <Integer> peersList){    
        return this.anyPeerRandomGenerator.nextInt(peersList.size());
    }
    
    private void sortReputations(Collaborator donor, Peer consumer){
    	/** Sort the List by its reputations. **/
		Collections.sort(donor.getPeersReputations());
		if(!(consumer instanceof FreeRider))
			Collections.sort(consumer.getPeersReputations());
    }
    
}




///**
// * checando o seed
// */
//System.out.println();
//System.out.println("Step "+this.currentStep);
//Collections.sort(donorsList);
//Collections.sort(consumersCollabList);
//System.out.print("Donators: ");
//for(Integer index : donorsList){
//	System.out.print(peers[index].getPeerId()+",");
//}
//System.out.println();
//System.out.print("Consumers: ");
//for(Integer index : consumersCollabList){
//	System.out.print(peers[index].getPeerId()+",");
//}
//
//if(this.currentStep==9){
//	System.exit(0);
//}
//
///**
// * fim de checando o seed
// */