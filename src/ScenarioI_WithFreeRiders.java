import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI_WithFreeRiders {

	public static void main(String[] args) {
		
		int numSteps = 1000;
		boolean nof[] = {true};
		boolean transitive[] = {true};
		double tMin = 0.75;
		double tMax = 0.95;
		double deltaC = 0.05;
		int seed = 1;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/23-09/";
		
		DecimalFormat formatter = new DecimalFormat("0.00");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		double capacity = 1, demand = 0.5;
		
		Queue<PeerGroup> groupsOfPeers = new LinkedList<PeerGroup>();
		PeerGroup freeRidersGroup = new PeerGroup(1, 15, 0, capacity, Double.MAX_VALUE, true);
		PeerGroup consumersGroup = new PeerGroup(2, 15, 0, capacity, demand, false);
		PeerGroup idlePeersGroup = new PeerGroup(3, 15, 0, capacity, demand, false);
		PeerGroup providersGroup = new PeerGroup(4, 15, 0, capacity, demand, false);
		groupsOfPeers.add(consumersGroup);
		groupsOfPeers.add(idlePeersGroup);
		groupsOfPeers.add(providersGroup);
		
		double kappa = 0.5;		
		int n = 0;
		for(PeerGroup gp : groupsOfPeers)
			n += gp.getNumPeers();		
		n += freeRidersGroup.getNumPeers();
		double fr = (double) freeRidersGroup.getNumPeers()/n;
		
		String outputFile = outputDir + "n"+ n + "|" + "fr" + fr + "|" +"K" + kappa +"|" 
				+ (nof[0] ? "fdnof": "sdnof") + "|" + (transitive[0] ? "transitive-": "") 
				+ "|D" + demand + "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + deltaC;
		
		Simulator sim = new Simulator(groupsOfPeers, freeRidersGroup, numSteps, nof[0], transitive[0], tMin, tMax, deltaC, seed, level, outputFile, kappa);
		sim.startSimulation();
		
		
	
	}
	
}
