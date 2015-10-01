package peer;

public class FreeRider extends Peer{

	public FreeRider(int id, double capacity, double demand, State state, int groupId, double deviation, int numSteps) {
		super(id, capacity, demand, state, groupId, deviation, numSteps);
		this.getRequestedHistory()[0] = demand;
		this.getConsumedHistory()[0] = 0;
		this.setDemand(demand);
	}
	
	@Override
	public String toString(){
		return "FreeRider: id = "+id+"; demand = "+demand;
	}

}
