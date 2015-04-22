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
			
			String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 2/SoCC2015/sameDistancefromModerate/";
			
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
			
//			double [] fixedDemand = {1.5, 1.607142857, 1.714285714, 1.821428571, 1.928571429, 2.035714286,	2.25, 2.357142857, 2.464285714, 2.678571429, 2.785714286, 2.892857143, 
//									  2, 2.142857143, 2.285714286, 2.428571429, 2.571428571, 2.714285714, 2.857142857, 3, 3.142857143, 3.285714286, 3.428571429, 3.571428571, 3.714285714, 3.857142857, 4,
//									  2.5, 2.857142857, 3.035714286, 3.214285714, 3.392857143, 3.75, 3.928571429, 4.107142857, 4.285714286, 4.464285714, 4.642857143, 4.821428571, 5};
//					
//			double [] variablePIK05 = {0.5, 0.4516129032, 0.4117647059, 0.3783783784, 0.35, 0.3255813953, 0.2857142857, 0.2692307692, 0.2545454545, 0.2295081967, 0.21875, 0.2089552239, 
//										0.3333333333, 0.3043478261, 0.28, 0.2592592593, 0.2413793103, 0.2258064516, 0.2121212121, 0.2, 0.1891891892, 0.1794871795, 0.1707317073, 0.1627906977, 0.1555555556, 0.1489361702, 0.1428571429,
//										0.25, 0.2121212121, 0.1971830986, 0.1842105263, 0.1728395062, 0.1538461538, 0.1458333333, 0.1386138614, 0.1320754717, 0.1261261261, 0.1206896552, 0.1157024793, 0.1111111111};
//			
//			double [] variablePIK1 = {	0.6666666667, 0.6222222222, 0.5833333333, 0.5490196078, 0.5185185185, 0.4912280702, 0.4444444444, 0.4242424242, 0.4057971014, 0.3733333333, 0.358974359, 0.3456790123, 
//										0.5, 0.4666666667, 0.4375, 0.4117647059, 0.3888888889, 0.3684210526, 0.35, 0.3333333333, 0.3181818182, 0.3043478261, 0.2916666667, 0.28, 0.2692307692, 0.2592592593, 0.25,
//										0.4, 0.35, 0.3294117647, 0.3111111111, 0.2947368421, 0.2666666667, 0.2545454545, 0.2434782609, 0.2333333333, 0.224, 0.2153846154, 0.2074074074, 0.2};
//			
//			double [] variablePIK2 = { 0.8, 0.7671232877, 0.7368421053, 0.7088607595, 0.6829268293, 0.6588235294, 0.6153846154, 0.5957446809, 0.5773195876, 0.5436893204, 0.5283018868, 0.5137614679, 
//										0.6666666667, 0.6363636364, 0.6086956522, 0.5833333333, 0.56, 0.5384615385, 0.5185185185, 0.5, 0.4827586207, 0.4666666667, 0.4516129032, 0.4375, 0.4242424242, 0.4117647059, 0.4,
//										0.5714285714, 0.5185185185, 0.4955752212, 0.4745762712, 0.4552845528, 0.4210526316, 0.4057971014, 0.3916083916, 0.3783783784, 0.3660130719, 0.3544303797, 0.3435582822, 0.3333333333};
//			
//			double [] variablePIK4 = { 0.8888888889, 0.8682170543, 0.8484848485, 0.8296296296, 0.8115942029, 0.7943262411, 0.7619047619, 0.7466666667, 0.7320261438, 0.7044025157, 0.6913580247, 0.6787878788, 
//										0.8, 0.7777777778, 0.7567567568, 0.7368421053, 0.7179487179, 0.7, 0.6829268293, 0.6666666667, 0.6511627907, 0.6363636364, 0.6222222222, 0.6086956522, 0.5957446809, 0.5833333333, 0.5714285714,
//										0.7272727273, 0.6829268293, 0.6627218935, 0.6436781609, 0.625698324, 0.5925925926, 0.5773195876, 0.5628140704, 0.5490196078, 0.5358851675, 0.523364486, 0.5114155251, 0.5};
			
			DecimalFormat formatter = new DecimalFormat("0.00"); 
			formatter.setRoundingMode(RoundingMode.DOWN); 
									  
//			String design = "d-fixo/";
//			
//			for(int i = 0; i < fixedDemand.length; i++){
//				double [] d = new double[1];
//				d[0] = fixedDemand[i];
//				
//				System.out.println("D = "+d[0]);
//				
//				double [] pi = new double [1];
//				pi[0] = variablePIK05[i];
//				
//				Simulator sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa05, pairwise);
//				sim.startSimulation();
//				
//				pi[0] = variablePIK1[i];
//				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa1, pairwise);
//				sim.startSimulation();
//				
//				pi[0] = variablePIK2[i];
//				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa2, pairwise);
//				sim.startSimulation();
//				
//				pi[0] = variablePIK4[i];
//				sim = new Simulator(numPeers, numSteps, pi, numberOfCollaborators, numberOfFreeRiders,
//						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//						outputDir+design+"D"+(formatter.format(fixedDemand[i])).replace(",", ".")+kappa4, pairwise);
//				sim.startSimulation();
//			}
			
			
			// PI FIXO e D VARIAVEL
			
			
			
			double [] piK05 = {0.2857142857, 0.3523316062, 0.4210526316, 0.4919786096, 0.5652173913, 0.6408839779, 0.7191011236, 0.8, 0.8837209302, 0.9704142012,
								0.2, 0.2428571429, 0.2857142857, 0.3285714286, 0.3714285714, 0.4142857143, 0.4571428571, 0.5, 0.5428571429, 0.5857142857, 0.6285714286, 0.6714285714, 0.7142857143, 0.7571428571, 0.8,
								0.1538461538, 0.1852861035, 0.2162162162, 0.2466487936, 0.2765957447, 0.3060686016, 0.335078534, 0.3636363636, 0.3917525773, 0.4194373402, 0.4467005076, 0.4735516373, 0.5, 0.5260545906, 0.5517241379
								};

			double [] piK1 = {0.2666666667, 0.3238095238, 0.380952381, 0.4380952381, 0.4952380952, 0.5523809524, 0.6095238095, 0.6666666667, 0.7238095238, 0.780952381, 0.8380952381, 0.8952380952, 0.9523809524,
								0.2, 0.2428571429, 0.2857142857, 0.3285714286, 0.3714285714, 0.4142857143, 0.4571428571, 0.5, 0.5428571429, 0.5857142857, 0.6285714286, 0.6714285714, 0.7142857143, 0.7571428571, 0.8,
								0.16, 0.1942857143, 0.2285714286, 0.2628571429, 0.2971428571, 0.3314285714, 0.3657142857, 0.4, 0.4342857143, 0.4685714286, 0.5028571429, 0.5371428571, 0.5714285714, 0.6057142857, 0.64
								};
			
			double [] piK2 = {0.2580645161, 0.3112128146, 0.3636363636, 0.4153498871, 0.466367713, 0.5167037862, 0.5663716814, 0.6153846154, 0.6637554585, 0.7114967462,0.7586206897, 0.8051391863, 0.8510638298, 0.8964059197, 0.9411764706,
								0.2, 0.2428571429, 0.2857142857, 0.3285714286, 0.3714285714, 0.4142857143, 0.4571428571, 0.5, 0.5428571429, 0.5857142857, 0.6285714286, 0.6714285714, 0.7142857143, 0.7571428571, 0.8,
								0.1632653061, 0.1991215227, 0.2352941176, 0.2717872969, 0.3086053412, 0.345752608, 0.3832335329, 0.4210526316, 0.4592145015, 0.497723824, 0.5365853659, 0.5758039816, 0.6153846154, 0.6553323029, 0.6956521739
								};
			
			double [] piK4 = {0.253968254, 0.3052749719, 0.3555555556, 0.404840484, 0.4531590414, 0.5005393743, 0.547008547, 0.5925925926, 0.6373165618, 0.6812045691, 0.7242798354, 0.7665647299, 0.8080808081, 0.8488488488, 0.8888888889,
								0.2, 0.2428571429, 0.2857142857, 0.3285714286, 0.3714285714, 0.4142857143, 0.4571428571, 0.5, 0.5428571429, 0.5857142857, 0.6285714286, 0.6714285714, 0.7142857143, 0.7571428571, 0.8,
								0.1649484536, 0.2016308377, 0.2388059701, 0.2764838467, 0.3146747352, 0.3533891851, 0.3926380368, 0.4324324324, 0.4727838258, 0.5137039937, 0.5552050473, 0.597299444, 0.64, 0.6833199033, 0.7272727273
								};

			double [] variableDK05 = {	2.25, 1.919117647, 1.6875, 1.516304348, 1.384615385, 1.280172414, 1.1953125, 1.125, 1.065789474, 1.015243902,
										3, 2.558823529, 2.25, 2.02173913, 1.846153846, 1.706896552, 1.59375, 1.5, 1.421052632, 1.353658537, 1.295454545, 1.244680851, 1.2, 1.160377358, 1.125,
										3.75, 3.198529412, 2.8125, 2.527173913, 2.307692308, 2.13362069, 1.9921875, 1.875, 1.776315789, 1.692073171, 1.619318182, 1.555851064, 1.5, 1.450471698, 1.40625};

			double [] variableDK1 = {	3.75, 3.088235294, 2.625, 2.282608696, 2.019230769, 1.810344828, 1.640625, 1.5, 1.381578947, 1.280487805, 1.193181818, 1.117021277, 1.05,
										5, 4.117647059, 3.5, 3.043478261, 2.692307692, 2.413793103, 2.1875, 2, 1.842105263, 1.707317073, 1.590909091, 1.489361702, 1.4, 1.320754717, 1.25,
										6.25, 5.147058824, 4.375, 3.804347826, 3.365384615, 3.017241379, 2.734375, 2.5, 2.302631579, 2.134146341, 1.988636364, 1.861702128, 1.75, 1.650943396, 1.5625
										};

			double [] variableDK2 = {	6.75, 5.426470588, 4.5, 3.815217391, 3.288461538, 2.870689655, 2.53125, 2.25, 2.013157895, 1.81097561, 1.636363636, 1.484042553, 1.35, 1.231132075, 1.125,
										9, 7.235294118, 6, 5.086956522, 4.384615385, 3.827586207, 3.375, 3, 2.684210526, 2.414634146, 2.181818182, 1.978723404, 1.8, 1.641509434, 1.5,
										11.25, 9.044117647, 7.5, 6.358695652, 5.480769231, 4.784482759, 4.21875, 3.75, 3.355263158, 3.018292683, 2.727272727, 2.473404255, 2.25, 2.051886792, 1.875};

			double [] variableDK4 = {	12.75, 10.10294118, 8.25, 6.880434783, 5.826923077, 4.99137931, 4.3125, 3.75, 3.276315789, 2.87195122, 2.522727273, 2.218085106, 1.95, 1.712264151, 1.5,
										17, 13.47058824, 11, 9.173913043, 7.769230769, 6.655172414, 5.75, 5, 4.368421053, 3.829268293, 3.363636364, 2.957446809, 2.6, 2.283018868, 2,
										21.25, 16.83823529, 13.75, 11.4673913, 9.711538462, 8.318965517, 7.1875, 6.25, 5.460526316, 4.786585366, 4.204545455, 3.696808511, 3.25, 2.853773585, 2.5
										};
			
			String design = "pi-fixo/";
			
			formatter = new DecimalFormat("0.0000"); 
			formatter.setRoundingMode(RoundingMode.DOWN); 
			
			
			double [] currentPI = new double[1];
			double [] d = new double [1];
			
			for(int i = 0; i < piK05.length; i++){
				
				currentPI[0] = piK05[i];						
				d[0] = variableDK05[i];
				System.out.println("k05: Pi = "+currentPI[0]+"; D = "+d[0]);
				
				Simulator sim = new Simulator(numPeers, numSteps, currentPI, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(d[0])).replace(",", ".")+kappa05, pairwise);
				sim.startSimulation();
			}
			
			for(int i = 0; i < piK1.length; i++){
				
				currentPI[0] = piK1[i];						
				d[0] = variableDK1[i];
				System.out.println("k1: Pi = "+currentPI[0]+"; D = "+d[0]);
				
				Simulator sim = new Simulator(numPeers, numSteps, currentPI, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(d[0])).replace(",", ".")+kappa1, pairwise);
				sim.startSimulation();
			}
			
			for(int i = 0; i < piK2.length; i++){
				
				currentPI[0] = piK2[i];						
				d[0] = variableDK2[i];
				System.out.println("k2: Pi = "+currentPI[0]+"; D = "+d[0]);
				
				Simulator sim = new Simulator(numPeers, numSteps, currentPI, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(d[0])).replace(",", ".")+kappa2, pairwise);
				sim.startSimulation();
			}
			
			for(int i = 0; i < piK4.length; i++){
				
				currentPI[0] = piK4[i];						
				d[0] = variableDK4[i];
				System.out.println("k4: Pi = "+currentPI[0]+"; D = "+d[0]);
				
				Simulator sim = new Simulator(numPeers, numSteps, currentPI, numberOfCollaborators, numberOfFreeRiders,
						dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
						outputDir+design+"D"+(formatter.format(d[0])).replace(",", ".")+kappa4, pairwise);
				sim.startSimulation();
			}
				
				
		}
		
		
		
		
		
	}	
	

}