package peer;

import java.util.ArrayList;
import java.util.List;

import nof.Interaction;
import peer.reputation.PeerInfo;

public class Peer{
	
	
	protected final double INITIAL_CAPACITY, INITIAL_DEMAND;
	
	protected double demand;
	protected int id;
	
	protected State state;
	private double consumingStateProbability, providingStateProbability, idleStateProbability;
	
	
	protected ArrayList<PeerInfo> balances;
	protected ArrayList <Interaction> interactions;
	protected double consumedHistory[];
	protected double requestedHistory[];
	
		
	public Peer(int id, double initialCapacity, double initialDemand, double consumingStateProbability, double idleStateProbability, double providingStateProbability, int numSteps) {
		super();
		this.INITIAL_CAPACITY = initialCapacity;
		this.INITIAL_DEMAND = initialDemand;
		
		this.id = id;
		this.demand = initialDemand;
		
		this.state = null;
		this.consumingStateProbability = consumingStateProbability;
		this.idleStateProbability = idleStateProbability;
		this.providingStateProbability = providingStateProbability;
		
		this.balances = new ArrayList<PeerInfo>();
		this.interactions = new ArrayList<Interaction>();
		this.consumedHistory = new double[numSteps];
		this.requestedHistory = new double[numSteps];
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
	
	public State getState(){
		return this.state;
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public double getConsumingStateProbability() {
		return consumingStateProbability;
	}
	
	public double getIdleStateProbability(){
		return idleStateProbability;
	}
	
	public double getProvidingStateProbability(){
		return providingStateProbability;
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
			currrentConsumed += this.consumedHistory[i];
		return currrentConsumed;
	}
	
	public double getCurrentConsumed(int beginning, int end) {
		double currrentConsumed = 0;
		for(int i = beginning; i <= end; i++)
			currrentConsumed += this.consumedHistory[i];
		return currrentConsumed;
	}
	
	public double [] getRequestedHistory() {
		return requestedHistory;
	}
	
	public double getCurrentRequested(int step) {
		double currrentRequested = 0;
		for(int i = 0; i <= step; i++)
			currrentRequested += this.requestedHistory[i];
		return currrentRequested;
	}
	
}