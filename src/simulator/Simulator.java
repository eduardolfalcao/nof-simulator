package simulator;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import nof.Interaction;
import peer.Collaborator;
import peer.Peer;
import peer.PeerGroup;
import peer.State;
import utils.GenerateCsv;

public class Simulator {	
	
	private PeerComunity peerComunity;
	private MemberPicker memberPicker;
	private Market market;
	
	private List <Integer> consumersList, idlePeersList, providersList;	
	private List <Integer> consumedPeersList, donatedPeersList;	
	
	private int numSteps;
	private int currentStep;
		
	//nof
	private boolean fdNof, transitivity;						
	private double tMin, tMax;
	private double deltaC;					
	
	//others
	private StateGenerator stateGenerator;
	public final static Logger logger = Logger.getLogger(Simulator.class.getName());
	private String outputFile;
	private double kappa;
	
	public Simulator(ArrayList<PeerGroup> groupsOfPeers, int numSteps, boolean fdNoF, boolean transitivity, double tMin, double tMax, double deltaC, 
			int seed, Level level, String outputFile, double kappa) {
		
		peerComunity = new PeerComunity(groupsOfPeers, numSteps);	//the constructor also creates the peers		
		memberPicker = new MemberPicker(seed);
		market = new Market(this);
		
		consumersList = new ArrayList<Integer> ();		
		idlePeersList = new ArrayList<Integer> ();		
		providersList = new ArrayList<Integer> ();
		consumedPeersList = new ArrayList<Integer> ();		
		donatedPeersList = new ArrayList<Integer> ();
		
		this.numSteps = numSteps;
		this.currentStep = 0;
		
		this.fdNof = fdNoF;
		this.transitivity = transitivity;
		this.tMin = tMin;
		this.tMax = tMax;
		this.deltaC = deltaC;
		
		this.stateGenerator = new StateGenerator(seed);
		
		/* Logger setup */
		Simulator.logger.setLevel(level);
		Simulator.logger.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
	    handler.setLevel(level);
	    logger.addHandler(handler);
			
		this.outputFile = outputFile;
		this.kappa = kappa;
	}
	
	public void startSimulation(){	
		//the constructor of PeerComunity already creates the peers
		for(int i = 0; i < this.numSteps; i++){
			this.setupPeersState();
			this.performCurrentStepDonations();
		}
		exportData();
	}
	
	private void setupPeersState(){
		
		//now we set the state of each peer
		for(Peer p : PeerComunity.peers){
			State currentState = stateGenerator.generateState(State.CONSUMING, p.getConsumingStateProbability(), State.IDLE, p.getIdleStateProbability(), State.PROVIDING, p.getProvidingStateProbability());
			p.setState(currentState);
					
			if(p.getState()==State.CONSUMING){
				consumersList.add(p.getId());
				PeerComunity.peers[p.getId()].setDemand(PeerComunity.peers[p.getId()].getInitialDemand());
				PeerComunity.peers[p.getId()].getRequestedHistory()[currentStep] = PeerComunity.peers[p.getId()].getInitialDemand();
				PeerComunity.peers[p.getId()].setResourcesDonatedInCurrentStep(0);
				PeerComunity.peers[p.getId()].getCapacitySuppliedHistory()[currentStep] = 0;			
			}
			else if(p.getState()==State.IDLE){
				idlePeersList.add(p.getId());
				PeerComunity.peers[p.getId()].setDemand(0);
				PeerComunity.peers[p.getId()].getRequestedHistory()[this.currentStep] = 0;
				PeerComunity.peers[p.getId()].setResourcesDonatedInCurrentStep(0);
				PeerComunity.peers[p.getId()].getCapacitySuppliedHistory()[currentStep] = 0;
			}
			else{
				providersList.add(p.getId());
				PeerComunity.peers[p.getId()].setDemand(0);
				PeerComunity.peers[p.getId()].getRequestedHistory()[this.currentStep] = 0;
				PeerComunity.peers[p.getId()].setResourcesDonatedInCurrentStep(0);
				if(PeerComunity.peers[p.getId()] instanceof Collaborator){
					Collaborator c = (Collaborator)PeerComunity.peers[p.getId()];
					c.getCapacitySuppliedHistory()[currentStep] = c.getMaxCapacityToSupply();									
				}
				else
					PeerComunity.peers[p.getId()].getCapacitySuppliedHistory()[currentStep] = PeerComunity.peers[p.getId()].getInitialCapacity();
			}
		}		
	}	
	
	//performs all donations of the current step.
	private void performCurrentStepDonations(){
		
		Collaborator provider = null;
				
		//while there is any collaborator willing to donate, choose one
		while(!providersList.isEmpty()){
			provider = memberPicker.choosesRandomPeer(providersList);
			
			//keeps choosing consumer and donating until supply all peer's capacity 
			ArrayList<Integer> alreadyConsumed = new ArrayList<Integer>();
			while(provider.getResourcesDonatedInCurrentStep() < provider.getMaxCapacityToSupply()){
				
				//first, we try to donate to peers with balance > 0, providing all that they ask, if the provider is able
				Peer consumer = memberPicker.choosesConsumerWithPositiveBalance(provider, consumersList, alreadyConsumed);
				
				/**
				 * If the provider already tried to donate to everyone with credit, but it still has some spare
				 * resources, divide it evenly between the ZeroCreditPeers.
				 */
				if(consumer == null){
					market.performDonationToPeersWithNilBalance(provider);
					break;	//breaks only the inner-loop
				}
				
				market.performDonation(provider, consumer);
				market.removePeerIfFullyConsumed(consumer);
				
				alreadyConsumed.add(consumer.getId());
			}			
			market.removePeerThatDonated(provider);	
		}
		setupNextStep();
	}
	
	private void setupNextStep(){	
		
		Simulator.logger.info("Step "+this.currentStep);

		//here we update the consumed and donated values of each peer
		for(Peer p : PeerComunity.peers){
			p.getConsumedHistory()[this.currentStep] += p.getInitialDemand() - p.getDemand();
			p.getDonatedHistory()[this.currentStep] += p.getResourcesDonatedInCurrentStep();
			
			if(p instanceof Collaborator){
				Collaborator collab = (Collaborator) p;
				
				
				//save last values and update status for next step
				for (Interaction interaction : collab.getInteractions())
					interaction.saveLastValues();
			}		
		}		
		
		consumersList.clear();
		idlePeersList.clear();
		providersList.clear();		
		consumedPeersList.clear();
		donatedPeersList.clear();
		
		if(this.fdNof)
			this.updateCapacitySupplied();
		
		this.currentStep++;			
	}
	
	//the global and pairwise controllers
	private void updateCapacitySupplied(){
		
		for(Peer p : PeerComunity.peers){		
			if(p instanceof Collaborator){
				Collaborator collab = (Collaborator) p;
					
				if(fdNof && currentStep>0){	
					
					//pairwise
					for (Interaction interaction : collab.getInteractions()) {					
						double lastFairness = getFairness(interaction.getLastConsumed(), interaction.getLastDonated());	
						double currentFairness = getFairness(interaction.getConsumed(), interaction.getDonated());						
						boolean change = false;
						if(currentFairness>=0){
							if(currentFairness < tMin)
								interaction.setIncreasingCapacity(false);
							else if(currentFairness > tMax)
								interaction.setIncreasingCapacity(true);
							else{
								if(currentFairness <= lastFairness)
									interaction.setIncreasingCapacity(!interaction.isIncreasingCapacity());
							}
							change = true;
						}						
													
						if(change){
							double totalAmountOfResources = collab.getInitialCapacity();								
							if(interaction.isIncreasingCapacity())		//try to increase the current maxCapacitySupplied
								interaction.setMaxCapacityToSupply(Math.min(totalAmountOfResources, interaction.getMaxCapacityToSupply()+deltaC*totalAmountOfResources));	
							else										//try to decrease the current maxCapacitySupplied
								interaction.setMaxCapacityToSupply(Math.max(0, Math.min(totalAmountOfResources, interaction.getMaxCapacityToSupply()-deltaC*totalAmountOfResources)));
						}						
						interaction.getCapacitySuppliedHistory()[currentStep] = interaction.getMaxCapacityToSupply();						
					}
					
					
					//global
					double currentFairness = Simulator.getFairness(collab.getCurrentConsumed(currentStep), collab.getCurrentDonated(currentStep));
//					collab.getFairnessHistory()[this.currentStep] = currentFairness;
					double lastFairness = Simulator.getFairness(collab.getCurrentConsumed(currentStep-1), collab.getCurrentDonated(currentStep-1));
					boolean change = false;
					if(currentFairness>=0){
						if(currentFairness < tMin)
							collab.setIncreasingCapacitySupplied(false);
						else if(currentFairness > tMax)
							collab.setIncreasingCapacitySupplied(true);
						else{
							if(currentFairness <= lastFairness)
								collab.setIncreasingCapacitySupplied(!collab.isIncreasingCapacitySupplied());
						}
						change = true;
					}
					
					if(change){
						double totalAmountOfResources = collab.getInitialCapacity();													
						if(collab.isIncreasingCapacitySupplied())				
							collab.setMaxCapacityToSupply(Math.min(totalAmountOfResources, collab.getMaxCapacityToSupply()+deltaC*totalAmountOfResources));	
						else
							collab.setMaxCapacityToSupply(Math.max(0, Math.min(totalAmountOfResources, collab.getMaxCapacityToSupply()-deltaC*totalAmountOfResources)));	
					}
											
					//if providing, save the amount of resources supplied
					if((currentStep+1)<numSteps && collab.getState() == State.PROVIDING)
						collab.getCapacitySuppliedHistory()[currentStep+1] = collab.getMaxCapacityToSupply();					
				}				
			}
		}	
	
		Simulator.logger.fine("FIM Update capacity supplied");
	}
	
	
	public static double getFairness(double consumed, double donated){
		if(donated == 0)
			return -1;
		else
			return consumed/donated;
	}
	
	public static double getSatisfaction(double consumed, double requested){
		return 	getFairness(consumed, requested);
	}

	/**
	 * Export the main data of simmulation to an excel (xlsx) file.
	 */
	private void exportData(){		
		
		//TODO adjust better the output name of file 
		GenerateCsv csvGen = new GenerateCsv(this.outputFile, this);
		csvGen.outputPeers();
	}
	
    
    //getters
    public int getNumSteps(){
    	return numSteps;
    }
    
    public int getCurrentStep(){
    	return currentStep;
    }
    
    public List <Integer> getConsumersList(){
    	return consumersList;
    }
    
    public List <Integer> getConsumedPeersList(){
    	return consumedPeersList;
    }
    
    public List <Integer> getProvidersList(){
    	return providersList;
    }
    
    public List <Integer> getDonatedPeersList(){
    	return donatedPeersList;
    }
    
	public double getTMin() {
		return tMin;
	}
	
	public double getTMax() {
		return tMax;
	}
	
	public double getDeltaC() {
		return deltaC;
	}
	
	public boolean isFdNof(){
		return this.fdNof;
	}
	
	public boolean isTransitivity() {
		return transitivity;
	}

	public double getKappa() {
		return kappa;
	}

	public PeerComunity getPeerComunity() {
		return peerComunity;
	}	
}