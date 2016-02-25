package nof;

public class NetworkOfFavors {
	
	private final int TOTAL_CAPACITY;	
	private double capacity;
	private boolean increasingCapacity;	
	
	public NetworkOfFavors(int totalCapacity) {
		super();
		this.capacity = this.TOTAL_CAPACITY = totalCapacity;		
		this.increasingCapacity = false;
	}

	public static double calculateBalance(double consumedValue, double donatedValue){
		return Math.max(0, consumedValue - donatedValue); //TODO add sqrt
	}
	
	public static void preempt(Peer provider, Peer peerToBePreempted){
		//
	}
	
	public static double getFairness(double consumed, double donated){
		if(donated == 0)
			return -1;
		else
			return consumed/donated;
	}	
	
	public static double getSatisfaction(double consumed, double requested){
		return 	getFairness(consumed, requested);
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public boolean isIncreasingCapacity() {
		return increasingCapacity;
	}

	public void setIncreasingCapacity(boolean increasingCapacity) {
		this.increasingCapacity = increasingCapacity;
	}

	public int getTOTAL_CAPACITY() {
		return TOTAL_CAPACITY;
	}

}
