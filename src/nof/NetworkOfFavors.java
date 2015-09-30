package nof;

public class NetworkOfFavors {
	
	public static double calculateBalance(double consumedValue, double donatedValue){
		return Math.max(0, consumedValue - donatedValue);
	}

}
