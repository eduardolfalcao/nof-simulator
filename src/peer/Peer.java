package peer;

import java.util.ArrayList;
import java.util.List;

import nof.Interaction;
import peer.reputation.PeerReputation;

public class Peer{
	
	
	protected double initialDemand;
	protected double demand;
	protected boolean consuming; 
	protected ArrayList<PeerReputation> peersReputations;
	protected ArrayList <Interaction> interactions;
	protected int peerId;
	
	protected double donatedHistory[];
	protected double consumedHistory[];
	protected double requestedHistory[];
	
	/**
	 * 
	 * Constructor for Collaborators.
	 * 
	 * @param demand
	 * @param peerId
	 * @param consuming
	 * @param numSteps
	 */
	public Peer(double demand, int peerId, boolean consuming, int numSteps) {
		super();
		this.initialDemand = demand;
		this.setDemand(demand);
		this.setPeerId(peerId);
		this.setConsuming(consuming);	
		this.setPeersReputations(new ArrayList<PeerReputation>());
		this.setInteractions(new ArrayList<Interaction>());
		this.setDonatedHistory(new double[numSteps]);
		this.setConsumedHistory(new double[numSteps]);
		this.setRequestedHistory(new double[numSteps]);
	}

	/**
	 * Constructor for Free Riders.
	 * 
	 * @param demand
	 * @param peerId 
	 * @param consuming
	 * @param numSteps
	 * @param isFreeRider (force Free Riders to use this constructor)
	 */
	public Peer(double demand, int peerId, boolean consuming, int numSteps, boolean isFreeRider) {
		super();
		this.initialDemand = demand;
		this.setDemand(demand);
		this.setPeerId(peerId);
		this.setConsuming(consuming);		
		this.setConsumedHistory(new double[numSteps]);
		this.setRequestedHistory(new double[numSteps]);
	}
	
	
	/**
	 * The peer with highest reputation might already been used. Therefore, we will seek
	 * the higher value before this. Where attempt = 2, means the second higher value in
	 * the SortedList, and so on.
	 * 
	 * @param nth the nth best reputation
	 * @return the peer id with the nth best reputation
	 */
	public int getThePeerIdWithNthBestReputation(int nth){
		if(nth<=0)
			return -1;
			
		for(int i = this.peersReputations.size()-1; i>=0 ;i--){
			if(nth==1)
				return this.peersReputations.get(i).getId();
			nth--;
		}
		
		return -1;
	}	
	
	/**
	 * @param step current step
	 * @return currrentDonated the amount donated until this step
	 */
	public double getCurrentRequested(int step) {
		double currrentRequested = 0;
		for(int i = 0; i <= step; i++)
			currrentRequested += this.requestedHistory[i];
		return currrentRequested;
	}
	
	/**
	 * @param step current step
	 * @return currrentDonated the amount donated until this step
	 */
	public double getCurrentDonated(int step) {
		double currrentDonated = 0;
		for(int i = 0; i <= step; i++)
			currrentDonated += this.donatedHistory[i];
		return currrentDonated;
	}
	
	/**
	 * @param step current step
	 * @return currrentConsumed the amount consumed until this step
	 */
	public double getCurrentConsumed(int step) {
		double currrentConsumed = 0;
		for(int i = 0; i <= step; i++)
			currrentConsumed += this.consumedHistory[i];
		return currrentConsumed;
	}
	
	/**
	 * peerId is unique
	 */
	public int hashCode() {
        return this.peerId;
    }
	
	/**
	 * @return true if they have the same peerId, false otherwise
	 */
	public boolean equals(Object obj) {
	       if (obj == null || !(obj instanceof Peer))
	            return false;
	       else{
	    	   Peer p = (Peer) obj;
	    	   if(this.peerId == p.getPeerId())
	    		   return true;
	    	   else
	    		   return false;
	       }
	}
	
	/**
	 * @return true if the peer is consuming another peer's resources, false otherwise
	 */
	public boolean isConsuming() {
		return consuming;
	}

	/**
	 * @param consuming the state of the peer
	 */
	public void setConsuming(boolean consuming) {
		this.consuming = consuming;
	}

	/**
	 * @return the list of all interactions
	 */
	public List<Interaction> getInteractions() {
		return interactions;
	}

	/**
	 * @param interactions
	 */
	private void setInteractions(ArrayList<Interaction> interactions) {
		this.interactions = interactions;
	}

	/**
	 * @return the peerId
	 */
	public int getPeerId(){
		return this.peerId;
	}
	
	/**
	 * @param the peerId
	 */
	public void setPeerId(int peerId){
		this.peerId = peerId;
	}
	
	/**
	 * @return the demand of the peer
	 */
	public double getDemand() {
		return demand;
	}
	
	/**
	 * @return the initial demand of the peer
	 */
	public double getInitialDemand() {
		return this.initialDemand;
	}
	
	/**
	 * @param demand the new demand of the peer
	 */
	public void setDemand(double demand) {
		this.demand = demand;
	}
	
	/**
	 * @return the peersReputations
	 */
	public ArrayList<PeerReputation> getPeersReputations() {
		return peersReputations;
	}

	/**
	 * @param peersReputations the SortedList with peers reputations
	 */
	public void setPeersReputations(ArrayList<PeerReputation> peersReputations) {
		this.peersReputations = peersReputations;
	}
	
	/**
	 * @return the donatedHistory
	 */
	public double[] getDonatedHistory() {
		return donatedHistory;
	}

	/**
	 * @param donatedHistory the donatedHistory to set
	 */
	public void setDonatedHistory(double[] donatedHistory) {
		this.donatedHistory = donatedHistory;
	}
	
	/**
	 * @return the consumedHistory
	 */
	public double[] getConsumedHistory() {
		return consumedHistory;
	}

	/**
	 * @param consumedHistory the consumedHistory to set
	 */
	public void setConsumedHistory(double[] consumedHistory) {
		this.consumedHistory = consumedHistory;
	}

	/**
	 * @return the requested
	 */
	public double [] getRequestedHistory() {
		return requestedHistory;
	}

	/**
	 * @param requested the requested to set
	 */
	public void setRequestedHistory(double [] requestedHistory) {
		this.requestedHistory = requestedHistory;
	}
	
	
	
}