import java.util.logging.Level;

import simulator.Simulator;


public class Main {
		
	public static void main(String [] args){
		
		/**
		 * Design 1:
		 * 
		 * Collaborators
		 * C = {1, 1, 1}
		 * D = {1.5, 2, 3}
		 * PI = {0.3, 0.4615384615, 0.6315789474, 0.7741935484}
		 * #Collab = {0.233, 0.233, 0.233}
		 * 
		 * Free Riders
		 * C = {1, 1, 1}
		 * D = {1.5, 2, 3}
		 * PI = {1, 1, 1}
		 */
		
		int numPeers = 100;
		int numSteps = 1000;
		double [] consumingStateProbability = new double [] {0.3, 0.4615384615, 0.6315789474, 0.7741935484};
		int [] numberOfCollaborators = new int [] {24, 23, 23};
		int [] numberOfFreeRiders = new int [] {10, 10, 10};
		boolean dynamic = true , nofWithLog = true;
		double fairnessLowerThreshold = 0.9;
		double [] peersDemand = new double [] {1.5, 2, 3};
		double [] capacitySupplied = new double [] {1, 1, 1}; 
		double changingValue = 0.05;
		long seed = 1;
		Level level = null;
		String outputFile = "";
		
		Simulator sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputFile);

		
	}
	
	

}
