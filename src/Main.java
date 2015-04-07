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
		int [] numberOfCollaborators = new int [] {25};
		int [] numberOfFreeRiders = new int [] {75};
		double [] capacitySupplied = new double []{1};
		boolean dynamic = true , nofWithLog = false;
		double fairnessLowerThreshold = 0.95;
		double changingValue = 0.05;
		long seed = 1;
		Level level = Level.SEVERE;
		
		
		
		boolean [] pairwiseArray = {true};
		
		
		for(boolean pairwise : pairwiseArray){
			
			String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 2/SoCC2015/newSoccDesign/pairwise/";
			
			String kappa05 = "kappa05";
			String kappa1 = "kappa1";
			String kappa2 = "kappa2";
			String kappa4 = "kappa4";
			
			/* D-LOWER
			1.5				OK
			1.607142857		OK
			1.714285714		OK
			1.821428571		OK
			1.928571429		OK
			2.035714286		OK
			2.142857143		ALREADY ON LIST
			2.25			OK
			2.357142857		OK
			2.464285714		OK
			2.571428571		ALREADY ON LIST
			2.678571429		OK
			2.785714286		OK
			2.892857143		OK
			3				ALREADY ON LIST
			*/
			
			/* D-UPPER
			2.5				OK
			2.678571429		ALREADY ON LIST
			2.857142857		OK
			3.035714286		OK
			3.214285714		OK
			3.392857143		OK
			3.571428571		ALREADY ON LIST
			3.75			OK
			3.928571429		OK
			4.107142857		OK
			4.285714286		OK
			4.464285714		OK
			4.642857143		OK
			4.821428571		OK
			5				OK
			 */
			
			
			// D FIXO e PI VARIAVEL
			
			double [] fixedDemand = {1.5, 1.607142857, 1.714285714, 1.821428571, 1.928571429, 2.035714286,	2.25, 2.357142857, 2.464285714, 2.678571429, 2.785714286, 2.892857143, 
									  2, 2.142857143, 2.285714286, 2.428571429, 2.571428571, 2.714285714, 2.857142857, 3, 3.142857143, 3.285714286, 3.428571429, 3.571428571, 3.714285714, 3.857142857, 4,
									  2.5, 2.857142857, 3.035714286, 3.214285714, 3.392857143, 3.75, 3.928571429, 4.107142857, 4.285714286, 4.464285714, 4.642857143, 4.821428571, 5};
					
			double [] variablePIK05 = {0.5, 0.4516129032, 0.4117647059, 0.3783783784, 0.35, 0.3255813953, 0.2857142857, 0.2692307692, 0.2545454545, 0.2295081967, 0.21875, 0.2089552239, 
										0.3333333333, 0.3043478261, 0.28, 0.2592592593, 0.2413793103, 0.2258064516, 0.2121212121, 0.2, 0.1891891892, 0.1794871795, 0.1707317073, 0.1627906977, 0.1555555556, 0.1489361702, 0.1428571429,
										0.25, 0.2121212121, 0.1971830986, 0.1842105263, 0.1728395062, 0.1538461538, 0.1458333333, 0.1386138614, 0.1320754717, 0.1261261261, 0.1206896552, 0.1157024793, 0.1111111111};
			
			double [] variablePIK1 = {	0.6666666667, 0.6222222222, 0.5833333333, 0.5490196078, 0.5185185185, 0.4912280702, 0.4444444444, 0.4242424242, 0.4057971014, 0.3733333333, 0.358974359, 0.3456790123, 
										0.5, 0.4666666667, 0.4375, 0.4117647059, 0.3888888889, 0.3684210526, 0.35, 0.3333333333, 0.3181818182, 0.3043478261, 0.2916666667, 0.28, 0.2692307692, 0.2592592593, 0.25,
										0.4, 0.35, 0.3294117647, 0.3111111111, 0.2947368421, 0.2666666667, 0.2545454545, 0.2434782609, 0.2333333333, 0.224, 0.2153846154, 0.2074074074, 0.2};
			
			double [] variablePIK2 = { 0.8, 0.7671232877, 0.7368421053, 0.7088607595, 0.6829268293, 0.6588235294, 0.6153846154, 0.5957446809, 0.5773195876, 0.5436893204, 0.5283018868, 0.5137614679, 
										0.6666666667, 0.6363636364, 0.6086956522, 0.5833333333, 0.56, 0.5384615385, 0.5185185185, 0.5, 0.4827586207, 0.4666666667, 0.4516129032, 0.4375, 0.4242424242, 0.4117647059, 0.4,
										0.5714285714, 0.5185185185, 0.4955752212, 0.4745762712, 0.4552845528, 0.4210526316, 0.4057971014, 0.3916083916, 0.3783783784, 0.3660130719, 0.3544303797, 0.3435582822, 0.3333333333};
			
			double [] variablePIK4 = { 0.8888888889, 0.8682170543, 0.8484848485, 0.8296296296, 0.8115942029, 0.7943262411, 0.7619047619, 0.7466666667, 0.7320261438, 0.7044025157, 0.6913580247, 0.6787878788, 
										0.8, 0.7777777778, 0.7567567568, 0.7368421053, 0.7179487179, 0.7, 0.6829268293, 0.6666666667, 0.6511627907, 0.6363636364, 0.6222222222, 0.6086956522, 0.5957446809, 0.5833333333, 0.5714285714,
										0.7272727273, 0.6829268293, 0.6627218935, 0.6436781609, 0.625698324, 0.5925925926, 0.5773195876, 0.5628140704, 0.5490196078, 0.5358851675, 0.523364486, 0.5114155251, 0.5};
			
			DecimalFormat formatter = new DecimalFormat("0.00"); 
			formatter.setRoundingMode(RoundingMode.DOWN); 
									  
			String design = "d-fixo/";
			
			for(int i = 0; i < fixedDemand.length; i++){
				double [] d = new double[1];
				d[0] = fixedDemand[i];
				
				System.out.println("D = "+d[0]);
				
				double [] pi = new double [1];
				pi[0] = variablePIK05[i];
				
				Simulator sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa05, pairwise);
				sim.startSimulation();
				
				pi[0] = variablePIK1[i];
				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa1, pairwise);
				sim.startSimulation();
				
				pi[0] = variablePIK2[i];
				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa2, pairwise);
				sim.startSimulation();
				
				pi[0] = variablePIK4[i];
				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa4, pairwise);
				sim.startSimulation();
			}
			
			
//			// PI FIXO e D VARIAVEL
//			
//			double [] fixedPI = {0.2, 0.2428571429, 0.2857142857, 0.3285714286, 0.3714285714, 0.4142857143, 0.4571428571, 
//								0.5, 0.5428571429, 0.5857142857, 0.6285714286, 0.6714285714, 0.7142857143, 0.7571428571, 0.8};
//
//
//			double [] variableDK05 = {3, 2.558823529, 2.25, 2.02173913, 1.846153846, 
//					1.706896552, 1.59375, 1.5, 1.421052632, 1.353658537,
//					1.295454545, 1.244680851, 1.2, 1.160377358, 1.125};
//
//			double [] variableDK1 = {5, 4.117647059, 3.5, 3.043478261, 2.692307692,
//				 	2.413793103, 2.1875, 2, 1.842105263, 1.707317073,
//					1.590909091, 1.489361702, 1.4, 1.320754717, 1.25};
//
//			double [] variableDK2 = {9, 7.235294118, 6, 5.086956522, 4.384615385,
//					3.827586207, 3.375, 3, 2.684210526, 2.414634146,
//					2.181818182, 1.978723404, 1.8, 1.641509434, 1.5};
//
//			double [] variableDK4 = {17, 13.47058824, 11, 9.173913043, 7.769230769,
//					6.655172414, 5.75, 5, 4.368421053, 3.829268293,
//					3.363636364, 2.957446809, 2.6, 2.283018868, 2};
//			
//			design = "pi-fixo/";
//			
//			formatter = new DecimalFormat("0.00"); 
//			formatter.setRoundingMode(RoundingMode.DOWN); 
//			
//			for(int i = 0; i < fixedPI.length; i++){
//				double [] pi = new double[1];
//				pi[0] = fixedPI[i];
//				
//				System.out.println("PI = "+pi[0]);
//				double [] d = new double [1];
//				d[0] = variableDK05[i];
//				
//				Simulator sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"PI"+(formatter.format(fixedPI[i])).replace(",", ".")+kappa05, pairwise);
//				sim.startSimulation();
//				
//				d[0] = variableDK1[i];
//				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"PI"+(formatter.format(fixedPI[i])).replace(",", ".")+kappa1, pairwise);
//				sim.startSimulation();
//				
//				d[0] = variableDK2[i];
//				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"PI"+(formatter.format(fixedPI[i])).replace(",", ".")+kappa2, pairwise);
//				sim.startSimulation();
//				
//				d[0] = variableDK4[i];
//				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"PI"+(formatter.format(fixedPI[i])).replace(",", ".")+kappa4, pairwise);
//				sim.startSimulation();
//			}
		}
		
		
		
		
		
	}	
	

}