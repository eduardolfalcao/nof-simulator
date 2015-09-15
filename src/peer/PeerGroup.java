package peer;

public class PeerGroup {
	
	private int groupId, numPeers;
	
	private double deviation;
	private double capacity, demand;
	private boolean freeRider;
	
	
	
	public PeerGroup(int groupId, int numPeers, double deviation,
			double capacity, double demand, boolean freeRider) {
		super();
		this.groupId = groupId;
		this.numPeers = numPeers;
		this.deviation = deviation;
		this.capacity = capacity;
		this.demand = demand;
		this.freeRider = freeRider;
	}

	public int getNumPeers() {
		return numPeers;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public double getDeviation() {
		return deviation;
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

	

}
