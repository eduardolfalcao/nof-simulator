package nof;

import peer.Peer;

/**
 * Represents the INTERACTION of two peers (A and B).
 * @author eduardolfalcao
 *
 */
public class Interaction {
	
	private Peer peerB;
	
	private double consumed = 0, lastConsumed = 0;	//value that peer A consumed from peer B
	private double donated = 0, lastDonated = 0;	//value that peer A donated to peer B
	
	private final double initialCapacity;
	private double maxCapacitySupplied;
	private double capacitySuppliedHistory[];
	private boolean increasingCapacity;
	
	public Interaction(Peer peerB, double initialCapacity, int numSteps) {
		this.peerB = peerB;
		this.initialCapacity = initialCapacity;
		maxCapacitySupplied = initialCapacity;
		capacitySuppliedHistory = new double[numSteps];
		increasingCapacity = false;
	}
	
	/**
	 * 
	 * @param donated value that peer A donated to peer B
	 */
	public void donate(double donated){
		this.donated += donated;
	}
	
	/**
	 * 
	 * @param consumed value that peer A consumed from peer B
	 */
	public void consume(double consumed){
		this.consumed += consumed;
	}
	
	public void updateLastValues(){
		lastDonated = donated;
		lastConsumed = consumed;
	}
	
	public boolean equals(Object obj) {
	    if (obj == null || !(obj instanceof Interaction))
	         return false;
	    else{
	 	   Interaction i = (Interaction) obj;
	 	   if(this.peerB.getPeerId() == i.getPeerB().getPeerId())
	 		   return true;
	 	   else 
	 		   return false;
	    }
	}

	public Peer getPeerB() {
		return peerB;
	}

	/**
	 * @param peerB the peerB to set
	 */
	public void setPeerB(Peer peerB) {
		this.peerB = peerB;
	}

	/**
	 * @return the maxCapacitySupplied
	 */
	public double getMaxCapacitySupplied() {
		return maxCapacitySupplied;
	}

	/**
	 * @param maxCapacitySupplied the maxCapacitySupplied to set
	 */
	public void setMaxCapacitySupplied(double maxCapacitySupplied) {
		this.maxCapacitySupplied = maxCapacitySupplied;
	}

	/**
	 * @return the capacitySuppliedHistory
	 */
	public double[] getCapacitySuppliedHistory() {
		return capacitySuppliedHistory;
	}

	/**
	 * @return the increasingCapacity
	 */
	public boolean isIncreasingCapacity() {
		return increasingCapacity;
	}

	/**
	 * @param increasingCapacity the increasingCapacity to set
	 */
	public void setIncreasingCapacity(boolean increasingCapacity) {
		this.increasingCapacity = increasingCapacity;
	}

	/**
	 * @return the consumed
	 */
	public double getConsumed() {
		return consumed;
	}

	/**
	 * @return the donated
	 */
	public double getDonated() {
		return donated;
	}

	/**
	 * @return the lastConsumed
	 */
	public double getLastConsumed() {
		return lastConsumed;
	}

	/**
	 * @return the lastDonated
	 */
	public double getLastDonated() {
		return lastDonated;
	}

	/**
	 * @return the initialCapacity
	 */
	public double getInitialCapacity() {
		return initialCapacity;
	}
	
	
//	/**
//	 * When the context is dynamic, we must have a reference value of
//	 * capacity to be supplied in the current step. In static context,
//	 * this value is always 1, but in dynamic context, this value changes
//	 * according to the users satisfaction.
//	 */
//	private double capacitySuppliedReferenceValue;			
//	private double capacitySupplied;						//capacity supplied in the current step		
//	private double capacitySuppliedHistory[];
//	private boolean increasingCapacitySupplied;
//	
//	private Peer peerA, peerB;	
//	
//	/**
//	 * The whole value that peer A donated to peer B, and, therefore,
//	 * also the whole value that peer B received from peer A.
//	 */
//	private double donatedValue;
//	private double lastDonatedValue;
//	
//
//	/**
//	 * The whole value that peer A received from peer B, and, therefore,
//	 * also the whole value that peer B donated from peer A.
//	 */
//	private double consumedValue;
//	private double lastConsumedValue;
//	
//	
//	/**
//	 * 
//	 * @param peerA
//	 * @param peerB
//	 */
//	public Interaction(Peer peerA, Peer peerB, int numSteps) {
//		super();
//		this.peerA = peerA;
//		this.peerB = peerB;
//		this.donatedValue = 0;
//		this.lastDonatedValue = 0;
//		this.consumedValue = 0;
//		this.lastConsumedValue = 0;
//		
//		
//		this.setCapacitySupplied(capacitySupplied);
//		this.setCapacitySuppliedReferenceValue(capacitySupplied);
//		this.setCapacitySuppliedHistory(new double[numSteps]);
//		this.setIncreasingCapacitySupplied(false);
//	}	
//	
//	/**
//	 * @return the capacitySuppliedReferenceValue
//	 */
//	public double getCapacitySuppliedReferenceValue() {
//		return capacitySuppliedReferenceValue;
//	}
//
//	/**
//	 * @param capacitySuppliedReferenceValue the capacitySuppliedReferenceValue to set
//	 */
//	public void setCapacitySuppliedReferenceValue(
//			double capacitySuppliedReferenceValue) {
//		this.capacitySuppliedReferenceValue = capacitySuppliedReferenceValue;
//	}
//
//	/**
//	 * @return the capacitySupplied
//	 */
//	public double getCapacitySupplied() {
//		return capacitySupplied;
//	}
//
//	/**
//	 * @param capacitySupplied the capacitySupplied to set
//	 */
//	public void setCapacitySupplied(double capacitySupplied) {
//		this.capacitySupplied = capacitySupplied;
//	}
//
//	/**
//	 * @return the capacitySuppliedHistory
//	 */
//	public double[] getCapacitySuppliedHistory() {
//		return capacitySuppliedHistory;
//	}
//
//	/**
//	 * @param capacitySuppliedHistory the capacitySuppliedHistory to set
//	 */
//	public void setCapacitySuppliedHistory(double capacitySuppliedHistory[]) {
//		this.capacitySuppliedHistory = capacitySuppliedHistory;
//	}
//
//	/**
//	 * The objects are equal if peerA and peerB have the same id of the interaction object.
//	 */
//	public boolean equals(Object obj) {
//	       if (obj == null || !(obj instanceof Interaction))
//	            return false;
//	       else{
//	    	   Interaction i = (Interaction) obj;
//	    	   if(((this.peerA.getPeerId() == i.getPeerA().getPeerId()) && (this.peerB.getPeerId() == i.getPeerB().getPeerId()))
//	    			   || ((this.peerA.getPeerId() == i.getPeerB().getPeerId()) && (this.peerB.getPeerId() == i.getPeerA().getPeerId())))
//	    		   return true;
//	    	   else
//	    		   return false;
//	       }
//	}
//	
//	/**
//	 * @return the increasingCapacitySupplied
//	 */
//	public boolean isIncreasingCapacitySupplied() {
//		return increasingCapacitySupplied;
//	}
//
//	/**
//	 * @param increasingCapacitySupplied the increasingCapacitySupplied to set
//	 */
//	public void setIncreasingCapacitySupplied(boolean increasingCapacitySupplied) {
//		this.increasingCapacitySupplied = increasingCapacitySupplied;
//	}
//	
//	/**
//	 * 
//	 * @param value that peer A donated to peer B
//	 */
//	public void peerADonatesValue(double value){
//		this.donatedValue += value;
//	}
//	
//	/**
//	 * 
//	 * @param value that peer B donated to peer A
//	 */
//	public void peerBDonatesValue(double value){
//		this.consumedValue += value;
//	}
//	
//	/**
//	 * From point of view of peer A. It's peer A who donates.
//	 * @return donatedValue
//	 */
//	public double getDonatedValueByPeerA() {
//		return this.donatedValue;
//	}
//
//	/**
//	 * From point of view of peer A. It's peer A who consumes.
//	 * @return consumedValue
//	 */
//	public double getConsumedValueByPeerA() {
//		return this.consumedValue;
//	}
//	
//	/**
//	 * From point of view of peer B. Value donated by B, it's the value consumed by A.
//	 * @return consumedValue
//	 */
//	public double getDonatedValueByPeerB() {
//		return this.consumedValue;
//	}
//
//	/**
//	 * From point of view of peer B. Value consumed by B, it's the value donated by A.
//	 * @return consumedValue
//	 */
//	public double getConsumedValueByPeerB() {
//		return this.donatedValue;
//	}
//	
//	/**
//	 * From point of view of peer A. It's peer A who donates.
//	 * @return donatedValue
//	 */
//	public double getLastDonatedValueByPeerA() {
//		return this.lastDonatedValue;
//	}
//
//	/**
//	 * From point of view of peer A. It's peer A who consumes.
//	 * @return consumedValue
//	 */
//	public double getLastConsumedValueByPeerA() {
//		return this.lastConsumedValue;
//	}
//	
//	/**
//	 * From point of view of peer B. Value donated by B, it's the value consumed by A.
//	 * @return consumedValue
//	 */
//	public double getLastDonatedValueByPeerB() {
//		return this.lastConsumedValue;
//	}
//
//	/**
//	 * From point of view of peer B. Value consumed by B, it's the value donated by A.
//	 * @return consumedValue
//	 */
//	public double getLastConsumedValueByPeerB() {
//		return this.lastDonatedValue;
//	}
//
//	/**
//	 * 
//	 * @return peerA
//	 */
//	public Peer getPeerA() {
//		return peerA;
//	}
//
//	/**
//	 * 
//	 * @return peerB
//	 */
//	public Peer getPeerB() {
//		return peerB;
//	}
//	
//	public void updateLastValues(){
//		this.lastDonatedValue = this.donatedValue;
//		this.lastConsumedValue = this.consumedValue;
//	}
	

}
