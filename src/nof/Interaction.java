package nof;

import java.util.ArrayList;
import java.util.List;

import peer.Peer;

/**
 * Represents the INTERACTION of two peers (A and B).
 * @author eduardolfalcao
 *
 */
public class Interaction {
	
	private Peer peerA, peerB;	
//	private List <Double> donatedHistory, consumedHistory;
	
	/**
	 * The whole value that peer A donated to peer B, and, therefore,
	 * also the whole value that peer B received from peer A.
	 */
	private double donatedValue;
	

	/**
	 * The whole value that peer A received from peer B, and, therefore,
	 * also the whole value that peer B donated from peer A.
	 */
	private double consumedValue;
	
	
	/**
	 * 
	 * @param peerA
	 * @param peerB
	 */
	public Interaction(Peer peerA, Peer peerB) {
		super();
		this.peerA = peerA;
		this.peerB = peerB;
//		this.donatedHistory = new ArrayList<Double>();
//		this.consumedHistory = new ArrayList<Double>();
		this.donatedValue = 0;
		this.consumedValue = 0;
	}	

	/**
	 * The objects are equal if peerA and peerB have the same id of the interaction object.
	 */
	public boolean equals(Object obj) {
	       if (obj == null || !(obj instanceof Interaction))
	            return false;
	       else{
	    	   Interaction i = (Interaction) obj;
	    	   if(((this.peerA.getPeerId() == i.getPeerA().getPeerId()) && (this.peerB.getPeerId() == i.getPeerB().getPeerId()))
	    			   || ((this.peerA.getPeerId() == i.getPeerB().getPeerId()) && (this.peerB.getPeerId() == i.getPeerA().getPeerId())))
	    		   return true;
	    	   else
	    		   return false;
	       }
	}
	
	/**
	 * 
	 * @param value that peer A donated to peer B
	 */
	public void peerADonatesValue(double value){
//		this.donatedHistory.add(value);
		this.donatedValue += value;
	}
	
	/**
	 * 
	 * @param value that peer B donated to peer A
	 */
	public void peerBDonatesValue(double value){
//		this.consumedHistory.add(value);
		this.consumedValue += value;
	}
	
	/**
	 * From point of view of peer A. It's peer A who donates.
	 * @return donatedValue
	 */
	public double getDonatedValueByPeerA() {
		return donatedValue;
	}

	/**
	 * From point of view of peer A. It's peer A who consumes.
	 * @return consumedValue
	 */
	public double getConsumedValueByPeerA() {
		return consumedValue;
	}
	
	/**
	 * From point of view of peer B. Value donated by B, it's the value consumed by A.
	 * @return consumedValue
	 */
	public double getDonatedValueByPeerB() {
		return this.consumedValue;
	}

	/**
	 * From point of view of peer B. Value consumed by B, it's the value donated by A.
	 * @return consumedValue
	 */
	public double getConsumedValueByPeerB() {
		return this.donatedValue;
	}
	
//	public List<Double> getDonatedHistory() {
//		return donatedHistory;
//	}
//
//	public List<Double> getConsumedHistory() {
//		return consumedHistory;
//	}

	/**
	 * 
	 * @return peerA
	 */
	public Peer getPeerA() {
		return peerA;
	}

	/**
	 * 
	 * @return peerB
	 */
	public Peer getPeerB() {
		return peerB;
	}
	

}
