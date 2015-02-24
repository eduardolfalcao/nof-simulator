package peer;


public class Collaborator extends Peer{
	
	/**
	 * When the context is dynamic, we must have a reference value of
	 * capacity to be supplied in the current step. In static context,
	 * this value is always 1, but in dynamic context, this value changes
	 * according to the users satisfaction.
	 */
	private double capacitySuppliedReferenceValue;			
	private double capacitySupplied;						//capacity supplied in the current step		
	private double capacitySuppliedHistory[];
	
	private boolean increasingCapacitySupplied;
	
	private String [] services;
	
	
	/**
	 * @param demand the amount of demand for resources from another peer
	 * @param peerId the id of the peer
	 * @param consuming the state of the peer: consuming or not
	 * @param capacitySupplied the capacity to be supplied in the current step
	 * @param numSteps the number of steps of the simulation
	 * @param services supported by this peer
	 */
	public Collaborator(double demand, int peerId, boolean consuming, double capacitySupplied,  int numSteps, char [] productsProvided, char [] productsDemanded) {
		super(demand, peerId, consuming, numSteps, productsProvided, productsDemanded);
		this.setCapacitySupplied(capacitySupplied);
		this.setCapacitySuppliedReferenceValue(capacitySupplied);
		this.setCapacitySuppliedHistory(new double[numSteps]);
		this.setIncreasingCapacitySupplied(false);
		
		int initialStep = 0;
		if(consuming){
			this.getCapacitySuppliedHistory()[initialStep] = 0;
			this.getRequestedHistory()[initialStep] = this.initialDemand;
			this.getConsumedHistory()[initialStep] = 0;
		}
		else{			
			this.getCapacitySuppliedHistory()[initialStep] = this.capacitySuppliedReferenceValue;
			this.getRequestedHistory()[initialStep] = 0;
		}
	}

	/**
	 * @return the capacitySuppliedReferenceValue
	 */
	public double getCapacitySuppliedReferenceValue() {
		return capacitySuppliedReferenceValue;
	}

	/**
	 * @param capacitySuppliedReferenceValue the capacitySuppliedReferenceValue to set
	 */
	public void setCapacitySuppliedReferenceValue(
			double capacitySuppliedReferenceValue) {
		this.capacitySuppliedReferenceValue = capacitySuppliedReferenceValue;
	}

	/**
	 * @return the capacitySupplied
	 */
	public double getCapacitySupplied() {
		return capacitySupplied;
	}

	/**
	 * @param capacitySupplied the capacitySupplied to set
	 */
	public void setCapacitySupplied(double capacitySupplied) {
		this.capacitySupplied = capacitySupplied;
	}

	/**
	 * @return the capacitySuppliedHistory
	 */
	public double[] getCapacitySuppliedHistory() {
		return capacitySuppliedHistory;
	}

	/**
	 * @param capacitySuppliedHistory the capacitySuppliedHistory to set
	 */
	public void setCapacitySuppliedHistory(double capacitySuppliedHistory[]) {
		this.capacitySuppliedHistory = capacitySuppliedHistory;
	}

	/**
	 * @return the increasingCapacitySupplied
	 */
	public boolean isIncreasingCapacitySupplied() {
		return increasingCapacitySupplied;
	}

	/**
	 * @param increasingCapacitySupplied the increasingCapacitySupplied to set
	 */
	public void setIncreasingCapacitySupplied(boolean increasingCapacitySupplied) {
		this.increasingCapacitySupplied = increasingCapacitySupplied;
	}

	/**
	 * @return the products
	 */
	public String [] getProducts() {
		return services;
	}

	/**
	 * @param products the products to set
	 */
	public void setServices(String [] products) {
		this.services = products;
	}
	
	


}
