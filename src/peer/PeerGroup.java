package peer;

public class PeerGroup {
	
	private double consumingStateProbability, idleStateProbability, providingStateProbability;
	private double capacity, demand;
	private boolean freeRider;
	
	private int numPeers;
	
	public PeerGroup(int numPeers, double consumingStateProbability,
			double idleStateProbability, double providingStateProbability,
			double capacity, double demand, boolean freeRider) {
		super();
		this.numPeers = numPeers;
		this.consumingStateProbability = consumingStateProbability;		
		this.idleStateProbability = idleStateProbability;
		this.providingStateProbability = providingStateProbability;
		this.capacity = capacity;
		this.demand = demand;
		this.freeRider = freeRider;
	}

	public double getConsumingStateProbability() {
		return consumingStateProbability;
	}

	public double getProvidingStateProbability() {
		return providingStateProbability;
	}

	public double getIdleStateProbability() {
		return idleStateProbability;
	}

	public double getCapacity() {
		return capacity;
	}

	public double getDemand() {
		return demand;
	}

	public boolean isFreeRider() {
		return freeRider;
	}

	public int getNumPeers() {
		return numPeers;
	}

}
