package simulator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.PeerGroup;
import peer.State;
import peer.Triplet;
import utils.GenerateCsv;
import utils.WriteExcel;

public class Simulator {	
	
	private PeerComunity peerComunity;
	private Queue<PeerGroup> groupsOfPeers;
	private PeerGroup groupOfFreeRiders;
	private MemberPicker memberPicker;
	private Market market;
	
	private List <Integer> consumersList, idlePeersList, providersList;	
	
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
	private boolean whiteWasher;
	
	public Simulator(Queue<PeerGroup> groupsOfCollaborativePeers, PeerGroup groupOfFreeRiders, boolean whiteWasher, int numSteps, boolean fdNoF, boolean transitivity, double tMin, double tMax, double deltaC, 
			int seed, Level level, String outputFile, double kappa) {
		
		groupsOfPeers = groupsOfCollaborativePeers;
		this.groupOfFreeRiders = groupOfFreeRiders;
		peerComunity = new PeerComunity(groupsOfPeers, groupOfFreeRiders, numSteps);	//the constructor also creates the peers
		this.whiteWasher = whiteWasher;
		
		memberPicker = new MemberPicker(seed);
		market = new Market(this);
		
		consumersList = new ArrayList<Integer> ();		
		idlePeersList = new ArrayList<Integer> ();		
		providersList = new ArrayList<Integer> ();
		
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
		
		int index = 0, consumerIndex = 0;
		int numberOfGroups = groupsOfPeers.size()+(groupOfFreeRiders!=null?1:0);	//1 is from the group of free riders	
		if(groupOfFreeRiders!=null){
			//first, get the collaborative peers and then make the interactions with free riders have donated = 0
			Queue<PeerGroup> groupsOfCollaborativePeersAux = new LinkedList<PeerGroup>();
			groupsOfCollaborativePeersAux.add(groupOfFreeRiders);
			for(PeerGroup pg : groupsOfPeers)
				groupsOfCollaborativePeersAux.add(pg);
			this.groupsOfPeers = groupsOfCollaborativePeersAux;
			consumerIndex = 1;
			
			if(whiteWasher){	//change ids ==> its easier to make donated to free riders
				for(Peer p : PeerComunity.peers){
					if(p instanceof Collaborator){
						for(Interaction interaction : p.getInteractions()){
							if(interaction.getPeerB() instanceof FreeRider)
								interaction.makeDonationEqualToZero();
						}
					}
				}
			}
		}
		
		while(index < numberOfGroups){			
			PeerGroup group = groupsOfPeers.poll();	//with the poll we remove it from the queue, so we can access the next element
			groupsOfPeers.add(group);				//then, we add it back on the queue, now, on its tails
			
			State masterState = null;
			if(index==consumerIndex || group.isFreeRider())		//the first might be the free riders (if there is free riders) and the second will always be the consumer (if there is free riders)
				masterState = State.CONSUMING;					//when there aren't free riders, the first will always be the consumer
			else if(index == numberOfGroups-1)		//the last will always be the  provider
				masterState = State.PROVIDING;
			else									//the others will always be idle
				masterState = State.IDLE;
			
			for(Peer p : PeerComunity.peers){		
				if(p.getGroupId() == group.getGroupId()){
					State currentState = stateGenerator.generateState(masterState, group.getDeviation());
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
			index++;
		}
		
		//after all elements have been accessed, the queue remains at the same configuration it was before
		if(groupOfFreeRiders!=null)					//if there are free riders, remove them
			groupsOfPeers.poll();					//remove free riders
		PeerGroup group = groupsOfPeers.poll();		//remove consumers
		groupsOfPeers.add(group);					//add consumers on the beginning of list, now they are providers
		
		for(Peer p : PeerComunity.peers)
			Simulator.logger.finest("Id: "+p.getId()+"; InitialDemand: "+p.getInitialDemand()+"; Demand: "+p.getDemand()+"InitialCapacity: "+p.getInitialCapacity());
	}	
	
	//performs all donations of the current step.
	private void performCurrentStepDonations(){
		
		Collaborator provider = null;
				
		//while there is any collaborator willing to donate, choose one
		while(!providersList.isEmpty() && !consumersList.isEmpty()){
			provider = memberPicker.choosesRandomPeer(providersList);
			
			List <Triplet> consumingPeers = new ArrayList<Triplet>();
			consumingPeers.addAll(memberPicker.getConsumersWithPositiveBalance(provider));
			if(transitivity)
				consumingPeers.addAll(memberPicker.getConsumersWithTransitiveBalance(provider));
			consumingPeers.addAll(memberPicker.getConsumersWithZeroBalance(provider));
			
			while(consumingPeers.size()>0 && provider.getResourcesDonatedInCurrentStep()<(provider.getInitialCapacity()-0.000000000000001)){
				List<Triplet> peersToDonate = new ArrayList<Triplet>();
				peersToDonate.addAll(memberPicker.getNextConsumersWithSameBalance(consumingPeers));
				market.performDonation(provider, peersToDonate);
				for(Triplet peer : peersToDonate)
					consumingPeers.remove(peer);
			}
			market.removePeerThatDonated(provider);

		}
		setupNextStep();
	}
	
	private void setupNextStep(){	
		
		Simulator.logger.info("Step "+currentStep);

		//here we update the consumed and donated values of each peer
		for(Peer p : PeerComunity.peers){			
			if(p instanceof Collaborator){
				Collaborator collab = (Collaborator) p;
				//save last values and update status for next step
				for (Interaction interaction : collab.getInteractions())
					interaction.saveLastValues();
			}		
		}		
		
		Simulator.logger.finest("\n\n\nPasso: "+currentStep);
		for(Peer p : PeerComunity.peers){
			Simulator.logger.finest("Id: "+p.getId()+"; Consumed: "+p.getConsumedHistory()[currentStep]+"; Donated: "+p.getDonatedHistory()[currentStep]);
		}
		
		consumersList.clear();
		idlePeersList.clear();
		providersList.clear();	
		
		if(this.fdNof)
			this.updateCapacitySupplied();
		
		if(currentStep==2)
			System.out.println();
		
		this.currentStep++;			
	}
	
	//the global and pairwise controllers
	private void updateCapacitySupplied(){
		
		if(currentStep>0){
			
			for(Peer p : PeerComunity.peers){		
				if(p instanceof Collaborator){
					Collaborator collab = (Collaborator) p;
					
					//pairwise
					for (Interaction interaction : collab.getInteractions()) {					
						double lastFairness = NetworkOfFavors.getFairness(interaction.getLastConsumed(), interaction.getLastDonated());	
						double currentFairness = NetworkOfFavors.getFairness(interaction.getConsumed(), interaction.getDonated());						
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
					double currentFairness = NetworkOfFavors.getFairness(collab.getCurrentConsumed(currentStep), collab.getCurrentDonated(currentStep));
					double lastFairness = NetworkOfFavors.getFairness(collab.getCurrentConsumed(currentStep-1), collab.getCurrentDonated(currentStep-1));					
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
				}				
			}
		}	
	
		Simulator.logger.fine("FIM Update capacity supplied");
	}	

	private void exportData(){		
		GenerateCsv csvGen = new GenerateCsv(outputFile, this);
		csvGen.outputPeers();
		
//		WriteExcel writeExcel = new WriteExcel(outputFile, this);
//		writeExcel.outputPeers();
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
    
    public List <Integer> getProvidersList(){
    	return providersList;
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