package peer;


public class Collaborator extends Peer{
	
	private double maxCapacityToSupply;			
	private double capacityDonatedInThisStep;						//capacity donated in the current step		
	private double capacitySuppliedHistory[];
	private boolean increasingCapacitySupplied;
	
	private double fairnessHistory[];
	protected double donatedHistory[];
	
	private double consumingStateProbability;
	
	
	
	/**
	 * @param demand the amount of demand for resources from another peer
	 * @param peerId the id of the peer
	 * @param consuming the state of the peer: consuming or not
	 * @param capacitySupplied the capacity to be supplied in the current step
	 * @param numSteps the number of steps of the simulation
	 */
	public Collaborator(double demand, int peerId, boolean consuming, double consumingStateProbability, double capacitySupplied,  int numSteps) {
		super(capacitySupplied, demand, peerId, consuming, numSteps);		
		this.setCapacityDonatedInThisStep(0);
		this.setConsumingStateProbability(consumingStateProbability);		
		this.setFairnessHistory(new double[numSteps]);
		this.setDonatedHistory(new double[numSteps]);
		this.setIncreasingCapacitySupplied(false);
		this.capacitySuppliedHistory = new double[numSteps];
		this.setMaxCapacityToSupply(capacitySupplied);
		if(consuming){
			this.setDemand(demand);
			this.getRequestedHistory()[0] = demand-capacitySupplied;
			this.getConsumedHistory()[0] = 0;
		}
		else{
			this.setDemand(0);
			this.getRequestedHistory()[0] = 0;
		}
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
	 * @return the capacityDonatedInThisStep
	 */
	public double getCapacityDonatedInThisStep() {
		return capacityDonatedInThisStep;
	}

	/**
	 * @param capacityDonatedInThisStep the capacityDonatedInThisStep to set
	 */
	public void setCapacityDonatedInThisStep(double capacityDonatedInThisStep) {
		this.capacityDonatedInThisStep = capacityDonatedInThisStep;
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

	/**
	 * @return the capacitySuppliedReferenceValue
	 */
	public double getMaxCapacityToSupply() {
		return maxCapacityToSupply;
	}

	/**
	 * @return the capacitySuppliedHistory
	 */
	public double[] getCapacitySuppliedHistory() {
		return capacitySuppliedHistory;
	}

	/**
	 * @return the increasingCapacitySupplied
	 */
	public boolean isIncreasingCapacitySupplied() {
		return increasingCapacitySupplied;
	}

	/**
	 * @param maxCapacityToSupply the maxCapacityToSupply to set
	 */
	public void setMaxCapacityToSupply(
			double maxCapacityToSupply) {
		this.maxCapacityToSupply = maxCapacityToSupply;
	}

	/**
	 * @param increasingCapacitySupplied the increasingCapacitySupplied to set
	 */
	public void setIncreasingCapacitySupplied(boolean increasingCapacitySupplied) {
		this.increasingCapacitySupplied = increasingCapacitySupplied;
	}	

}
