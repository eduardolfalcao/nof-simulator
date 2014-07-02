import java.util.logging.Level;

import nof.NetworkOfFavors;
import simulator.Simulator;


public class Main {
	
	public static void main(String [] args){
		
//		System.out.println(NetworkOfFavors.calculateLocalReputation(0, 4, true));
//		System.out.println(NetworkOfFavors.calculateLocalReputation(4, 0, true));
//		System.exit(0);
		
		int numPeers = 50;
		int numSteps = 200;
		double consumingStateProbability = 0.5;
		double percentageCollaborators = 0.5;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 5;
		double changingValue = 0.05;
		boolean dynamic = true;
		boolean nofWithLog = false;			//with sqrt
		
		/**
		 * Case 1:
		 * consumingStateProbability = 20, percentageCollaborators = 0.25
		 */
		
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
		s1.setupSimulation();
		s1.startSimulation();
		
	}

}
