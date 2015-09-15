import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI {
	
	public static void main(String[] args) {
	
		int numSteps = 10;
		boolean nof[] = {false};
		boolean transitive[] = {false};
		double tMin = 0.75;
		double tMax = 0.95;
		double deltaC = 0.05;
		int seed = 1;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/";
		
		DecimalFormat formatter = new DecimalFormat("0.00");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		
		Queue<PeerGroup> queue = new LinkedList<PeerGroup>();
		
		
		
		
		
		
		
		
		
		
		
		
		
		double[] kappa = new double[]{0.5, 1, 2, 4};
		
		double[] demand = { 1, 1.142857143, 1.285714286, 1.428571429, 1.571428571, 1.714285714, 1.857142857, 
				2, 2.142857143, 2.285714286, 2.428571429, 2.571428571, 2.714285714, 2.857142857, 3};
		
		double[] pK05 = { 0.3333333333, 0.3043478261, 0.28, 0.2592592593, 0.2413793103, 0.2258064516, 0.2121212121, 
				0.2, 0.1891891892, 0.1794871795, 0.1707317073, 0.1627906977, 0.1555555556, 0.1489361702, 0.1428571429 };
		double[] pK1 = { 0.5, 0.4666666667, 0.4375, 0.4117647059, 0.3888888889, 0.3684210526, 0.35, 
				0.3333333333, 0.3181818182, 0.3043478261, 0.2916666667, 0.28, 0.2692307692, 0.2592592593, 0.25 };
		double[] pK2 = { 0.6666666667, 0.6363636364, 0.6086956522, 0.5833333333, 0.56, 0.5384615385, 0.5185185185, 
				0.5, 0.4827586207, 0.4666666667, 0.4516129032, 0.4375, 0.4242424242, 0.4117647059, 0.4 };
		double[] pK4 = { 0.8, 0.7777777778, 0.7567567568, 0.7368421053, 0.7179487179, 0.7, 0.6829268293, 
				0.6666666667, 0.6511627907, 0.6363636364, 0.6222222222, 0.6086956522, 0.5957446809, 0.5833333333, 0.5714285714 };
		double[][] ps = new double[][]{pK05,pK1,pK2,pK4};
		
		int replication = 0;
		for(boolean fdNof : nof){
			for(boolean transitivity : transitive){
				for(double d : demand){
					System.out.println("Running experiment with D="+d);
					System.out.print("Kappa: ");
					
					for(int i = 0; i < ps.length; i++){
						System.out.print(kappa[i]+" ");
						
						PeerGroup collaborators = new PeerGroup(15, 100*ps[i][replication], 0, 100-(100*ps[i][replication]), 1, d, false);
						PeerGroup freeRiders = new PeerGroup(5, 100, 0, 0, 1, Double.MAX_VALUE, true);
						ArrayList<PeerGroup> groupsOfPeers = new ArrayList<PeerGroup>();
						groupsOfPeers.add(collaborators);
						groupsOfPeers.add(freeRiders);
						
						String outputFile = outputDir + "K" + kappa[i] +"|" + (fdNof ? "fdnof": "sdnof") + "|" 
						+ (transitivity ? "transitive-": "") + "|D" + (formatter.format(d)).replace(",", ".")
						+ "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + deltaC;
						
						Simulator sim = new Simulator(groupsOfPeers, numSteps, fdNof, transitivity, tMin, tMax, deltaC, seed, level, outputFile, kappa[i]);
						sim.startSimulation();
					}					
					replication++;
					System.out.println();
				}
			}
		}
	
	}

}
