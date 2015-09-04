package peer;


public class Collaborator extends Peer{
	
	
	private double maxCapacityToSupply, capacityDonatedInThisStep;								
	private double fairnessHistory[], donatedHistory[], capacitySuppliedHistory[];
	private boolean increasingCapacitySupplied;
	
	public Collaborator(int id, double capacity, double demand, double consumingStateProbability, double idleStateProbability, double providingStateProbability, int numSteps) {
		
		super(id, capacity, demand, consumingStateProbability, idleStateProbability, providingStateProbability, numSteps);		
		this.setCapacityDonatedInThisStep(0);
		this.setIncreasingCapacitySupplied(false);				
		
		this.fairnessHistory = new double[numSteps];
		this.donatedHistory = new double[numSteps];
		this.capacitySuppliedHistory = new double[numSteps];		
		this.setMaxCapacityToSupply(capacity);		
	}
	
	
	public double getMaxCapacityToSupply() {
		return maxCapacityToSupply;
	}
	
	public void setMaxCapacityToSupply(double maxCapacityToSupply) {
		this.maxCapacityToSupply = maxCapacityToSupply;
	}
	
	public double getCapacityDonatedInThisStep() {
		return capacityDonatedInThisStep;
	}
	
	public void setCapacityDonatedInThisStep(double capacityDonatedInThisStep) {
		this.capacityDonatedInThisStep = capacityDonatedInThisStep;
	}
	
	public double[] getFairnessHistory() {
		return fairnessHistory;
	}
	
	public double[] getDonatedHistory() {
		return donatedHistory;
	}

	public double[] getCapacitySuppliedHistory() {
		return capacitySuppliedHistory;
	}
	
	public boolean isIncreasingCapacitySupplied() {
		return increasingCapacitySupplied;
	}

	public void setIncreasingCapacitySupplied(boolean increasingCapacitySupplied) {
		this.increasingCapacitySupplied = increasingCapacitySupplied;
	}	
	
	public double getCurrentDonated(int step) {
		double currrentDonated = 0;
		for(int i = 0; i <= step; i++)
			currrentDonated += this.donatedHistory[i];
		return currrentDonated;
	}
	
	public double getCurrentDonated(int beginning, int end) {
		double currrentDonated = 0;
		for(int i = beginning; i <= end; i++)
			currrentDonated += this.donatedHistory[i];
		return currrentDonated;
	}
	
}
