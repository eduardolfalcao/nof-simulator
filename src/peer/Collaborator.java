package peer;


public class Collaborator extends Peer{
	
	private double capacitySupplied;			//capacity supplied in the current step	
	
	
	public Collaborator(double demand, int peerId, boolean consuming, double capacitySupplied,  int numSteps) {
		super(demand, peerId, consuming, numSteps);
		this.capacitySupplied = capacitySupplied;
	}
	
	
	public double getCapacitySupplied() {
		return capacitySupplied;
	}
	
	public void setCapacitySupplied(double capacitySupplied) {
		this.capacitySupplied = capacitySupplied;
	}

}
