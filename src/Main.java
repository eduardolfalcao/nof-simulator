import java.math.RoundingMode;
import java.text.DecimalFormat;
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
		double [] capacitySupplied;
		double [] demand;
		boolean dynamic = false , nofWithLog = false;
		double fairnessLowerThreshold = 0.95;
		double changingValue = 0.05;
		long seed = 1;
		Level level = Level.SEVERE;
		
		
		boolean [] pairwiseArray = {false};
		
		
		for(boolean pairwise : pairwiseArray){
			
			String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 2/SoCC2015/differentDemands/";
//			if(pairwise)
//				outputDir += "pairwise/";
//			else
//				outputDir += "global/";
			
			outputDir += "sd-nof/";
			
			String kappa05 = "kappa05";
			String kappa1 = "kappa1";
			String kappa2 = "kappa2";
			String kappa4 = "kappa4";		
			
			DecimalFormat formatter = new DecimalFormat("0.0"); 
			formatter.setRoundingMode(RoundingMode.DOWN); 
			
			// C fixo e D variavel
			capacitySupplied = new double []{1, 1, 1};
			demand = new double []{1.5, 2, 3};
			double piK05 [] = new double []{0.3012048193, 0.3012048193, 0.3012048193};
			double piK1[] = new double []{0.462962963, 0.462962963, 0.462962963};
			double piK2[] = new double []{0.6329113924, 0.6329113924, 0.6329113924};
			double piK4[] = new double []{0.7751937984, 0.6329113924, 0.6329113924};
			
			String design = "fixedC-variableD/";

			Simulator sim = new Simulator(numPeers, numSteps, piK05, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK05[0])).replace(",", ".")+kappa05, pairwise);
			sim.startSimulation();
			
			sim = new Simulator(numPeers, numSteps, piK1, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK1[0])).replace(",", ".")+kappa1, pairwise);
			sim.startSimulation();
			
			sim = new Simulator(numPeers, numSteps, piK2, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK2[0])).replace(",", ".")+kappa2, pairwise);
			sim.startSimulation();
			
			sim = new Simulator(numPeers, numSteps, piK4, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK4[0])).replace(",", ".")+kappa4, pairwise);
			sim.startSimulation();			
			
			
			
			// C variavel e D variavel
			capacitySupplied = new double []{1, 10, 100};
			demand = new double []{1.5, 20, 300};
			piK05 = new double []{0.2095193654, 0.2095193654, 0.2095193654};
			piK1 = new double []{0.3464506173, 0.3464506173, 0.3464506173};
			piK2 = new double []{0.5146131805, 0.5146131805, 0.5146131805};
			piK4 = new double []{0.6795308362, 0.6795308362, 0.6795308362};						
			
			design = "variableC-variableD/";

			sim = new Simulator(numPeers, numSteps, piK05, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK05[0])).replace(",", ".")+kappa05, pairwise);
			sim.startSimulation();
			
			sim = new Simulator(numPeers, numSteps, piK1, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK1[0])).replace(",", ".")+kappa1, pairwise);
			sim.startSimulation();
			
			sim = new Simulator(numPeers, numSteps, piK2, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK2[0])).replace(",", ".")+kappa2, pairwise);
			sim.startSimulation();
			
			sim = new Simulator(numPeers, numSteps, piK4, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, 
					outputDir+design+"PI"+(formatter.format(piK4[0])).replace(",", ".")+kappa4, pairwise);
			sim.startSimulation();
			
		}
				
		
	}	
	

}
