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
import peer.State;
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
	
	
	private double kappa;
	private String design;
	
	private int numCollaborators;
	private int numFreeRiders;
		
	/**
	 * NoF characteristics.
	 */
	private boolean dynamic;						//[FACTOR]: if the capacity supply changes dynamicly or not
	private boolean nofWithLog;						//if the reputations will be calculated with log or with sqrt
	private double tauMin, tauMax;				//the threshold in which fairness must have in order to prioritize satisfaction
	
	

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
	public Peer peers [];					//all peers of the simulation
	
	private int currentStep;						//current step of simulation
	private List <Integer> consumedPeersList;		//list of all peers that have already consumed (collaborators + free riders)
	private List <Integer> donatedPeersList;		//list of all peers (collaborators + free riders)
	private List <Integer> consumersCollabList;		//list of consumers collaborators
	private List <Integer> providersList;				//list of donors
	private List <Integer> freeRidersList;			//list of free-riders	
	private Random randomGenerator;					//to randomly define who is donor or consumer (besides collaborator or free rider) 
	
	
	private String outputFile;						//file to export Data
	private String f;
	private double [] suppliedHistory;
	
	
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
			double tauMin, double tauMax, double [] peersDemand, double [] capacitySupplied, double changingValue, long seed, Level level, String outputFile, boolean pairwise,
			double kappa, String design, String f) {
		super();
		this.numPeers = numPeers;
		this.numSteps = numSteps;
		this.consumingStateProbability = consumingStateProbability;
		this.numberOfCollaborators = numberOfCollaborators;
		this.numberOfFreeRiders = numberOfFreeRiders;
		this.dynamic = dynamic;
		this.nofWithLog = nofWithLog;
		this.tauMin = tauMin;
		this.tauMax = tauMax;
		this.peersDemand = peersDemand;
		//this.capacitySupplied = capacitySupplied;
		this.changingValue = changingValue;
		this.currentStep = 0;
		this.peers = new Peer[numPeers];
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
	    this.f = f;
	    this.suppliedHistory = new double[numSteps];
	    //this.df = new DecimalFormat("#.00000"); //df.format(0.912385); // Imprime 0,91238
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
				if(beginsConsuming){
					consumersCollabList.add(id);
					collab.getStateHistory()[0] = State.CONSUMING;
				}
				else{
					providersList.add(id);
					collab.getStateHistory()[0] = State.PROVIDING;
				}
				
				this.peers[id] = collab;
				id++;
			}						
		}
		
		for(int group = 0; group < numberOfFreeRiders.length; group++){
			for(int j = 0; j < numberOfFreeRiders[group]; j++){
				//freeRiders never donates, they are always in consuming state. At least, they are always trying to consume.
				Peer p = new FreeRider(0, 40, id, this.numSteps);	//demanda infinita pra consumir os recursos excedentes
				freeRidersList.add(id);
				this.peers[id] = p;
				id++;
			}
		}
		
		for(int n : numberOfCollaborators)
			this.numCollaborators += n;
		
		for(int n : numberOfFreeRiders)
			this.numFreeRiders += n;
		
		for(int idProvider : providersList)
			this.suppliedHistory[0] += ((Collaborator)this.peers[idProvider]).getMaxCapacityToSupply();
		
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
			
			// There will always be a consumer: the eager free rider.
			collab = this.choosesCollaboratorToDonate();	//randomly chooses who will donate
			
			/**
			 * Keeps choosing consumer and donating until supply all his capacity.
			 */
			List alreadyConsumed = new ArrayList<Integer>();
			while(collab.getCapacityDonatedInThisStep() < collab.getMaxCapacityToSupply()){
				
				//First, we try to donate to peers with credit, providing all that they ask, if the collaborator is able.
				Peer consumer = choosesConsumer(collab, alreadyConsumed);
				
				/**
				 * If the provider already tried to donate to everyone with credit, but it still has some spare
				 * resources, divide it equally between the ZeroCreditPeers and the unique free rider. If there is still
				 * any resources, donate it to the free rider: it is eager.
				 */
				if(consumer == null){
					performDonationToPeersWithoutCredit(collab);
					break;
				}
				
				performDonation(collab, consumer, -1);
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
		
		
		/** Fulfilling consumersCollaalreadyConsumedbList and providersList with collaborators with their new consuming status. **/
		for(int collabId : allCollaborators){
			/** Based in consumingStateProbability, we decide if the collaborator will consume or not in the next step. **/
			peers[collabId].setConsuming((this.randomGenerator.nextInt(100)+1 <= (((Collaborator)peers[collabId]).getConsumingStateProbability()*100)));
			if(peers[collabId].isConsuming()){				
				if((this.currentStep+1)<this.numSteps){
					peers[collabId].getRequestedHistory()[this.currentStep+1] = peers[collabId].getInitialDemand()-peers[collabId].getInitialCapacity();
					peers[collabId].getConsumedHistory()[this.currentStep+1] = 0;
					peers[collabId].setDemand(peers[collabId].getInitialDemand()-peers[collabId].getInitialCapacity());
					((Collaborator)peers[collabId]).setCapacityDonatedInThisStep(0);
					peers[collabId].getStateHistory()[this.currentStep+1] = State.CONSUMING;
					
					consumersCollabList.add(collabId);
				}
			}
			else{											//capacitySupplied is updated based on capacitySuppliedReferenceValue
				if((this.currentStep+1)<this.numSteps){
					peers[collabId].setDemand(0);
					((Collaborator)peers[collabId]).setCapacityDonatedInThisStep(0);
					((Collaborator)peers[collabId]).getRequestedHistory()[this.currentStep+1] = 0;
					((Collaborator)peers[collabId]).getCapacitySuppliedHistory()[this.currentStep+1] = ((Collaborator)peers[collabId]).getInitialCapacity();
					peers[collabId].getStateHistory()[this.currentStep+1] = State.PROVIDING;
					
					providersList.add(collabId);	
				}
			}
		}
			
		/** Refreshing Free Riders' demand. **/
		for(Integer fId : freeRidersList){
			if((this.currentStep+1)<this.numSteps){
				//peers[fId].setDemand(peers[fId].getInitialDemand());			
				peers[fId].getRequestedHistory()[this.currentStep+1] = 40;
				peers[fId].getConsumedHistory()[this.currentStep+1] = 0;
				//peers[fId].setDemand(peers[fId].getInitialDemand());
				peers[fId].setDemand(40);
			}
		}
			
		/** If in dynamic context update capacity supplied by collaborators. **/
		if(this.dynamic)
			this.updateCapacitySupplied();
		
		/** Set next step.**/
		this.currentStep++;			
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
		 * If we got here, there is no collaborator willing to consume with reputation > 0, then return null.
		 */
		return null;
		
	}
	
	

	/**
	 * Performs the donation from a provider to a consumer.
	 * @param provider the collaborator peer who will donate his spare resources
	 * @param consumer the peer who will consume the resources
	 */
	private void performDonation(Collaborator provider, Peer consumer, double resources){	
		
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
		
		double donated = updateProvidersInteraction(provider, consumer, resources);		//update the value donated in providers interaction
		updateConsumersInteraction(provider, consumer, donated);						//now we update the interaction values of consumer
		
			
		//since the interactions already exist, simply update them
		updateReputation(provider, consumer, provider.getInteractions().get(provider.getInteractions().indexOf(new Interaction(consumer, 0, 0))));
		if(!(consumer instanceof FreeRider))	//free riders don't use reputation for nothing
			updateReputation(provider, consumer, consumer.getInteractions().get(consumer.getInteractions().indexOf(new Interaction(provider, 0, 0))));
		
		this.sortReputations(provider, consumer);									//sort the reputations in both provider and consumer
		
		provider.getDonatedHistory()[this.currentStep] += donated;					//update the donated amount (from provider) in this step
		consumer.getConsumedHistory()[this.currentStep] += donated;					//update the consumed amount (from consumer) in this step
		
		if(consumer instanceof FreeRider)
			provider.getDonatedToFreeRidersHistory()[this.currentStep] += donated;
	}	
	
	private void performDonationToPeersWithoutCredit(Collaborator collaborator) {
				
		List <Integer> consumingPeers = new ArrayList<Integer>();			//consuming peers that didn't consume
		consumingPeers.addAll(this.consumersCollabList);					//free riders arenot needed here, since they never have reputation > 0
		
		List <Integer> peersWithZeroCredit = new ArrayList<Integer>();	//peers with reputation>0 relative to the collaborator
		Collections.sort(collaborator.getPeersReputations());				//assure the reputation order
		for(int i = collaborator.getPeersReputations().size()-1; i >= 0 ; i--){
			if(collaborator.getPeersReputations().get(i).getReputation()<=0)
				if(!(peers[collaborator.getPeersReputations().get(i).getId()] instanceof FreeRider)
						&& consumingPeers.contains(peers[collaborator.getPeersReputations().get(i).getId()]))
					peersWithZeroCredit.add(collaborator.getPeersReputations().get(i).getId());
		}
		
		//add the peers who want to consume but which I didnt interact before
		for(int idConsumer : consumingPeers){
			if(!peersWithZeroCredit.contains(idConsumer))
				peersWithZeroCredit.add(idConsumer);
		}
		
		/**
		 * ADAPTAR
		 */
		
		while(peersWithZeroCredit.size()>0 && collaborator.getCapacityDonatedInThisStep()<(collaborator.getMaxCapacityToSupply()-0.0000000001)){
			double smallestDemand = Double.MAX_VALUE;
			for(int idConsumer : peersWithZeroCredit)
				smallestDemand = smallestDemand < peers[idConsumer].getDemand()? smallestDemand : peers[idConsumer].getDemand();
			
			double resourcesForPeersWithZeroCredit = collaborator.getMaxCapacityToSupply() - collaborator.getCapacityDonatedInThisStep();
			double howMuchShouldEachPeerReceive = resourcesForPeersWithZeroCredit/(peersWithZeroCredit.size() + freeRidersList.size());
			
			double howMuchWillEachPeerReceiveInThisRound = Math.min(smallestDemand, howMuchShouldEachPeerReceive);
			List <Integer> peersWithZeroCreditAux = new ArrayList<Integer>();
			peersWithZeroCreditAux.addAll(peersWithZeroCredit);
			for(int idConsumer : peersWithZeroCreditAux){
				performDonation(collaborator, peers[idConsumer], howMuchWillEachPeerReceiveInThisRound);
				removePeerIfFullyConsumed(peers[idConsumer]);
				if(peers[idConsumer].getDemand()<=0)
					peersWithZeroCredit.remove((Integer)idConsumer);
			}
			
			for(int idConsumer : freeRidersList)
				performDonation(collaborator, peers[idConsumer], howMuchWillEachPeerReceiveInThisRound);
		}
		
		if(collaborator.getCapacityDonatedInThisStep()<collaborator.getMaxCapacityToSupply()){
			double surplusResources = collaborator.getMaxCapacityToSupply() - collaborator.getCapacityDonatedInThisStep();
			double howMuchWillEachFreeRiderReceive = surplusResources/freeRidersList.size();
			for(int idConsumer : freeRidersList)
				performDonation(collaborator, peers[idConsumer], howMuchWillEachFreeRiderReceive);
		}
		
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
			
			
			
			if(this.peers[i] instanceof Collaborator){		
				Collaborator collaborator = (Collaborator) this.peers[i];	
				
				
				if(this.currentStep>0){
					
					if(pairwise){
						
						for (Interaction interaction : collaborator.getInteractions()) {
							
							double lastFairness = getFairness(interaction.getLastConsumed(), interaction.getLastDonated());	
							double currentFairness = getFairness(interaction.getConsumed(), interaction.getDonated());
							
							boolean change = false;
							if(currentFairness>=0){
								if(currentFairness < tauMin)
									interaction.setIncreasingCapacity(false);
								else if(currentFairness > tauMax)
									interaction.setIncreasingCapacity(true);
								else{
									if(currentFairness <= lastFairness)
										interaction.setIncreasingCapacity(!interaction.isIncreasingCapacity());
								}
								change = true;
							}	
							
							
														
							if(change){
								/** Change it's capacity in order to achieve a greater fairness. **/ 
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
					if(currentFairness>=0){
						if(currentFairness < tauMin)
							collaborator.setIncreasingCapacitySupplied(false);
						else if(currentFairness > tauMax)
							collaborator.setIncreasingCapacitySupplied(true);
						else{
							if(currentFairness <= lastFairness)
								collaborator.setIncreasingCapacitySupplied(!collaborator.isIncreasingCapacitySupplied());
						}
						change = true;
					}
				
					
					if(change){
	//					double maxLim = Math.min(collaborator.getInitialCapacity(), collaborator.getInitialDemand()-collaborator.getInitialCapacity());
						double maxLim = collaborator.getInitialCapacity();
								
						/** Change it's capacity in order to achieve a greater fairness. **/
						if(collaborator.isIncreasingCapacitySupplied())				
							collaborator.setMaxCapacityToSupply(Math.min(maxLim, collaborator.getMaxCapacityToSupply()+(this.changingValue*collaborator.getInitialCapacity())));	//try to increase the current capacitySuppliedReferenceValue
						else
							collaborator.setMaxCapacityToSupply(Math.max(0, Math.min(maxLim,collaborator.getMaxCapacityToSupply()-(this.changingValue*collaborator.getInitialCapacity()))));	//try to decrease the current capacitySuppliedReferenceValue
					}
						
					if((this.currentStep+1)<this.numSteps && !collaborator.isConsuming())
						collaborator.getCapacitySuppliedHistory()[this.currentStep+1] = collaborator.getMaxCapacityToSupply();
				}
			}
		}	
		
		for(int idProvider : providersList)
			this.suppliedHistory[this.currentStep+1] += ((Collaborator)this.peers[idProvider]).getMaxCapacityToSupply();
	
		
		
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
	 * Export the main data of simmulation to an excel (xlsx) file.
	 */
	private void exportData(){		
		
		GenerateCsv csvGen = new GenerateCsv(this.outputFile, this.numSteps, this);
		csvGen.outputPeers();
//		csvGen.outputWelfareCollaborators();
//		csvGen.outputFreeRiders();
//		csvGen.outputSharingLevel();
		
//		WriteExcel2010 we = new WriteExcel2010(this.outputFile, this.numSteps);
//		we.setupFile();		
//		we.fulfillCapacitySuppliedData(peers);
//		we.fulfillFairnessPerSteps(peers);
//		we.writeFile();
	}
	
	int global = 0;
	
	private double updateProvidersInteraction(Collaborator provider, Peer consumer, double resources){
		
		int index = provider.getInteractions().indexOf(new Interaction(consumer, 0, 0));
		Interaction interaction = provider.getInteractions().get(index);		//retrieve the interaction object with its history		
		
		double valueToBeDonated = 0;
		if(resources==-1){			
			double maxToBeDonated = 0;
			
			boolean newComer = true;
			if(interaction.getDonated()>0 || interaction.getConsumed()>0)
				newComer = false;
			
			if(dynamic){
				double fairness = getFairness(interaction.getConsumed(), interaction.getDonated());
				
				if(newComer || fairness<=0) 
					maxToBeDonated = provider.getMaxCapacityToSupply();		//global
				else
					maxToBeDonated = interaction.getMaxCapacitySupplied();	//pairwise
				
				PeerReputation peerRep = new PeerReputation(consumer.getPeerId(), 0);
				peerRep = provider.getPeersReputations().get(provider.getPeersReputations().indexOf(peerRep));
				maxToBeDonated = Math.max(maxToBeDonated, peerRep.getReputation());
				maxToBeDonated = Math.min(provider.getInitialCapacity(), maxToBeDonated);
			}
			else
				maxToBeDonated = provider.getInitialCapacity();
			
			double amountThatCouldBeDonated = Math.min(consumer.getDemand(), maxToBeDonated);
			double spareResources = maxToBeDonated - provider.getCapacityDonatedInThisStep();
			valueToBeDonated = Math.max(0,Math.min(spareResources, amountThatCouldBeDonated));	
		}
		else{
			if(!(consumer instanceof FreeRider)){
				valueToBeDonated = Math.min(consumer.getDemand(), resources);
			}
			else{
				valueToBeDonated = resources;
			}
		}	
			
		
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
		return tauMin;
	}
	
	public double getFairnessUpperThreshold() {
		return tauMax;
	}
	
	public double getChangingValue() {
		return changingValue;
	}
	
	public boolean isDynamic(){
		return this.dynamic;
	}
	
	public Peer[] getPeers() {
		return peers;
	}
	
	public int getNumSteps() {
		return numSteps;
	}


}