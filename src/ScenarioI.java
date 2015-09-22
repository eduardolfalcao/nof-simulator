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
	
		int numSteps = 1000;
		boolean nof[] = {true};
		boolean transitive[] = {true};
		double tMin = 0.75;
		double tMax = 0.95;
		double deltaC = 0.05;
		int seed = 1;
		Level level = Level.FINE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/";
		
		DecimalFormat formatter = new DecimalFormat("0.00");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		boolean isFreeRider = false;
		
		Queue<PeerGroup> groupsOfPeers = new LinkedList<PeerGroup>();
		PeerGroup consumersGroup = new PeerGroup(1, 10, 0, 1, 0.5, isFreeRider);
		PeerGroup idlePeersGroup1 = new PeerGroup(2, 10, 0, 1, 0.5, isFreeRider);
		PeerGroup providersGroup = new PeerGroup(3, 10, 0, 1, 0.5, isFreeRider);
		groupsOfPeers.add(consumersGroup);
		groupsOfPeers.add(idlePeersGroup1);
		groupsOfPeers.add(providersGroup);
		
		double kappa = 0.5;
		
		String outputFile = outputDir + "K" + kappa +"|" + (nof[0] ? "fdnof": "sdnof") + "|" 
				+ (transitive[0] ? "transitive-": "") + "|D" + (formatter.format(1)).replace(",", ".")
				+ "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + deltaC;
		
		Simulator sim = new Simulator(groupsOfPeers, numSteps, nof[0], transitive[0], tMin, tMax, deltaC, seed, level, outputFile, kappa);
		sim.startSimulation();		
	
	}

}
