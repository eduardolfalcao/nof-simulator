package simulator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.peerid.PeerReputation;
import utils.WriteExcel;
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
	
	/**
	 * Collaborators characteristics.
	 */
	private double capacitySupplied;				//capacity of resources that peers can donate 			[begins with 1]
	private int returnLevelVerificationTime;		//times in steps to measure the necessity of supplying more or less resources. [assumed 10]
	private double changingValue;					//value added or subtracted to/from capacitySupplied	[assumed 0.05]
	
	/**
	 * Other variables used in simulation.
	 */
	public static Peer peers [];					//all peers of the simulation
	
	private int currentStep;						//current step of simulation
	private List <Integer> consumedPeersList;		//list of all peers that have already consumed (collaborators + free riders)
	private List <Integer> donatedPeersList;		//list of all peers (collaborators + free riders)
	private List <Integer> consumersCollabList;		//list of consumers collaborators
	private List <Integer> donatorsList;			//list of donators
	private List <Integer> freeRidersList;			//list of free-riders	
	private Random randomGenerator;					//to randomly retrieve an element from ArrayList
	private int numCollaborators;					//number of collaborators
	
	
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
	 */
	public Simulator(int numPeers, int numSteps, double consumingStateProbability, double percentageCollaborators, boolean dynamic, boolean nofWithLog,
			double peersDemand, double capacitySupplied, int returnLevelVerificationTime, double changingValue, Level level) {
		super();
		this.numPeers = numPeers;
		this.numSteps = numSteps;
		this.consumingStateProbability = consumingStateProbability;
		this.percentageCollaborators = percentageCollaborators;
		this.dynamic = dynamic;
		this.nofWithLog = nofWithLog;
		this.peersDemand = peersDemand;
		this.capacitySupplied = capacitySupplied;
		this.returnLevelVerificationTime = returnLevelVerificationTime;
		this.changingValue = changingValue;
		this.currentStep = 0;
		Simulator.peers = new Peer[numPeers];
		this.consumedPeersList = new ArrayList<Integer> ();
		this.donatedPeersList = new ArrayList<Integer> ();
		this.consumersCollabList = new ArrayList<Integer> ();
		this.donatorsList = new ArrayList<Integer> ();
		this.freeRidersList = new ArrayList<Integer> ();
		this.randomGenerator = new Random();
		
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
	public void setupSimulation(){
		//creating the peers (collaborators + freeRiders)		
		numCollaborators = (int) Math.ceil(this.numPeers*this.percentageCollaborators);
		
		for(int i = 0; i < numCollaborators; i++){			
			//based in consumingStateProbability, we decide if the peer will begin consuming or not
			boolean beginsConsuming = (this.randomGenerator.nextInt(100)+1 <= (this.consumingStateProbability*100));
			Collaborator collab = null;
			if(beginsConsuming){
				collab = new Collaborator(this.peersDemand, i, beginsConsuming, this.capacitySupplied, this.numSteps);
				collab.getCapacitySuppliedHistory()[0]=0;
				consumersCollabList.add(i);
			}				
			else{
				collab = new Collaborator(0, i, beginsConsuming, this.capacitySupplied, this.numSteps);
				collab.getCapacitySuppliedHistory()[0]=1;
				donatorsList.add(i);
			}
			Simulator.peers[i] = collab;
		}
		
		int numFreeRiders = numPeers - numCollaborators;
		for(int i = 0; i < numFreeRiders; i++){
			//freeRiders never donates, they are always in consuming state. At least, they are always trying to consume.
			Peer p = new FreeRider(this.peersDemand, i+numCollaborators, this.numSteps);
			freeRidersList.add(i+numCollaborators);
			Simulator.peers[i+numCollaborators] = p;
		}		
		
		Simulator.logger.info("Created: "+numCollaborators+" collaborators and "+numFreeRiders+" free riders.");

	}
	
	/**
	 * Starts the simulation.
	 */
	public void startSimulation(){
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
		while(!this.donatorsList.isEmpty()){			
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
				/** If a donator finish the consumers list here, there's no further consumer... **/
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
		donatorsList.addAll(donatedPeersList);
		donatedPeersList.clear();
		
		for(int peerId : consumedPeersList){
			if(peers[peerId] instanceof FreeRider)
				freeRidersList.add(peerId);				
			else
				consumersCollabList.add(peerId);					
		}
		consumedPeersList.clear();
		
		
		//just to check if everything is OK until now
		Simulator.logger.finest("#Donators("+donatorsList.size()+") + #Consumers("+consumersCollabList.size()+") == #Collaborators("+numCollaborators+") :-)");
		Simulator.logger.finest("#Collaborators("+(donatorsList.size()+consumersCollabList.size())+") + #FreeRiders("+freeRidersList.size()+") = #Peers("+this.numPeers+") :-)");
		
			
		/** Join all collaborators in a list, and clear donatorsList and consumersList, to fulfill them again. **/
		List <Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(donatorsList);
		allCollaborators.addAll(consumersCollabList);			
		donatorsList.clear();
		consumersCollabList.clear();
		
		/** Fulfilling consumersCollabList and donatorsList with collaborators with their new consuming status. **/
		for(int collabId : allCollaborators){
			/** Based in consumingStateProbability, we decide if the collaborator will consume or not in the next step. **/
			peers[collabId].setConsuming((this.randomGenerator.nextInt(100)+1 <= (this.consumingStateProbability*100))?true:false);				
			if(peers[collabId].isConsuming()){				
				peers[collabId].setDemand(this.peersDemand);
				if((this.currentStep+1)<this.numSteps)
					((Collaborator)peers[collabId]).getCapacitySuppliedHistory()[currentStep+1] = 0;
				consumersCollabList.add(collabId);
			}
			else{											//capacitySupplied is updated based on capacitySuppliedReferenceValue
				peers[collabId].setDemand(0);
				((Collaborator)peers[collabId]).setCapacitySupplied(((Collaborator)peers[collabId]).getCapacitySuppliedReferenceValue());
				if((this.currentStep+1)<this.numSteps)
					((Collaborator)peers[collabId]).getCapacitySuppliedHistory()[currentStep+1] = ((Collaborator)peers[collabId]).getCapacitySuppliedReferenceValue();
				donatorsList.add(collabId);	
			}
		}
			
		/** Refreshing Free Riders' demand. **/
		for(Integer fId : freeRidersList){
			/** To calculate the probability of success of free riders in all steps **/
			((FreeRider)peers[fId]).getSuccessHistory()[this.currentStep] = (((FreeRider)peers[fId]).getDemand())==this.peersDemand?false:true;
			peers[fId].setDemand(this.peersDemand);
		}
			
		/** If in dynamic context, update capacity supplied by collaborators (on the right time). **/
		if(this.dynamic && ((this.currentStep+1)%this.returnLevelVerificationTime)==0)
			this.updateCapacitySupplied();

		/** Set next step.**/
		this.currentStep++;
		if(this.currentStep<this.numSteps){
			performCurrentStepDonations();
		}
		else{
			System.out.println();
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
		int id = this.donatorsList.get(anyPeer(this.donatorsList));
		return peers[id] instanceof Collaborator?(Collaborator)peers[id]:null;
	}
	
	/**
	 * Chooses a peer to consume the resources of next donation. Based on interactions history or randomly. 
	 * @param c the collaborator who will donate his resources
	 * @return the peer choosed to consume, a consuming collaborator or a free rider
	 */
	private Peer choosesConsumer(Collaborator c){
		
		/**
		 * This peer has interacted at least once, then, we should get the peer with highest reputation
		 * in the TreeMap structure. (if there is at least one that is a consumer on this step)
		 */
		if(!c.getInteractions().isEmpty()){									
			int index = -1;								//index used to retrieve the peer. It stores the peerId.
			int nth = 1;								//nth peer with highest reputation
			
			/** Id = -1, so that we won't find it on consumedPeersList in first iteration.
			 *  consuming should be false, so that we assure entering in while loop.
			 **/
			Peer p = new Peer(0, -1, false, 0);		
			
			/** Stop searching if the peers has not consumed and is in consuming state. **/
			while(!(!this.consumedPeersList.contains((Integer)p.getPeerId()) && p.isConsuming())){
				try{
					index = c.getThePeerIdWithNthBestReputation(nth);
					if(index==-1)											//we couldn't find a peer that hasn't interacted yet, and wants to consume
						break;
					p = peers[index];										//update the peer being evaluated
				}catch(ArrayIndexOutOfBoundsException ex){
					ex.printStackTrace();
					return null;
				}
				nth++;								
			}
			
			/** The case that we find a candidate! **/
			if(index>-1){
				if(p instanceof Collaborator)
					return (Collaborator) p;
				else if(p instanceof FreeRider)
					return (FreeRider) p;
				else{
					Simulator.logger.severe("For some reason, the consumer (object) retrieved is not a FreeRider nor a Collaborator... You must check what is happening!");
					System.exit(0);
					return null;
				}
			}
			
			/**
			 * If we got here, then we had candidates in the tree Reputation but they were not in consuming state, at this turn, 
			 * or they might already have consumed in this step.
			 */
		}
		
		/**
		 * There are 2 options we might got here:
		 * 		1: we didn't find a candidate in consuming state, in the Reputation's tree (Interactions history).
		 * 		2: collaborator c has never interacted before
		 * Solution: choose randomly.
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
	 * Performs the donation from a donator to a consumer.
	 * @param donator the collaborator peer who will donate his spare resources
	 * @param consumer the peer who will consume the resources
	 */
	private void performDonation(Collaborator donator, Peer consumer){
		
		Interaction artificialInteraction = new Interaction(donator, consumer);		//just to retrieve the real interaction by comparison
		
		double valueToBeDonated = Math.min(consumer.getDemand(), donator.getCapacitySupplied());	//value to be donated is the minimum between consumers demand and donators capacity
		
		/** The peers have already interacted. **/
		if(donator.getInteractions().contains(artificialInteraction) && consumer.getInteractions().contains(artificialInteraction)){
			
			int index = donator.getInteractions().indexOf(artificialInteraction);
			Interaction interaction = donator.getInteractions().get(index);			//retrieve the interaction object with its history
						
			/**
			 * This if-else is necessary because we don't want to duplicate the interaction (objects) of peers A and B.
			 * We have only one object "called" interaction(A,B). And we carefully deal the data when we want the
			 * object "called" interaction (B,A).
			 */
			
			/** If the donator is peerA. Performs the donation, and updates the peers reputation. **/
			if(donator.getPeerId() == interaction.getPeerA().getPeerId()){
				interaction.peerADonatesValue(valueToBeDonated);		//update the interaction of peerA
				
				/** Add/Update the treeMap donator reputation, and Add/Update also the consumer reputation (if he is not a free rider). **/				
				PeerReputation peerRep = donator.getPeersReputations().getPeer(consumer.getPeerId());
				if(peerRep==null){		//Add
					donator.getPeersReputations().add(new PeerReputation(consumer.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog)));
					if(!(consumer instanceof FreeRider))
						consumer.getPeersReputations().add(new PeerReputation(donator.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog)));
				}
				else{					//Update
					peerRep.setReputation(NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
					if(!(consumer instanceof FreeRider))
						consumer.getPeersReputations().getPeer(donator.getPeerId()).setReputation(NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog));
				}
			}
			/** The donator is PeerB. Performs the donation, and updates the peers reputation. **/
			else if(donator.getPeerId() == interaction.getPeerB().getPeerId()){
				interaction.peerBDonatesValue(valueToBeDonated);		//update the interaction of peerB
				
				/** Add/Update the treeMap donator reputation, and Add/Update also the consumer reputation (if he is not a free rider). **/				
				PeerReputation peerRep = donator.getPeersReputations().getPeer(consumer.getPeerId());
				if(peerRep==null){		//Add
					donator.getPeersReputations().add(new PeerReputation(consumer.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog)));
					if(!(consumer instanceof FreeRider))
						consumer.getPeersReputations().add(new PeerReputation(donator.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog)));
				}
				else{					//Update
					peerRep.setReputation(NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog));
					if(!(consumer instanceof FreeRider))
						consumer.getPeersReputations().getPeer(donator.getPeerId()).setReputation(NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
				}
			}
			else{
				Simulator.logger.severe("The interaction exists, but for some reason, I could not identify who is peer A (the donator) or peer B (the consumer). "
						+ "You must check what is happening!");
				System.exit(0);
				return;
			}
		}
		/**
		 * The peers never interacted.
		 */
		else{
			Interaction interaction = new Interaction(donator, consumer);
			interaction.peerADonatesValue(valueToBeDonated);		
			donator.getInteractions().add(interaction);																		//adds the new interaction to the donator peer	
			consumer.getInteractions().add(interaction);																	//adds the new interaction to the consumer peer
			
			Simulator.logger.finest("Donator: "+donator.getPeerId()+". Consumer: "+consumer.getPeerId()+".");
			Simulator.logger.finest("Donation: "+valueToBeDonated+". Reputation: "+NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
			
			/**
			 * Add the new PeerReputation to the treeMap of the donator and consumer reputation (if he is not a free rider). 
			 */
			donator.getPeersReputations().add(new PeerReputation(consumer.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog)));
			if(!(consumer instanceof FreeRider))
				consumer.getPeersReputations().add(new PeerReputation(donator.getPeerId(), NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog)));
			
			Simulator.logger.finest("Reputação do consumidor em relação ao doador: "+NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerA(), interaction.getDonatedValueByPeerA(), this.nofWithLog));
			Simulator.logger.finest("Reputação do doador em relação ao consumidor: "+NetworkOfFavors.calculateLocalReputation(interaction.getConsumedValueByPeerB(), interaction.getDonatedValueByPeerB(), this.nofWithLog));
			
			System.out.println();
		}
		
		donator.setCapacitySupplied(donator.getCapacitySupplied()-valueToBeDonated);//update the capacity supplied by donator
		donator.getDonatedHistory()[this.currentStep] = valueToBeDonated;			//update the donated amount (from donator) in this step
		consumer.setDemand(consumer.getDemand()-valueToBeDonated);					//update the demand of the consumer in the current step
		consumer.getConsumedHistory()[this.currentStep] = valueToBeDonated;			//update the consumed amount (from consumer) in this step
		
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
	 * Remove the donator from donatorsList, and add to donatedPeersList, in order to know who has
	 * already donated.
	 * 
	 * @param donator
	 */
	private void removePeerIfFullyDonated(Collaborator donator){
		if(donator.getCapacitySupplied()==0){
			this.donatorsList.remove((Integer)donator.getPeerId());
			this.donatedPeersList.add(donator.getPeerId());
		}
	}
	
	/**
	 * Update the capacity supplied of all peers (collaborators) based on their current and last satisfaction.	
	 */
	private void updateCapacitySupplied(){
		
		List <Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(donatorsList);
		allCollaborators.addAll(consumersCollabList);
		
		for(int i : allCollaborators){		
			
			if(Simulator.peers[i] instanceof Collaborator){		
				
				Collaborator collaborator = (Collaborator) Simulator.peers[i];
				
				double currentDonated = collaborator.getCurrentDonated(this.currentStep);				
				double currentConsumed = collaborator.getCurrentConsumed(this.currentStep);
				double currentSatisfaction = Simulator.getSatisfaction(currentDonated, currentConsumed);

				double lastDonated = collaborator.getCurrentDonated((this.currentStep-this.returnLevelVerificationTime)<0?0:this.currentStep-this.returnLevelVerificationTime);
				double lastConsumed = collaborator.getCurrentConsumed((this.currentStep-this.returnLevelVerificationTime)<0?0:this.currentStep-this.returnLevelVerificationTime);
				double lastSatisfaction = Simulator.getSatisfaction(lastDonated, lastConsumed);
				
				if(currentSatisfaction==lastSatisfaction)						//keep the current capacitySuppliedReferenceValue
					continue;
				else if(currentSatisfaction>lastSatisfaction){					//try to increase the current capacitySuppliedReferenceValue
					if(collaborator.getCapacitySuppliedReferenceValue()<1)
						collaborator.setCapacitySuppliedReferenceValue(Math.min(1, collaborator.getCapacitySuppliedReferenceValue()+this.changingValue));
				}
				else {															//currentSatisfaction<lastSatisfaction
					if(collaborator.getCapacitySuppliedReferenceValue()>0)		//try to decrease the current capacitySuppliedReferenceValue
						collaborator.setCapacitySuppliedReferenceValue(Math.max(0, collaborator.getCapacitySuppliedReferenceValue()-this.changingValue));					
				}
				collaborator.setCapacitySupplied(collaborator.getCapacitySuppliedReferenceValue());
				if((this.currentStep+1)<this.numSteps)
					collaborator.getCapacitySuppliedHistory()[this.currentStep+1] = collaborator.getCapacitySuppliedReferenceValue();
			}
		}	
	}
	
	/**
	 * Given the amount donated and consumed, returns the satisfaction of the peer.
	 * 
	 * @param donated
	 * @param consumed
	 * @return
	 */
	public static double getSatisfaction(double donated, double consumed){		
		if(consumed == 0 && donated == 0)
			return 1;
		else if(consumed == 0)
			consumed = Double.MIN_VALUE;
		else if(donated == 0)
			donated = Double.MIN_VALUE;
			
		return consumed/donated;
	}
			
	/**
	 * Print simulation summary: peers, ids, ammount donated, ammount consumed, satisfaction.
	 */
	private void printSummary(){
		this.currentStep--;		
		System.out.println("\n\n\n####################Peers Satisfactions#####################");
		for(Peer peer : Simulator.peers){				
			if(peer instanceof Collaborator){
				Collaborator collaborator = (Collaborator) peer;
				
				double currentDonated = collaborator.getCurrentDonated(this.currentStep);
				double currentConsumed = collaborator.getCurrentConsumed(this.currentStep);
				double currentSatisfaction = Simulator.getSatisfaction(currentDonated, currentConsumed);
				
				System.out.println("Collaborator ID=["+collaborator.getPeerId()+"] ==> Satisfatcion: "+currentSatisfaction+"; Donated: "+currentDonated+"; Consumed: "+currentConsumed);
			}
			else{
				FreeRider freeRider = (FreeRider) peer;
				double currentDonated = 0;
				double currentConsumed = freeRider.getCurrentConsumed(this.currentStep);
				double currentSatisfaction = Simulator.getSatisfaction(currentDonated, currentConsumed);
				System.out.println("FreeRider ID=["+freeRider.getPeerId()+"] ==> Satisfatcion: "+currentSatisfaction+"; Donated: 0.0; Consumed: "+currentConsumed);
			}
		}
	}

	private void exportData(){
//		String fileName = "simulationExample";
//		WriteExcel we = new WriteExcel("/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation/"+fileName+".xls", this.numSteps);
//		we.setupFile();
//		try {
//			we.fulfillSatisfactions(peers);
//			we.fulfillSatisfactionsPerSteps(peers);
//			we.fulfillConsumptionData(peers);
//			we.fulfillDonationData(peers);
//			we.fulfillCapacitySuppliedData(peers);
//			we.fulfillfreeRiderSuccessData(peers);
//		} catch (RowsExceededException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		
//		try {
//			we.writeFile();
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String fileName = "simulationExample";
		WriteExcel2010 we = new WriteExcel2010("/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation/"+fileName+".xlsx", this.numSteps);
		we.setupFile();		
		we.fulfillSatisfactions(peers);
		we.fulfillSatisfactionsPerSteps(peers);
		we.fulfillConsumptionData(peers);
		we.fulfillDonationData(peers);
		we.fulfillCapacitySuppliedData(peers);
		we.fulfillfreeRiderSuccessData(peers);
		we.writeFile();
	}
	
	/**
	 * Randomly chooses an item of the list.
	 * @param peersList	 the list of integers (peer ids) on which we will randomly choose one
	 * @return the index of item randomly choosed
	 */
    private int anyPeer(List <Integer> peersList){    	
        return randomGenerator.nextInt(peersList.size());
    }

    
    
    
    
    
    
    
    
    
	
	/*******************************************
	 * Breaking visibility for testing methods *
	 *******************************************/
    
    //no nextStep();
    public void testPerformCurrentStepDonationsNoNextStep(){
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
				System.out.println("(ANTES) Collab id: "+collab.getPeerId()+". Capacity: "+collab.getCapacitySupplied());
				Peer p = choosesConsumer(collab);
				performDonation(collab, p);
				System.out.println("(DEPOIS) Collab id: "+collab.getPeerId()+". Capacity: "+collab.getCapacitySupplied());
			}
			
			/**
			 * Remove the donator from donatorsList, and add to donatedPeersList, in order
			 * to know who has already donated.
			 */
			this.donatorsList.remove((Integer)collab.getPeerId());
			this.donatedPeersList.add(collab.getPeerId());
		}
		
		//nextStep();
    }
    
    //no performCurrentStepDonations()
    public void testNextStepNoPerformCurrentStepDonations(){
		if(this.currentStep>=this.numSteps){
			//acabou... salva os dados em algum lugar
		}
		else{			
			
			/**
			 * Join all collaborators, consumers, and free riders in their lists.
			 */
			donatorsList.addAll(donatedPeersList);
			donatedPeersList.clear();
			
			for(int peerId : consumedPeersList){
				if(peers[peerId] instanceof FreeRider)
					freeRidersList.add(peerId);					
				else
					consumersCollabList.add(peerId);					
			}
			consumedPeersList.clear();

			
			//just to check if everything is OK until now
			if((donatorsList.size() + consumersCollabList.size())==numCollaborators)
				System.out.println("#Donators("+donatorsList.size()+") + #Consumers("+consumersCollabList.size()+") == #Collaborators("+numCollaborators+") :-)");
			
			
			/**
			 * Join all collaborators in a list, and clear donatorsList and consumersList,
			 * to fulfill them again.
			 */
			List <Integer> allCollaborators = new ArrayList<Integer>();
			allCollaborators.addAll(donatorsList);
			allCollaborators.addAll(consumersCollabList);			
			donatorsList.clear();
			consumersCollabList.clear();
			
			for(int collabId : allCollaborators){
				//based in consumingStateProbability, we decide if the collaborator will consume or not in the next step
				peers[collabId].setConsuming((this.randomGenerator.nextInt(100)+1 <= (this.consumingStateProbability*100))?true:false);
				if(peers[collabId].isConsuming()){
					peers[collabId].setDemand(this.peersDemand);
					((Collaborator)peers[collabId]).setCapacitySupplied(0);
					consumersCollabList.add(collabId);
				}
				else{
					peers[collabId].setDemand(0);
					((Collaborator)peers[collabId]).setCapacitySupplied(this.capacitySupplied);
					donatorsList.add(collabId);	
				}
			}
			
			for(Integer fId : freeRidersList){
				peers[fId].setConsuming(true);				//just to assure
				peers[fId].setDemand(this.peersDemand);
			}
				
			//performCurrentStepDonations();
		}			
	}
    
	public Collaborator testChoosesCollaboratorToDonate(){
		return this.choosesCollaboratorToDonate();
	}
	
	public int testAnyPeer(List <Integer> peersList){    	
        return this.anyPeer(peersList);
    }
	
	public Peer testChoosesConsumer(Collaborator c){
		return this.choosesConsumer(c);
	}
	
	public void testPerformDonation(Collaborator donator, Peer consumer){
		this.performDonation(donator, consumer);
	}
	
	public List<Integer> getConsumersCollabList() {
		return consumersCollabList;
	}

	public void setConsumersCollabList(List<Integer> consumersCollabList) {
		this.consumersCollabList = consumersCollabList;
	}

	public List<Integer> getDonatorsList() {
		return donatorsList;
	}

	public void setDonatorsList(List<Integer> donatorsList) {
		this.donatorsList = donatorsList;
	}

	public List<Integer> getFreeRidersList() {
		return freeRidersList;
	}

	public void setFreeRidersList(List<Integer> freeRidersList) {
		this.freeRidersList = freeRidersList;
	}	


	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public List<Integer> getDonatedPeersList() {
		return donatedPeersList;
	}

	public void setDonatedPeersList(List<Integer> donatedPeersList) {
		this.donatedPeersList = donatedPeersList;
	}

	public List<Integer> getConsumedPeersList() {
		return consumedPeersList;
	}

	public void setConsumedPeersList(List<Integer> consumedPeersList) {
		this.consumedPeersList = consumedPeersList;
	}
}




