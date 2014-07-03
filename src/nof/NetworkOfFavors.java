package nof;

public class NetworkOfFavors {


	/**
	 * @param consumedValue value that peer A consumed from peer B	
	 * @param donatedValue value that peer A donated to peer B
	 * @param withLog function to try to prioritize who have already donated
	 * @return the reputation
	 */
	public static double calculateLocalReputation(double consumedValue, double donatedValue, boolean withLog){
		if(withLog)
			return Math.max(0, consumedValue - donatedValue + Math.log(consumedValue));
		else
			return Math.max(0, consumedValue - donatedValue + Math.sqrt(consumedValue));
	}

}
