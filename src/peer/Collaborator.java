package peer;


public class Collaborator extends Peer{
	
	
	private double maxCapacityToSupply;
	private boolean increasingCapacitySupplied;
	
	public Collaborator(int id, double capacity, double demand, State state, int groupId, double deviation, int numSteps) {		
		super(id, capacity, demand, state, groupId, deviation, numSteps);		
		this.setIncreasingCapacitySupplied(false);					
		this.setMaxCapacityToSupply(capacity);		
	}	
	
	@Override
	public String toString(){
		return "Collaborator: id = "+id+"; demand = "+demand+"; currentDonated = "+resourcesDonatedInCurrentStep+"; State = "+state;
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
