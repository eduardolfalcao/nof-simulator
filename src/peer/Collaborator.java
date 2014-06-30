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
	
	
	public Collaborator(double demand, int peerId, boolean consuming, double capacitySupplied,  int numSteps) {
		super(demand, peerId, consuming, numSteps);
		this.capacitySupplied = capacitySupplied;
		this.capacitySuppliedReferenceValue = capacitySupplied;
	}
	
	
	public double getCapacitySupplied() {
		return capacitySupplied;
	}
	
	public void setCapacitySupplied(double capacitySupplied) {
		this.capacitySupplied = capacitySupplied;
	}


	public double getCapacitySuppliedReferenceValue() {
		return capacitySuppliedReferenceValue;
	}


	public void setCapacitySuppliedReferenceValue(
			double capacitySuppliedReferenceValue) {
		this.capacitySuppliedReferenceValue = capacitySuppliedReferenceValue;
	}

}
