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
		int numSteps = 4000;
		int [] numberOfCollaborators = new int [] {25, 25, 25};
		int [] numberOfFreeRiders = new int [] {9, 8, 8};
		boolean dynamic = true , nofWithLog = false;
		double fairnessLowerThreshold = 0.95;
		double [] peersDemand = new double [] {1.5, 2, 3};
		double [] capacitySupplied = new double [] {1, 1, 1}; 
		double changingValue = 0.05;
		long seed = 1;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/SoCC2015/csv/fr75tau95/";
		
		double [] consumingStateProbability = new double [] {0.3, 0.3, 0.3};
		String file = "design1Kappa05";		
		Simulator sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		consumingStateProbability = new double [] {0.4615384615, 0.4615384615, 0.4615384615};
		file = "design1Kappa1";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.6315789474, 0.6315789474, 0.6315789474};
		file = "design1Kappa2";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.7741935484, 0.7741935484, 0.7741935484};
		file = "design1Kappa4";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		
		/*********************************************************
		 * Design 2
		 */
		
		capacitySupplied = new double [] {1, 10, 100};
		peersDemand = new double [] {1.5, 20, 300};
		
		consumingStateProbability = new double [] {0.2086466165, 0.2086466165, 0.2086466165};
		file = "design2Kappa05";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.3452566096, 0.3452566096, 0.3452566096};
		file = "design2Kappa1";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.5132947977, 0.5132947977, 0.5132947977};
		file = "design2Kappa2";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
		consumingStateProbability = new double [] {0.6783804431, 0.6783804431, 0.6783804431};
		file = "design2Kappa4";
		sim = new Simulator(numPeers, numSteps, consumingStateProbability, numberOfCollaborators, numberOfFreeRiders,
				dynamic, nofWithLog, fairnessLowerThreshold, peersDemand, capacitySupplied, changingValue, seed, level, outputDir+file);
		sim.startSimulation();
		
		
	}
	
	

}
