import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import model.peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI_WithFreeRiders {

	public static void main(String[] args) {
		
		int numSteps = 100;
		boolean fdNof[] = {true};
		boolean transitive[] = {true};
		double tMin = 0.75;
		double tMax = 0.95;
		double deltaC = 0.05;
		int seed = 1;
		Level level = Level.INFO;
//		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/5-10/+fr/";
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/";
		
		double capacity = 1, demand = 0.5;
		
		Queue<PeerGroup> groupsOfPeers = new LinkedList<PeerGroup>();
		PeerGroup freeRidersGroup = new PeerGroup(1, 1, 0, capacity, Double.MAX_VALUE, true);
		boolean whiteWasher = true;
		PeerGroup consumersGroup = new PeerGroup(2, 1, 0, capacity, demand, false);
		PeerGroup idlePeersGroup = new PeerGroup(3, 1, 0, capacity, demand, false);
		PeerGroup providersGroup = new PeerGroup(4, 1, 0, capacity, demand, false);
		groupsOfPeers.add(consumersGroup);
		groupsOfPeers.add(idlePeersGroup);
		groupsOfPeers.add(providersGroup);
		
		double kappa = 0.5;		
		int n = 0;
		for(PeerGroup gp : groupsOfPeers)
			n += gp.getNumPeers();		
		n += freeRidersGroup.getNumPeers();
		double fr = (double) freeRidersGroup.getNumPeers()/n;
		
		for(boolean nof: fdNof){
			for(boolean transitivity : transitive){
				String outputFile = outputDir + "n"+ n + "|" + "fr" + fr + "|" +"K" + kappa +"|" 
						+ (nof ? "fdnof": "sdnof") + "|" + (transitivity ? "transitive": "") 
						+ "|D" + demand + "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + deltaC;
				
				System.out.println(outputFile);
				
				Simulator sim = new Simulator(groupsOfPeers, freeRidersGroup, whiteWasher, numSteps, nof, transitivity, tMin, tMax, deltaC, seed, level, outputFile, kappa);
				sim.startSimulation();
			}
		}
	
	}
	
}
