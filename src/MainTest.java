import java.util.logging.Level;

import simulator.Simulator;


public class MainTest {
	
	public static void main(String[] args) {
		
		double capacity = 10;
		boolean fdNof = true, transitivity = false;
		double tMin = 0.85, tMax = 0.95, deltaC = 0.05;
		int loopbackTime = 50;	//3600
		String outputFile = "", 
				inputFile = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 5/workload-generator/tool/workload/ordered_time_workload_clust_5spt_10ups.csv";
		int seed = 1, endTime = 2592000, numPeers = 30;
		Level level = Level.INFO;
				
		
		Simulator sim = new Simulator(null, null, capacity, numPeers, fdNof, transitivity, tMin, tMax, deltaC, loopbackTime, outputFile, inputFile, seed, endTime, level);
		sim.startSimulation();
		
	}

}
