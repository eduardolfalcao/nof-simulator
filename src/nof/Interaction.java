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
		if(numSteps != 0)
			capacitySuppliedHistory[0] = initialCapacity;
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
	
	public void saveLastValues(){
		lastDonated = donated;
		lastConsumed = consumed;
	}
	
	public boolean equals(Object obj) {
	    if (obj == null || !(obj instanceof Interaction))
	         return false;
	    else{
	 	   Interaction i = (Interaction) obj;
	 	   if(this.peerB.getId() == i.getPeerB().getId())
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

}