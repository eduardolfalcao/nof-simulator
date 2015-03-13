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
	
	private double fairnessHistory[];
	
	private double consumingStateProbability;
	
	private boolean increasingCapacitySupplied;
	
	/**
	 * @param demand the amount of demand for resources from another peer
	 * @param peerId the id of the peer
	 * @param consuming the state of the peer: consuming or not
	 * @param capacitySupplied the capacity to be supplied in the current step
	 * @param numSteps the number of steps of the simulation
	 */
	public Collaborator(double demand, int peerId, boolean consuming, double consumingStateProbability, double capacitySupplied,  int numSteps) {
		super(capacitySupplied, demand, peerId, consuming, numSteps);
		this.setConsumingStateProbability(consumingStateProbability);
		this.setCapacitySupplied(capacitySupplied);
		this.setCapacitySuppliedReferenceValue(capacitySupplied);
		this.setCapacitySuppliedHistory(new double[numSteps]);
		this.setIncreasingCapacitySupplied(false);
		this.setFairnessHistory(new double[numSteps]);
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
	 * @return the consumingStateProbability
	 */
	public double getConsumingStateProbability() {
		return consumingStateProbability;
	}

	/**
	 * @param consumingStateProbability the consumingStateProbability to set
	 */
	public void setConsumingStateProbability(double consumingStateProbability) {
		this.consumingStateProbability = consumingStateProbability;
	}

	/**
	 * @return the fairnessHistory
	 */
	public double[] getFairnessHistory() {
		return fairnessHistory;
	}

	/**
	 * @param fairnessHistory the fairnessHistory to set
	 */
	public void setFairnessHistory(double fairnessHistory[]) {
		this.fairnessHistory = fairnessHistory;
	}	

}
