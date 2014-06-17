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
import utils.SortedList;
import nof.Interaction;

public class Peer{
	
	
	protected double demand;
	protected boolean consuming; 
	protected SortedList<PeerReputation> peersReputations;
	protected ArrayList <Interaction> interactions;
	protected int peerId;
	
	
	/**
	 * 
	 * @param capacity of resources of the peer
	 * @param demand for resources from another peer
	 */
	public Peer(double demand, int peerId) {
		super();
		this.demand = demand;
		this.peerId = peerId;
		this.consuming = false;
		peersReputations = new SortedList<PeerReputation>();
		interactions = new ArrayList<Interaction>();
	}
	
	/**
	 * 
	 * @param capacity of resources of the peer
	 * @param demand for resources from another peer
	 * @param consuming the state of the peer
	 */
	public Peer(double demand, int peerId, boolean consuming) {
		super();
		this.demand = demand;
		this.peerId = peerId;
		this.consuming = consuming;
		peersReputations = new SortedList<PeerReputation>();
		interactions = new ArrayList<Interaction>();
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
	
	
	
}