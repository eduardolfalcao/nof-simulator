package peer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import peer.peerid.PeerReputation;
import peer.peerid.PeerReputationComparator;
import utils.SortedList;
import nof.Interaction;

public class Peer{
	
	
	protected double demand;
	protected boolean consuming; 
	protected SortedList<PeerReputation> peersReputations;
	protected ArrayList <Interaction> interactions;
	protected int peerId;
	
	protected double donatedHistory[];
	protected double consumedHistory[];	
	
	/**
	 * 
	 * @param capacity of resources of the peer
	 * @param demand for resources from another peer
	 */
	public Peer(double demand, int peerId, int numSteps) {
		super();
		this.demand = demand;
		this.peerId = peerId;
		this.consuming = false;
		this.peersReputations = new SortedList<PeerReputation>(new PeerReputationComparator());
		this.interactions = new ArrayList<Interaction>();
		this.donatedHistory = new double[numSteps];
		this.consumedHistory = new double[numSteps];
	}
	
	/**
	 * 
	 * @param capacity of resources of the peer
	 * @param demand for resources from another peer
	 * @param consuming the state of the peer
	 */
	public Peer(double demand, int peerId, boolean consuming, int numSteps) {
		super();
		this.demand = demand;
		this.peerId = peerId;
		this.consuming = consuming;
		this.peersReputations = new SortedList<PeerReputation>(new PeerReputationComparator());
		this.interactions = new ArrayList<Interaction>();
		this.donatedHistory = new double[numSteps];
		this.consumedHistory = new double[numSteps];
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
	 * peerId is unique
	 */
	public int hashCode() {
        return this.peerId;
    }
	
	/**
	 * The objects are equal if they have the same peerId.
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
	 * 
	 * @return true if the peer is consuming another peer's resources, false, otherwise
	 */
	public boolean isConsuming() {
		return consuming;
	}

	/**
	 * 
	 * @param consuming the state of the peer
	 */
	public void setConsuming(boolean consuming) {
		this.consuming = consuming;
	}

	/**
	 * 
	 * @return the list of all interactions
	 */
	public List<Interaction> getInteractions() {
		return interactions;
	}
	
	/**
	 * 
	 * @return peerId
	 */
	public int getPeerId(){
		return this.peerId;
	}
	
	/**
	 * 
	 * @return demand of this peer
	 */
	public double getDemand() {
		return demand;
	}
	
	/**
	 * Set the new demand of the peer.
	 * @param demand
	 */
	public void setDemand(double demand) {
		this.demand = demand;
	}
	
	/**
	 * 
	 * @return SortedList with peers reputations
	 */
	public SortedList<PeerReputation> getPeersReputations() {
		return peersReputations;
	}

	/**
	 * 
	 * @param peersReputations SortedList with peers reputations
	 */
	public void setPeersReputations(SortedList<PeerReputation> peersReputations) {
		this.peersReputations = peersReputations;
	}
	
	public double getCurrentDonated(int step) {
		int currrentDonated = 0;
		for(int i = 0; i <= step; i++){
			currrentDonated += this.donatedHistory[i];
		}
		return currrentDonated;
	}

	public double[] getDonatedHistory() {
		return donatedHistory;
	}

	public void setDonatedHistory(double[] donatedHistory) {
		this.donatedHistory = donatedHistory;
	}
	
	public double getCurrentConsumed(int step) {
		int currrentConsumed = 0;
		for(int i = 0; i <= step; i++){
			currrentConsumed += this.consumedHistory[i];
		}
		return currrentConsumed;
	}

	public double[] getConsumedHistory() {
		return consumedHistory;
	}

	public void setConsumedHistory(double[] consumedHistory) {
		this.consumedHistory = consumedHistory;
	}
	
	
	
}