import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI {
	
	public static void main(String[] args) {
	
		int numSteps = 5000;
		boolean fdNof[] = {false, true};
		boolean transitive[] = {false, true};
		double tMin = 0.75;
		double tMax = 0.95;
		double deltaC = 0.05;
		int seed = 1;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/2-10/-fr/";
		
		boolean isFreeRider = false, whiteWasher = false;
		double capacity = 1, demand = 0.5;
		
		Queue<PeerGroup> groupsOfPeers = new LinkedList<PeerGroup>();
		PeerGroup consumersGroup = new PeerGroup(1, 20, 0, capacity, demand, isFreeRider);
		PeerGroup idlePeersGroup = new PeerGroup(2, 20, 0, capacity, demand, isFreeRider);
		PeerGroup providersGroup = new PeerGroup(3, 20, 0, capacity, demand, isFreeRider);
		groupsOfPeers.add(consumersGroup);
		groupsOfPeers.add(idlePeersGroup);
		groupsOfPeers.add(providersGroup);
		
		double kappa = 0.5;		
		int n = 0;
		for(PeerGroup gp : groupsOfPeers)
			n += gp.getNumPeers();		
		double fr = 0;
		
		for(boolean nof: fdNof){
			for(boolean transitivity : transitive){
				String outputFile = outputDir + "n"+ n + "|" + "fr" + fr + "|" +"K" + kappa +"|" 
						+ (nof ? "fdnof": "sdnof") + "|" + (transitivity ? "transitive": "") 
						+ "|D" + demand + "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + deltaC;
				
				System.out.println(outputFile);
				
				Simulator sim = new Simulator(groupsOfPeers, null, whiteWasher, numSteps, nof, transitivity, tMin, tMax, deltaC, seed, level, outputFile, kappa);
				sim.startSimulation();
			}
		}
	
	}

}
