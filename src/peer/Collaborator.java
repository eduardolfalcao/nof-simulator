package peer;


public class Collaborator extends Peer{
	
	
	private double maxCapacityToSupply;
	private boolean increasingCapacitySupplied;
	
	public Collaborator(int id, double capacity, double demand, double consumingStateProbability, double idleStateProbability, double providingStateProbability, int numSteps) {
		
		super(id, capacity, demand, consumingStateProbability, idleStateProbability, providingStateProbability, numSteps);		
		this.setIncreasingCapacitySupplied(false);					
		this.setMaxCapacityToSupply(capacity);		
	}	
	
	public double getMaxCapacityToSupply() {
		return maxCapacityToSupply;
	}
	
	public void setMaxCapacityToSupply(double maxCapacityToSupply) {
		this.maxCapacityToSupply = maxCapacityToSupply;
	}
	
	public boolean isIncreasingCapacitySupplied() {
		return increasingCapacitySupplied;
	}

	public void setIncreasingCapacitySupplied(boolean increasingCapacitySupplied) {
		this.increasingCapacitySupplied = increasingCapacitySupplied;
	}	
}
