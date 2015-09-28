package peer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nof.Interaction;
import peer.balance.PeerInfo;

public class Peer{
	
	
	protected final double INITIAL_CAPACITY, INITIAL_DEMAND;
	
	protected double demand, resourcesDonatedInCurrentStep;
	protected int id;
	
	private State state;
	private int groupId;
	private double deviation;
	
	protected ArrayList<PeerInfo> balances;
	protected ArrayList <Interaction> interactions;
	
	private double consumedHistory[], donatedHistory[], donatedToFreeRidersHistory[], requestedHistory[], capacitySuppliedHistory[];
	
		
	public Peer(int id, double initialCapacity, double initialDemand, State state, int groupId, double deviation, int numSteps) {
		super();
		this.INITIAL_CAPACITY = initialCapacity;
		this.INITIAL_DEMAND = initialDemand;
		
		this.id = id;
		this.demand = initialDemand;
		this.resourcesDonatedInCurrentStep = 0;
		
		this.state = state;
		this.groupId = groupId;
		this.deviation = deviation;
		
		this.balances = new ArrayList<PeerInfo>();
		this.interactions = new ArrayList<Interaction>();
		
		this.consumedHistory = new double[numSteps];
		this.donatedHistory = new double[numSteps];
		this.donatedToFreeRidersHistory = new double[numSteps];
		this.requestedHistory = new double[numSteps];
		this.capacitySuppliedHistory = new double[numSteps];		
	}
	
	/**
	 * id is unique
	 */
	public int hashCode() {
        return this.id;
    }
	
	/**
	 * @return true if they have the same id, false otherwise
	 */
	public boolean equals(Object obj) {
	       if (obj == null || !(obj instanceof Peer))
	            return false;
	       else{
	    	   Peer p = (Peer) obj;
	    	   if(this.id == p.getId())
	    		   return true;
	    	   else
	    		   return false;
	       }
	}
	
	
	public double getInitialDemand() {
		return this.INITIAL_DEMAND;
	}
	
	public double getInitialCapacity() {
		return INITIAL_CAPACITY;
	}
	
	public int getId(){
		return this.id;
	}
	
	public double getDemand() {
		return demand;
	}
	
	public void setDemand(double demand) {
		this.demand = demand;
	}	
	
	public double getResourcesDonatedInCurrentStep() {
		return resourcesDonatedInCurrentStep;
	}
	
	public void setResourcesDonatedInCurrentStep(double resourcesDonatedInCurrentStep) {
		this.resourcesDonatedInCurrentStep = resourcesDonatedInCurrentStep;
	}
	
	public State getState(){
		return state;
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public int getGroupId(){
		return groupId;
	}
	
	public double getDeviation(){
		return deviation;
	}
	
	public ArrayList<PeerInfo> getBalances() {
		return balances;
	}

	public void setPeersReputations(ArrayList<PeerInfo> peersReputations) {
		this.balances = peersReputations;
	}
	
	/**
	 * The peer with highest balance might already been used. Therefore, we will seek
	 * the higher value before this. Where attempt = 2, means the second higher value in
	 * the SortedList, and so on.
	 * 
	 * @param nth the nth highest balance
	 * @return the peer id with the nth highest balance
	 */
	public int getThePeerIdWithNthBestReputation(int nth){
		if(nth<=0)
			return -1;
		
		//sort the balance
		Collections.sort(balances);		
		for(int i = this.balances.size()-1; i>=0 ;i--){
			if(nth==1)
				return this.balances.get(i).getId();
			nth--;
		}
		
		return -1;
	}	
	
	public List<Interaction> getInteractions() {
		return interactions;
	}

	private void setInteractions(ArrayList<Interaction> interactions) {
		this.interactions = interactions;
	}
	
	public double[] getConsumedHistory() {
		return consumedHistory;
	}
	
	public double getCurrentConsumed(int step) {
		double currrentConsumed = 0;
		for(int i = 0; i <= step; i++)
			currrentConsumed += consumedHistory[i];
		return currrentConsumed;
	}
	
	public double getCurrentConsumed(int beginning, int end) {
		double currrentConsumed = 0;
		for(int i = beginning; i <= end; i++)
			currrentConsumed += consumedHistory[i];
		return currrentConsumed;
	}
	
	public double[] getDonatedHistory() {
		return donatedHistory;
	}
	
	public double getCurrentDonated(int step) {
		double currrentDonated = 0;
		for(int i = 0; i <= step; i++)
			currrentDonated += donatedHistory[i];
		return currrentDonated;
	}
	
	public double getCurrentDonated(int beginning, int end) {
		double currrentDonated = 0;
		for(int i = beginning; i <= end; i++)
			currrentDonated += donatedHistory[i];
		return currrentDonated;
	}
	
	public double[] getDonatedToFreeRidersHistory() {
		return donatedToFreeRidersHistory;
	}
	
	public double getCurrentDonatedToFreeRiders(int step) {
		double currrentDonated = 0;
		for(int i = 0; i <= step; i++)
			currrentDonated += donatedToFreeRidersHistory[i];
		return currrentDonated;
	}
	
	public double [] getRequestedHistory() {
		return requestedHistory;
	}
	
	public double getCurrentRequested(int step) {
		double currrentRequested = 0;
		for(int i = 0; i <= step; i++)
			currrentRequested += requestedHistory[i];
		return currrentRequested;
	}
	
	public double[] getCapacitySuppliedHistory() {
		return capacitySuppliedHistory;
	}
		
}