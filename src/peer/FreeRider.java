package peer;

public class FreeRider extends Peer{

	public FreeRider(int id, double capacity, double demand, double consumingStateProbability, double idleStateProbability, double providingStateProbability, int numSteps) {
		super(id, capacity, demand, consumingStateProbability, idleStateProbability, providingStateProbability, numSteps);
		this.getRequestedHistory()[0] = demand;
		this.getConsumedHistory()[0] = 0;
		this.setDemand(demand);
	}

}
