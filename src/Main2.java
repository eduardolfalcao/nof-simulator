import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;


public class Main2 {
		
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
		double [] capacitySupplied = new double []{1, 1, 1};
		boolean dynamic = true, pairwise = true, nofWithLog = false;
		double fairnessLowerThreshold = 0.95;
		double changingValue = 0.05;
		long seed = 1;
		Level level = Level.SEVERE;
		
		
		DecimalFormat formatter = new DecimalFormat("0.00"); 
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 2/SoCC2015/newSoccDesign/pairwise/different needs/d-fixo/";
		String kappa05 = "kappa05";
		String kappa1 = "kappa1";
		String kappa2 = "kappa2";
		String kappa4 = "kappa4";
		
		double [][] demands = new double [][] {{1.5, 2, 2.5},
												 {1.607142857, 2.142857143, 2.678571429},
												 {1.714285714, 2.285714286, 2.857142857},
												 {1.821428571, 2.428571429, 3.035714286},
												 {1.928571429, 2.571428571, 3.214285714},
												 {2.035714286, 2.714285714, 3.392857143},
												 {2.142857143, 2.857142857, 3.571428571},
												 {2.25, 3, 3.75},
												 {2.357142857, 3.142857143, 3.928571429},
												 {2.464285714, 3.285714286, 4.107142857},
												 {2.571428571, 3.428571429, 4.285714286},
												 {2.678571429, 3.571428571, 4.464285714},
												 {2.785714286, 3.714285714, 4.642857143},
												 {2.892857143, 3.857142857, 4.821428571},
												 {3, 4, 5}
												 };
		
		double [][] pis = new double [][] {{0.3333333333, 0.5, 0.6666666667, 0.8},
											{0.3043478261, 0.4666666667, 0.6363636364, 0.7777777778},
											{0.28, 0.4375, 0.6086956522, 0.7567567568},
											{0.2592592593, 0.4117647059, 0.5833333333, 0.7368421053},
											{0.2413793103, 0.3888888889, 0.56, 0.7179487179},
											{0.2258064516, 0.3684210526, 0.5384615385, 0.7},
											{0.2121212121, 0.35, 0.5185185185, 0.6829268293},
											{0.2, 0.3333333333, 0.5, 0.6666666667},
											{0.1891891892, 0.3181818182, 0.4827586207, 0.6511627907},
											{0.1794871795, 0.3043478261, 0.4666666667, 0.6363636364},
											{0.1707317073, 0.2916666667, 0.4516129032, 0.6222222222},
											{0.1627906977, 0.28, 0.4375, 0.6086956522},
											{0.1555555556, 0.2692307692, 0.4242424242, 0.5957446809},
											{0.1489361702, 0.2592592593, 0.4117647059, 0.5833333333},
											{0.1428571429, 0.25, 0.4, 0.5714285714}
											};
		
		for(int i = 0; i < demands.length; i++){
			
			double [] demand = demands[i]; 
			String file = "D("+(formatter.format(demand[0])).replace(",", ".")+","+(formatter.format(demand[1])).replace(",", ".")+","+(formatter.format(demand[2])).replace(",", ".")+")-";
			
			double [] pi = new double [] {pis[i][0], pis[i][0], pis[i][0]};			
			
			Simulator sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, outputDir+file+kappa05,pairwise);
			sim.startSimulation();
			
			pi = new double [] {pis[i][1], pis[i][1], pis[i][1]};
			sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, outputDir+file+kappa1,pairwise);
			sim.startSimulation();
			
			pi = new double [] {pis[i][2], pis[i][2], pis[i][2]};
			sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, outputDir+file+kappa2,pairwise);
			sim.startSimulation();
			
			pi = new double [] {pis[i][3], pis[i][3], pis[i][3]};
			sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
					dynamic, nofWithLog, fairnessLowerThreshold, demand, capacitySupplied, changingValue, seed, level, outputDir+file+kappa4,pairwise);
			sim.startSimulation();
			
		}
		
		
		
	}	
	

}