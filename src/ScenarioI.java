import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI {
	
	public static void main(String[] args) {
	
		int numSteps = 1000;
		boolean nof[] = {true};
		boolean transitive[] = {true};
		double tMin = 0.75;
		double tMax = 0.95;
		double deltaC = 0.05;
		int seed = 1;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/1-10/-fr/";
		
		boolean isFreeRider = false;
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
		
		String outputFile = outputDir + "n"+ n + "|" + "fr" + fr + "|" +"K" + kappa +"|" 
				+ (nof[0] ? "fdnof": "sdnof") + "|" + (transitive[0] ? "transitive": "") 
				+ "|D" + demand + "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + deltaC;
		
		Simulator sim = new Simulator(groupsOfPeers, null, numSteps, nof[0], transitive[0], tMin, tMax, deltaC, seed, level, outputFile, kappa);
		sim.startSimulation();
		
		
	
	}

}
