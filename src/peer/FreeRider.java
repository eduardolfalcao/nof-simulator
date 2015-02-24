package peer;

public class FreeRider extends Peer{
	
	private boolean successHistory[];
	
	/**
	 * @param demand the amount of demand for resources from another peer
	 * @param peerId the id of the peer
	 * @param numSteps the number of steps of the simulation
	 */
	public FreeRider(double demand, int peerId, int numSteps, char [] productsDemanded) {
		super(demand, peerId, true, numSteps, null, productsDemanded);
		this.setSuccessHistory(new boolean[numSteps]);
		
		int initialStep = 0;
		this.getRequestedHistory()[initialStep] = this.getInitialDemand();
		this.getConsumedHistory()[initialStep] = 0;
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
