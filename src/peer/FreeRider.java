package peer;

public class FreeRider extends Peer{

	private boolean successHistory[];
	
	/**
	 * @param demand the amount of demand for resources from another peer
	 * @param peerId the id of the peer
	 * @param numSteps the number of steps of the simulation
	 */
	public FreeRider(double capacity, double demand, int peerId, int numSteps) {
		super(capacity, demand, peerId, true, numSteps);
		this.setSuccessHistory(new boolean[numSteps]);
		this.getRequestedHistory()[0] = demand-capacity;
		this.getConsumedHistory()[0] = 0;
		this.setDemand(demand);
	}

	/**
	 * @return the successHistory
	 */
	public boolean[] getSuccessHistory() {
		return successHistory;
	}

	/**
	 * @param successHistory the successHistory to set
	 */
	public void setSuccessHistory(boolean successHistory[]) {
		this.successHistory = successHistory;
	}
	
	

}
