import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;


public class MainDebugg {
	
	
	public static void main(String [] args){
	
		int numPeers = 100;
		int numSteps = 10000;
		int [] numberOfCollaborators = new int [] {8, 9, 8};
		int [] numberOfFreeRiders = new int [] {25, 25, 25};
		double [] capacitySupplied = new double []{1, 1, 1};
		boolean dynamic = true , nofWithLog = false;
		double fairnessLowerThreshold = 0.95;
		double changingValue = 0.05;
		long seed = 1;
		Level level = Level.SEVERE;
		
		
		
		boolean pairwise = true;
	
		DecimalFormat formatter = new DecimalFormat("0.00"); 
		formatter.setRoundingMode(RoundingMode.DOWN); 
		formatter = new DecimalFormat("0.0000"); 
		formatter.setRoundingMode(RoundingMode.DOWN);
		
//		double [] currentPI = {0.1386138614};
//		double [] d = {4.107142857};
		
		double [] currentPI = {0.5857142857, 0.5857142857, 0.5857142857};
		double [] d = {1.015243902,	 1.353658537, 1.692073171};
		
		
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 2/SoCC2015/newSoccDesign/pairwise/different needs/pi-fixo/debug/";
		String kappa05 = "kappa05";
		
		
//		Simulator sim = new Simulator(numPeers, numSteps, currentPI, numberOfCollaborators, numberOfFreeRiders,
//				dynamic, nofWithLog, fairnessLowerThreshold, d, capacitySupplied, changingValue, seed, level, 
//				outputDir+"D"+(formatter.format(d[0])).replace(",", ".")+kappa05, pairwise);
//		sim.startSimulation();
	}

}
