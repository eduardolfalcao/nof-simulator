import simulator.Simulator;


public class Main {
	
	public static void main(String [] args){
		
		int numPeers = 1000;
		int numSteps = 1000;
		int consumingStateProbability = 20;
		double percentageCollaborators = 0.75;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 5;
		double changingValue = 0.05;
		boolean dynamic = true;
		
		/**
		 * Case 1:
		 * consumingStateProbability = 20, percentageCollaborators = 0.25
		 */
		
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s1.setupSimulation();
		s1.startSimulation();
		
	}

}
