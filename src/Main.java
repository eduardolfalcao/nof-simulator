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
		 * PI = 0.3, 0.4615384615, 0.6315789474, 0.7741935484
		 * #Collab = {25, 25, 25}
		 * 
		 * Free Riders
		 * C = {1, 1, 1}
		 * D = {1.5, 2, 3}
		 * PI = {9, 8, 8}
		 */
		
		int numPeers = 100;
		int numSteps = 10000;
		int [] numberOfCollaborators = new int [] {8, 9, 8};
		int [] numberOfFreeRiders = new int [] {25, 25, 25};
		boolean dynamic = true , nofWithLog = false;
		double fairnessLowerThreshold = 0.95;
		double [] peersDemand = new double [] {1.5, 2, 3};
		double [] capacitySupplied = new double [] {1, 1, 1}; 
		double changingValue = 0.05;
		long seed = 1;
		Level level = Level.INFO;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 2/SoCC2015/csv/pairwise/";
		
		double [] consumingStateProbability = new double [] {0.3012048193, 0.3012048193, 0.3012048193};
		String file = "design1Kappa05";		
		Simulator sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		
		consumingStateProbability = new double [] {0.462962963, 0.462962963, 0.462962963};
		file = "design1Kappa1";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.6329113924, 0.6329113924, 0.6329113924};
		file = "design1Kappa2";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.7751937984, 0.7751937984, 0.7751937984};
		file = "design1Kappa4";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		/*********************************************************
		 * Design 2
		 */
		
//		capacitySupplied = new double [] {1, 10, 100};
//		peersDemand = new double [] {1.5, 20, 300};
//		
//		consumingStateProbability = new double [] {0.2095193654, 0.2095193654, 0.2095193654};
//		file = "design2Kappa05";
//		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
//				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
//		sim.startSimulation();
//		
//		
//		consumingStateProbability = new double [] {0.3464506173, 0.3464506173, 0.3464506173};
//		file = "design2Kappa1";
//		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
//				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
//		sim.startSimulation();
//		
//		
//		consumingStateProbability = new double [] {0.5146131805, 0.5146131805, 0.5146131805};
//		file = "design2Kappa2";
//		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
//				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
//		sim.startSimulation();
//		
//		
//		consumingStateProbability = new double [] {0.6795308362, 0.6795308362, 0.6795308362};
//		file = "design2Kappa4";
//		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
//				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
//		sim.startSimulation();
		
		
	}
	
	

}
