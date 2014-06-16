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

import peerid.PeerReputation;
import nof.Interaction;

public class Peer{
	
	
	protected double demand;
	

	

	protected boolean consuming; 
//	protected TreeMap<Integer, Double> peersReputations;  
	protected ArrayList<PeerReputation> peersReputations;
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
		peersReputations = new ArrayList<PeerReputation>();
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
		peersReputations = new ArrayList<PeerReputation>();
		interactions = new ArrayList<Interaction>();
	}
	
	/**
	 * peerId is unique
	 */
	public int hashCode() {
        return this.peerId;
    }
	
	/**
	 * The objects are equal f they have the same peerId.
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
	 * @return treeMap with peers reputations
	 */
	public ArrayList<PeerReputation> getPeersReputations() {
		return peersReputations;
	}

	/**
	 * 
	 * @param peersReputations treeMap with peers reputations
	 */
	public void setPeersReputations(ArrayList<PeerReputation> peersReputations) {
		this.peersReputations = peersReputations;
	}
	
	/**
	 * The peer with highest reputation might already been used. Therefore, we will seek
	 * the higher value before this. Where attempt = 2, means the second higher value in
	 * the treeMap, and so on.
	 * 
	 * @param nth the nth best reputation
	 * @return the peer id with the nth best reputation
	 */
	public int getThePeerIdWithNthBestReputation(int nth){
		if(nth<=0)
			return -1;
		
		Collections.sort(this.peersReputations);		
		for(PeerReputation p : this.peersReputations){
			if(nth==1)
				return p.getId();
			nth--;
		}
		
		return -1;
	}
	
	




//	@Override
//	public int compareTo(Object o) {
//		final int BEFORE = -1;
//	    final int EQUAL = 0;
//	    final int AFTER = 1;
//		
//		if(!(o instanceof Peer))
//			return EQUAL;
//		
//		Peer otherPeer = (Peer) o;
//		
//		double myReputation = otherPeer.getPeersReputations().get(this.peerId)!=null?otherPeer.getPeersReputations().get(this.peerId):0;
//		double hisReputation = this.getPeersReputations().get(otherPeer.getPeerId())!=null?this.getPeersReputations().get(otherPeer.getPeerId()):0;
//		
//		if (myReputation < hisReputation) 
//	    	return BEFORE;
//		else	// (myReputation >= hisReputation) 
//	    	return AFTER;
//	}		
	
}




//Entry <Peer, Double> last = this.peersReputations.lastEntry();
//if(nth > 1 && last != null){			
//	for(int i = 1; i < nth; i++)				
//		last = this.peersReputations.floorEntry(last.getKey());						
//}
//return last!=null?last.getKey():null;