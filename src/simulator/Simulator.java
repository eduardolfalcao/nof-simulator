package simulator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.reputation.PeerReputation;
import utils.GenerateCsv;
import utils.WriteExcel2010;

public class Simulator {
	
	/**
	 * Simulation characteristics.
	 */
	private int numPeers;							//[FACTOR]: (collaborators + freeRiders)	
	private int numSteps;							//[FACTOR]: number of steps of the simulation
	
	//each array item is a group of collaborators with the same characteristics
	private double [] consumingStateProbability;	//[FACTOR]: probability of being in consuming state. Ranges (0,1).
	private int [] numberOfCollaborators;		//[FACTOR]: number of collaborators, formed in groups
	private int [] numberOfFreeRiders;		//[FACTOR]: number of free riders, formed in groups
	
	private int [] index;
	
	private double kappa;
	private String design;
	
	private int numCollaborators;
	private int numFreeRiders;
		
	/**
	 * NoF characteristics.
	 */
	private boolean dynamic;						//[FACTOR]: if the capacity supply changes dynamicly or not
	private boolean nofWithLog;					//if the reputations will be calculated with log or with sqrt
	private double fairnessLowerThreshold;			//the threshold in which fairness must have in order to prioritize satisfaction
	

	private boolean pairwise;
	
	/**
	 * Peers characteristics.
	 */
	private double [] peersDemand;					//[FACTOR]: the demand the peers will try to use from other peers
	private double [] peersCapacity;
	
	/**
	 * Collaborators characteristics.
	 */
	//private double capacitySupplied;				//capacity of resources that peers can donate 			[begins with 1]
	private double changingValue;					//value added or subtracted to/from capacitySupplied	[assumed 0.05]
	
	/**
	 * Other variables used in simulation.
	 */
	public static Peer peers [];					//all peers of the simulation
	
	private int currentStep;						//current step of simulation
	private List <Integer> consumedPeersList;		//list of all peers that have already consumed (collaborators + free riders)
	private List <Integer> donatedPeersList;		//list of all peers (collaborators + free riders)
	private List <Integer> consumersCollabList;		//list of consumers collaborators
	private List <Integer> providersList;				//list of donors
	private List <Integer> freeRidersList;			//list of free-riders	
	private Random randomGenerator;					//to randomly define who is donor or consumer (besides collaborator or free rider) 
	
	
	private String outputFile;						//file to export Data
	private String f;
	
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
	public Simulator(int numPeers, int numSteps, double [] consumingStateProbability, int [] numberOfCollaborators, int [] numberOfFreeRiders, boolean dynamic, boolean nofWithLog,
			double fairnessLowerThreshold, double [] peersDemand, double [] capacitySupplied, double changingValue, long seed, Level level, String outputFile, boolean pairwise,
			double kappa, String design, int [] index, String f) {
		super();
		this.numPeers = numPeers;
		this.numSteps = numSteps;
		this.consumingStateProbability = consumingStateProbability;
		this.numberOfCollaborators = numberOfCollaborators;
		this.numberOfFreeRiders = numberOfFreeRiders;
		this.dynamic = dynamic;
		this.nofWithLog = nofWithLog;
		this.fairnessLowerThreshold = fairnessLowerThreshold;
		this.peersDemand = peersDemand;
		//this.capacitySupplied = capacitySupplied;
		this.changingValue = changingValue;
		this.currentStep = 0;
		Simulator.peers = new Peer[numPeers];
		this.consumedPeersList = new ArrayList<Integer> ();
		this.donatedPeersList = new ArrayList<Integer> ();
		this.consumersCollabList = new ArrayList<Integer> ();
		this.providersList = new ArrayList<Integer> ();
		this.freeRidersList = new ArrayList<Integer> ();		
		this.randomGenerator = new Random(seed);
		this.outputFile = outputFile;
		
		this.peersCapacity = capacitySupplied;
		
		/* Logger setup */
		Simulator.logger.setLevel(level);
		Simulator.logger.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
	    handler.setLevel(level);
	    logger.addHandler(handler);
	    
	    this.pairwise = pairwise;
	    this.kappa = kappa;
	    this.design = design;
	    this.index = index;
	    this.f = f;
	}

	/**
	 * Create the peers. When they're collaborators we decide (calculate) if the peer will begin consuming or not
	 * based in consumingStateProbability. The free riders are always in consuming state, despite they achieve
	 * or not success.
	 */
	private void setupSimulation(){
		//creating the peers (collaborators + freeRiders)
		int id = 0;
		for(int group = 0; group < numberOfCollaborators.length; group++){
			for(int j = 0; j < numberOfCollaborators[group]; j++){				
				//based in consumingStateProbability, we decide if the peer will begin consuming or not
				boolean beginsConsuming = (this.randomGenerator.nextInt(100)+1 <= (this.consumingStateProbability[group]*100));
				Collaborator collab = new Collaborator(this.peersDemand[group], id, beginsConsuming, this.consumingStateProbability[group], this.peersCapacity[group], this.numSteps);
				if(beginsConsuming)
					consumersCollabList.add(id);
				else
					providersList.add(id);
				
				Simulator.peers[id] = collab;
				Simulator.peers[id].setIndex(this.index[group]);
				id++;
			}						
		}
		
		for(int group = 0; group < numberOfFreeRiders.length; group++){
			for(int j = 0; j < numberOfFreeRiders[group]; j++){
				//freeRiders never donates, they are always in consuming state. At least, they are always trying to consume.
				Peer p = new FreeRider(this.peersCapacity[group], this.peersDemand[group], id, this.numSteps);
				freeRidersList.add(id);
				Simulator.peers[id] = p;
				Simulator.peers[id].setIndex(this.index[group]);
				id++;
			}
		}	
		
		for(int n : numberOfCollaborators)
			this.numCollaborators += n;
		
		for(int n : numberOfFreeRiders)
			this.numFreeRiders += n;
		
		Simulator.logger.info("Created: "+this.numCollaborators+" collaborators and "+this.numFreeRiders+" free riders.");

	}
	
	/**
	 * Starts the simulation.
	 */
	public void startSimulation(){
		Simulator.logger.info("Setting up simulation...");
		this.setupSimulation();
		Simulator.logger.info("Starting simulation...");
		
		for(int i = 0; i < this.numSteps; i++){
			this.performCurrentStepDonations();
		}
		
		if(Simulator.logger.isLoggable(Level.INFO))
			printSummary();			
		exportData();
	}
	
	/**
	 * Performs all donations of the current step.
	 */
	private void performCurrentStepDonations(){
		
		if(this.currentStep>0 && this.pairwise){
			updateLastConsumedAndDonatedInteractions();
		}
		
		Collaborator collab = null;
		
		/**
		 * While there is any collaborator willing to donate, try to choose one.
		 */
		while(!this.providersList.isEmpty()){			
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
			List alreadyConsumed = new ArrayList<Integer>();
			while(collab.getCapacityDonatedInThisStep() < collab.getMaxCapacityToSupply()){
				/** If a provider finishes the consumers list here, there's no further consumer... 
				 * Further, if the provider already tried to donate to everyone, but the local NoF didn't allow
				 * then, he won't donate to any peer.
				 **/
				if((this.consumersCollabList.isEmpty() && this.freeRidersList.isEmpty()))
					break;
								
				Peer consumer = choosesConsumer(collab, alreadyConsumed);
				
				/**
				 * If the provider already tried to donate to everyone, but the local NoF didn't allow
				 * then, he won't donate to any peer. Then, the method choosesConsumer returns null.
				 */
				if(consumer == null)
					break;
				
				performDonation(collab, consumer);
				removePeerIfFullyConsumed(consumer);
				
				//update alreadyConsumed
				alreadyConsumed.add(consumer.getPeerId());
			}			
			removePeerThatDonated(collab);	
		}
		nextStep();
	}
	
	

	/**
	 * Verify if the simulation ended and save the data. If it's not ended, then we redefine (calculate) if the
	 * collaborator will be in consuming state or not in the next step ( based in consumingStateProbability). 
	 */
	private void nextStep(){
		
		Simulator.logger.info("Step "+this.currentStep);
		
		/** Join all collaborators, consumers, and free riders in their lists. **/
		providersList.addAll(donatedPeersList);
		donatedPeersList.clear();
		
		for(int peerId : consumedPeersList){
			if(peers[peerId] instanceof FreeRider)
				freeRidersList.add(peerId);	
			else
				consumersCollabList.add(peerId);				
		}
		consumedPeersList.clear();
		
		
		//just to check if everything is OK until now
		Simulator.logger.finest("#providers("+providersList.size()+") + #Consumers("+consumersCollabList.size()+") == #Collaborators("+numCollaborators+") :-)");
		Simulator.logger.finest("#Collaborators("+(providersList.size()+consumersCollabList.size())+") + #FreeRiders("+freeRidersList.size()+") = #Peers("+this.numPeers+") :-)");		
		
			
		/** Join all collaborators in a list, and clear providersList and consumersList, to fulfill them again. **/
		List <Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(providersList);
		allCollaborators.addAll(consumersCollabList);
		Collections.sort(allCollaborators);
		providersList.clear();
		consumersCollabList.clear();
		
		
		/** Fulfilling consumersCollabList and providersList with collaborators with their new consuming status. **/
		for(int collabId : allCollaborators){
			/** Based in consumingStateProbability, we decide if the collaborator will consume or not in the next step. **/
			peers[collabId].setConsuming((this.randomGenerator.nextInt(100)+1 <= (((Collaborator)peers[collabId]).getConsumingStateProbability()*100)));
			if(peers[collabId].isConsuming()){				
				if((this.currentStep+1)<this.numSteps){
					peers[collabId].getRequestedHistory()[this.currentStep+1] = peers[collabId].getInitialDemand()-peers[collabId].getInitialCapacity();
					peers[collabId].getConsumedHistory()[this.currentStep+1] = 0;
					peers[collabId].setDemand(peers[collabId].getInitialDemand());
					((Collaborator)peers[collabId]).setCapacityDonatedInThisStep(0);
					
					consumersCollabList.add(collabId);
				}
			}
			else{											//capacitySupplied is updated based on capacitySuppliedReferenceValue
				if((this.currentStep+1)<this.numSteps){
					peers[collabId].setDemand(0);
					((Collaborator)peers[collabId]).setCapacityDonatedInThisStep(0);
					((Collaborator)peers[collabId]).getRequestedHistory()[this.currentStep+1] = 0;
					
					providersList.add(collabId);	
				}
			}
		}
			
		/** Refreshing Free Riders' demand. **/
		for(Integer fId : freeRidersList){
			if((this.currentStep+1)<this.numSteps){
				//peers[fId].setDemand(peers[fId].getInitialDemand());			
				peers[fId].getRequestedHistory()[this.currentStep+1] = peers[fId].getInitialDemand()-peers[fId].getInitialCapacity();
				peers[fId].getConsumedHistory()[this.currentStep+1] = 0;
				peers[fId].setDemand(peers[fId].getInitialDemand());
			}
		}
			
		/** If in dynamic context update capacity supplied by collaborators. **/
		if(this.dynamic)
			this.updateCapacitySupplied();

		/** Set next step.**/
		this.currentStep++;
//		if(this.currentStep<this.numSteps){
//			performCurrentStepDonations();
//		}
//		else{
//			if(Simulator.logger.isLoggable(Level.INFO))
//				printSummary();			
//			exportData();
//		}			
	}

	
	
	
	
	
	/*********************************************************************************
	 * 									Auxiliary methods.							 *
	 *********************************************************************************/
	
	/**
	 * Randomly chooses a collaborator to donate.
	 * @return the collaborator that is not consuming, randomly choosed, or null if something strange happens
	 */
	private Collaborator choosesCollaboratorToDonate(){
		int id = this.providersList.get(anyPeer(this.providersList));
		return peers[id] instanceof Collaborator?(Collaborator)peers[id]:null;
	}
	
	/**
	 * Chooses a peer to consume the resources of next donation. Based on reputations or randomly. 
	 * @param collaborator the collaborator who will donate his resources
	 * @return the peer chose to consume, a consuming collaborator or a free rider
	 */
	private Peer choosesConsumer(Collaborator collaborator, List <Integer> alreadyConsumed){
		
		
		List <Integer> consumingPeers = new ArrayList<Integer>();			//consuming peers that didn't consume
		consumingPeers.addAll(this.consumersCollabList);					//free riders arenot needed here, since they never have reputation > 0
		
		List <Integer> peersWithGoodReputation = new ArrayList<Integer>();	//peers with reputation>0 relative to the collaborator
		Collections.sort(collaborator.getPeersReputations());				//assure the reputation order
		for(int i = collaborator.getPeersReputations().size()-1; i >= 0 ; i--){
			if(collaborator.getPeersReputations().get(i).getReputation()>0 && !(alreadyConsumed.contains(collaborator.getPeersReputations().get(i).getId())))
				peersWithGoodReputation.add(collaborator.getPeersReputations().get(i).getId());
		}
		
		if(Simulator.logger.isLoggable(Level.FINEST)){
			System.out.println("########################################");
			System.out.println("Peers Reputations");
			for (PeerReputation peerReputation : collaborator.getPeersReputations()) 
				System.out.println("Id: "+peerReputation.getId()+"; Rep: "+peerReputation.getReputation());
			System.out.println("Peers With Good Reputations");
			System.out.println(peersWithGoodReputation);
		}
		
		
		for (Integer peerId : peersWithGoodReputation) {
			if(consumingPeers.contains(peerId) && peers[peerId].isConsuming())
				return peers[peerId];
		}
		
		/**
		 * If we got here, there is no collaborator willing to consume with reputation > 0.
		 * Then, we have to choose randomly between collaborators and free riders that want to consume. 
		 * Solution: choose randomly from consumersCollabList + freeRidersList.
		 */
		consumingPeers.addAll(this.freeRidersList);		
		for (int i = 0; i < alreadyConsumed.size(); i++) {
			if(consumingPeers.contains(alreadyConsumed.get(i)))
				consumingPeers.remove((Integer)alreadyConsumed.get(i));
		}			
		
		if(consumingPeers.size()==0)
			return null;
		else{
			int index = anyPeer(consumingPeers);
			return peers[consumingPeers.get(index)];
		}
		
	}
	
	

	/**
	 * Performs the donation from a provider to a consumer.
	 * @param provider the collaborator peer who will donate his spare resources
	 * @param consumer the peer who will consume the resources
	 */
	private void performDonation(Collaborator provider, Peer consumer){	
		
		if(!provider.getInteractions().contains(new Interaction(consumer, 0, 0))){	//just to retrieve the real interaction by comparison			
			//creates an interaction for the provider and for the consumer
			Interaction providersInteraction = new Interaction(consumer, provider.getInitialCapacity(), numSteps);
			provider.getInteractions().add(providersInteraction);
			provider.getPeersReputations().add(new PeerReputation(consumer.getPeerId(), 0));
			if(!(consumer instanceof FreeRider)){
				Interaction consumersInteraction = new Interaction(provider, consumer.getInitialCapacity(), numSteps);
				consumer.getInteractions().add(consumersInteraction);
				consumer.getPeersReputations().add(new PeerReputation(provider.getPeerId(), 0));
			}
		}			
		
		double donated = updateProvidersInteraction(provider, consumer);			//update the value donated in providers interaction
		updateConsumersInteraction(provider, consumer, donated);				//now we update the interaction values of consumer
		
			
		//since the interactions already exist, simply update them
		updateReputation(provider, consumer, provider.getInteractions().get(provider.getInteractions().indexOf(new Interaction(consumer, 0, 0))));
		if(!(consumer instanceof FreeRider))	//free riders don't use reputation for nothing
			updateReputation(provider, consumer, consumer.getInteractions().get(consumer.getInteractions().indexOf(new Interaction(provider, 0, 0))));
		
		this.sortReputations(provider, consumer);									//sort the reputations in both provider and consumer
		
		provider.getDonatedHistory()[this.currentStep] += donated;					//update the donated amount (from provider) in this step
		consumer.getConsumedHistory()[this.currentStep] += donated;					//update the consumed amount (from consumer) in this step
	}
				
		
		

	
	/**
	 * If the consumer already consumed everything he wanted, then we remove him from consumersList,
	 * and add to consumedPeersList, in order to know who has already consumed.
	 * 
	 * @param consumer
	 */
	private void removePeerIfFullyConsumed(Peer consumer){
		if(consumer.getDemand()==consumer.getInitialCapacity()){
			if(consumer instanceof FreeRider)
				this.freeRidersList.remove((Integer)consumer.getPeerId());
			else
				this.consumersCollabList.remove((Integer)consumer.getPeerId());
			this.consumedPeersList.add(consumer.getPeerId());
		}
		else if(consumer.getDemand()<consumer.getInitialCapacity()){
			Simulator.logger.severe("Consumer demand should never be smaller than consumer.getInitialCapacity(). Some sheet happened here. "
					+ "We should find the origin of this bug!");
			System.out.println(consumer.getDemand());
			System.exit(0);
		}	
	}
	
	/**
	 * Remove the provider from donorsList, and add to donatedPeersList, in order to know who has
	 * already donated.
	 * 
	 * @param provider
	 */
	private void removePeerThatDonated(Collaborator provider){
		this.providersList.remove((Integer)provider.getPeerId());
		this.donatedPeersList.add(provider.getPeerId());		
	}
	
	/**
	 * Update the capacity supplied of all collaborators, when they consume, based on their current and last fairness.	
	 */
	private void updateCapacitySupplied(){
		
		List <Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(providersList);
		allCollaborators.addAll(consumersCollabList);
		
		for(int i : allCollaborators){		
			
			if(Simulator.peers[i] instanceof Collaborator){		
				Collaborator collaborator = (Collaborator) Simulator.peers[i];

				if(this.currentStep>0){
					
					if(pairwise){
						
						for (Interaction interaction : collaborator.getInteractions()) {
							
							double lastFairness = getFairness(interaction.getLastConsumed(), interaction.getLastDonated());	
							double currentFairness = getFairness(interaction.getConsumed(), interaction.getDonated());
							
							Simulator.logger.fine("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
							Simulator.logger.fine("LastFairness: "+lastFairness+"; CurrentFairness: "+currentFairness);
							Simulator.logger.fine("LastConsumed: "+interaction.getLastConsumed()+"; LastDonated: "+interaction.getLastDonated());
							Simulator.logger.fine("CurrentConsumed: "+interaction.getConsumed()+"; CurrentDonated: "+interaction.getDonated());
							
							boolean change = false;
							if(this.currentStep>1000){
							if(currentFairness>=0){
								/** If my fairness is decreasing or equal to last fairness, then:
								 *  	1 - if I was diminishing the capacity supplied and it did not work, then try something different, try increasing it;
								 *  	2 - if I was increasing the capacity supplied, and it did not work, then try something different, try decreasing it, 
								 *  		but only if my fairness is lower than a certain threshold, otherwise, I will prioritize satisfaction, once I already
								 *  		have a nice fairness value. **/
								if(currentFairness <= lastFairness){
									if(!interaction.isIncreasingCapacity())
										interaction.setIncreasingCapacity(true);
									else{
										if(currentFairness < this.fairnessLowerThreshold)
											interaction.setIncreasingCapacity(false);
									}
								}
								change = true;
							}
							}
							else{
								if(currentFairness>=0){
									/** If my fairness is decreasing or equal to last fairness, then:
									 *  	1 - if I was diminishing the capacity supplied and it did not work, then try something different, try increasing it;
									 *  	2 - if I was increasing the capacity supplied, and it did not work, then try something different, try decreasing it, 
									 *  		but only if my fairness is lower than a certain threshold, otherwise, I will prioritize satisfaction, once I already
									 *  		have a nice fairness value. **/
									if(currentFairness <= lastFairness){
										if(!interaction.isIncreasingCapacity())
											interaction.setIncreasingCapacity(true);
										else{
											if(currentFairness < this.fairnessLowerThreshold)
												interaction.setIncreasingCapacity(false);
										}
									}
									change = true;
								}
							}
							
								
							Simulator.logger.fine("LastMaxCapacitySupplied: "+interaction.getMaxCapacitySupplied());
							
							
							if(change){
								/** Change it's capacity in order to achiever a greater fairness. **/							
//								double maxLim = Math.min(collaborator.getInitialDemand()-collaborator.getInitialCapacity(), collaborator.getInitialCapacity()); 
								double maxLim = collaborator.getInitialCapacity();
									
								if(interaction.isIncreasingCapacity())		//try to increase the current maxCapacitySupplied
									interaction.setMaxCapacitySupplied(Math.min(maxLim, interaction.getMaxCapacitySupplied()+(this.changingValue*interaction.getInitialCapacity())));	
								else										//try to decrease the current maxCapacitySupplied
									interaction.setMaxCapacitySupplied(Math.max(0, Math.min(maxLim, interaction.getMaxCapacitySupplied()-(this.changingValue*interaction.getInitialCapacity()))));
							}
							
							interaction.getCapacitySuppliedHistory()[this.currentStep] = interaction.getMaxCapacitySupplied();
							
							
							Simulator.logger.fine("CurrentMaxCapacitySupplied: "+interaction.getMaxCapacitySupplied());
							Simulator.logger.fine("**********************************************************************");
							
						}
						
					}					
					
							
					double currentConsumed = collaborator.getCurrentConsumed(this.currentStep);
					double currentDonated = collaborator.getCurrentDonated(this.currentStep);
					double currentFairness = Simulator.getFairness(currentConsumed, currentDonated);
						
					collaborator.getFairnessHistory()[this.currentStep] = currentFairness;
						
					double lastConsumed = collaborator.getCurrentConsumed(this.currentStep-1);
					double lastDonated = collaborator.getCurrentDonated(this.currentStep-1);
					double lastFairness = Simulator.getFairness(lastConsumed, lastDonated);
					
					boolean change = false;	
					if(this.currentStep>1000){
					if(currentFairness>=0){
						/** If my fairness is decreasing or equal to last fairness, then:
						 *  	1 - if I was diminishing the capacity supplied and it did not work, then try something different, try increasing it;
						 *  	2 - if I was increasing the capacity supplied, and it did not work, then try something different, try decreasing it, 
						 *  		but only if my fairness is lower than a certain threshold, otherwise, I will prioritize satisfaction, once I already
						 *  		have a nice fairness value. **/
						if(currentFairness <= lastFairness){
							if(!collaborator.isIncreasingCapacitySupplied()){
								collaborator.setIncreasingCapacitySupplied(true);
							}
							else{
								if(currentFairness < this.fairnessLowerThreshold){
									collaborator.setIncreasingCapacitySupplied(false);
								}
							}
						}
						change = true;
					}
					}
					else{
						if(currentFairness>=0){
							/** If my fairness is decreasing or equal to last fairness, then:
							 *  	1 - if I was diminishing the capacity supplied and it did not work, then try something different, try increasing it;
							 *  	2 - if I was increasing the capacity supplied, and it did not work, then try something different, try decreasing it, 
							 *  		but only if my fairness is lower than a certain threshold, otherwise, I will prioritize satisfaction, once I already
							 *  		have a nice fairness value. **/
							if(currentFairness <= lastFairness){
								if(!collaborator.isIncreasingCapacitySupplied()){
									collaborator.setIncreasingCapacitySupplied(true);
								}
								else{
									if(currentFairness < this.fairnessLowerThreshold){
										collaborator.setIncreasingCapacitySupplied(false);
									}
								}
							}
							change = true;
						}
					}
					
					if(change){
//						double maxLim = Math.min(collaborator.getInitialCapacity(), collaborator.getInitialDemand()-collaborator.getInitialCapacity());
						double maxLim = collaborator.getInitialCapacity();
							
						/** Change it's capacity in order to achieve a greater fairness. **/
						if(collaborator.isIncreasingCapacitySupplied())				
							collaborator.setMaxCapacityToSupply(Math.min(maxLim, collaborator.getMaxCapacityToSupply()+(this.changingValue*collaborator.getInitialCapacity())));	//try to increase the current capacitySuppliedReferenceValue
						else
							collaborator.setMaxCapacityToSupply(Math.max(0, Math.min(maxLim,collaborator.getMaxCapacityToSupply()-(this.changingValue*collaborator.getInitialCapacity()))));	//try to decrease the current capacitySuppliedReferenceValue
					}
					
					if((this.currentStep+1)<this.numSteps)
						collaborator.getCapacitySuppliedHistory()[this.currentStep+1] = collaborator.getMaxCapacityToSupply();
				}
			}
		}	
		
		
		
		Simulator.logger.fine("FIM Update capacity supplied");
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
	 * 
	 * @param consumed
	 * @param requested
	 * @return
	 */
	public static double getSatisfaction(double consumed, double requested){
		return 	getFairness(consumed, requested);
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
		
		for(Peer peer : Simulator.peers){
			System.out.println("\n\n\n####################Collaborators Capacity Supplied#####################");
			System.out.println("Demanda = 0.5C");
			if(peer instanceof Collaborator){
				Collaborator collaborator = (Collaborator) peer;
				if((collaborator.getInitialDemand()-collaborator.getInitialCapacity())/collaborator.getInitialCapacity() == 0.5){
					System.out.print("Collaborator ID=["+collaborator.getPeerId()+"] ==> ");
//					for(double supplied : collaborator.getCapacitySuppliedHistory()){
//						System.out.print(supplied+" ");
//					}
				}
				
			}
		}
	}

	/**
	 * Export the main data of simmulation to an excel (xlsx) file.
	 */
	private void exportData(){		
		
		GenerateCsv csvGen = new GenerateCsv(this.outputFile, this.numSteps, this);
		csvGen.outputPeers();
		//csvGen.outputCollaborators();
//		csvGen.outputCapacitySupplied();
		//csvGen.outputFreeRiders();
		
//		WriteExcel2010 we = new WriteExcel2010(this.outputFile, this.numSteps);
//		we.setupFile();
//		we.fulfillFairnessPerSteps(peers);
//		we.fulfillSatisfactionPerSteps(peers);
//		we.fulfillfreeRiderSatisfactionsData(peers);
//		we.setupFileLastStep();		
//		we.fulfillFairnessLastStep(peers);
//		we.fulfillSatisfactionLastStep(peers);
//		we.fulfillfreeRiderSatisfactionsLastStep(peers);
	
		
//		we.writeFile();
//		
//		if(this.dynamic){
//			WriteExcel2010 we2 = new WriteExcel2010(this.outputFile.replace(".xlsx","-Contention.xlsx"), this.numSteps);
//			we2.setupFile2();	
//			we2.fulfillContentionData(peers);		
//			we2.writeFile();
//		}
	}
	
	int global = 0;
	
	private double updateProvidersInteraction(Collaborator provider, Peer consumer){
		
		int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 0));
		Interaction interaction = provider.getInteractions().get(index);		//retrieve the interaction object with its history		
		
		double maxToBeDonated = 0;
		
		boolean newComer = true;
		if(interaction.getDonated()>0 || interaction.getConsumed()>0)
			newComer = false;
		
		if(dynamic){
			double fairness = getFairness(interaction.getConsumed(), interaction.getDonated());
			
			if(newComer || fairness<=0 || !pairwise) 
				maxToBeDonated = provider.getMaxCapacityToSupply();
			else
				maxToBeDonated = interaction.getMaxCapacitySupplied();	
		}
		else
			maxToBeDonated = provider.getInitialCapacity();
		
			
		double consumerDemand = consumer.getDemand()-consumer.getInitialCapacity();
		double amountThatCouldBeDonated = Math.min(consumerDemand, maxToBeDonated);
		double spareResources = maxToBeDonated - provider.getCapacityDonatedInThisStep();
		double valueToBeDonated = Math.max(0,Math.min(spareResources, amountThatCouldBeDonated));	
		interaction.donate(valueToBeDonated);
		
		//update the capacity donated 
		provider.setCapacityDonatedInThisStep(provider.getCapacityDonatedInThisStep()+valueToBeDonated);
				
		return valueToBeDonated;
	}
	
	private void updateLastConsumedAndDonatedInteractions() {
		List<Integer> allCollaborators = new ArrayList<Integer>();
		allCollaborators.addAll(consumersCollabList);
		allCollaborators.addAll(providersList);
		for (Integer id : allCollaborators) {
			for (Interaction interaction : peers[id].getInteractions())
				interaction.updateLastValues();
		}		
	}
	
	private void updateConsumersInteraction(Collaborator provider, Peer consumer, double consumed){
		if(!(consumer instanceof FreeRider)){
			int index = consumer.getInteractions().indexOf(new Interaction(provider, 0, 0));
			Interaction interaction = consumer.getInteractions().get(index);		//retrieve the interaction object with its history		
			interaction.consume(consumed);
		}
		consumer.setDemand(Math.max(0,consumer.getDemand()-consumed));
	}
	
	private void updateReputation(Collaborator provider, Peer consumer, Interaction interaction){	//we have to call it twice: each for the interaction of a peer		
		double reputation = NetworkOfFavors.calculateLocalReputation(interaction.getConsumed(), interaction.getDonated(), nofWithLog);
		if(interaction.getPeerB() == consumer){
			int consumerIndex = provider.getPeersReputations().indexOf(new PeerReputation(consumer.getPeerId(), 0));
			provider.getPeersReputations().get(consumerIndex).setReputation(reputation);
		}
		else{
			int providerIndex = consumer.getPeersReputations().indexOf(new PeerReputation(provider.getPeerId(), 0));
			consumer.getPeersReputations().get(providerIndex).setReputation(reputation);
		}
	}
	
	/**
	 * Randomly chooses an item of the list.
	 * @param peersList	 the list of integers (peer ids) on which we will randomly choose one
	 * @return the index of item randomly choosed
	 */
    private int anyPeer(List <Integer> peersList){    
        return this.randomGenerator.nextInt(peersList.size());
    }
    
    private void sortReputations(Collaborator provider, Peer consumer){
    	/** Sort the List by its reputations. **/
		Collections.sort(provider.getPeersReputations());
		if(!(consumer instanceof FreeRider))
			Collections.sort(consumer.getPeersReputations());
    }

	/**
	 * @return the kappa
	 */
	public double getKappa() {
		return kappa;
	}
	
	public String getDesign() {
		return design;
	}

	/**
	 * @return the pairwise
	 */
	public boolean isPairwise() {
		return pairwise;
	}

	/**
	 * @return the f
	 */
	public String getF() {
		return f;
	}
    
	/**
	 * @return the fairnessLowerThreshold
	 */
	public double getFairnessLowerThreshold() {
		return fairnessLowerThreshold;
	}
}
