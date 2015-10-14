import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI_WithFreeRidersWhitewashersDifferentTs {

public static void main(String[] args) {
		
		int numSteps = 5000;
		boolean fdNof[] = {true};
		boolean transitive[] = {false, true};
		double tMin[] = {0.85, 0.95};
		double tMax = 1;
		double deltaC[] = {0.05};	
		int replication = 1;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/7-10/+frWhitewashers/";
		
		double capacity = 1, demand = 0.5;
		
		Queue<PeerGroup> groupsOfPeers = new LinkedList<PeerGroup>();
		PeerGroup freeRidersGroup = new PeerGroup(1, 75, 0, capacity, Double.MAX_VALUE, true);
		boolean whiteWasher = true;
		PeerGroup consumersGroup = new PeerGroup(2, 75, 0, capacity, demand, false);
		PeerGroup idlePeersGroup = new PeerGroup(3, 75, 0, capacity, demand, false);
		PeerGroup providersGroup = new PeerGroup(4, 75, 0, capacity, demand, false);
		groupsOfPeers.add(consumersGroup);
		groupsOfPeers.add(idlePeersGroup);
		groupsOfPeers.add(providersGroup);
		
		double kappa = 0.5;		
		int n = 0;
		for(PeerGroup gp : groupsOfPeers)
			n += gp.getNumPeers();		
		n += freeRidersGroup.getNumPeers();
		double fr = (double) freeRidersGroup.getNumPeers()/n;
		
		
		for(int seed = 1; seed<=replication; seed++){
			System.out.println("Seed: "+seed);			
			for(boolean nof: fdNof){
				System.out.println("NoF: "+(nof ? "fdnof": "sdnof"));
				for(double tauMin : tMin){
					for(boolean transitivity : transitive){
						System.out.println("Transitive: "+transitivity);
						for(double delta : deltaC){
							System.out.println("DeltaC: "+delta);
							String outputFile = outputDir + "n"+ n + "|" + "fr" + fr + "|" +"K" + kappa +"|" 
									+ (nof ? "fdnof": "sdnof") + "|" + (transitivity ? "transitive": "") 
									+ "|D" + demand + "|tMin" + tauMin + "|tMax" + tMax + "|deltaC" + delta 
									+ "|rep"+seed;						
							Simulator sim = new Simulator(groupsOfPeers, freeRidersGroup, whiteWasher, numSteps, nof, transitivity, tauMin, tMax, delta, seed, level, outputFile, kappa);
							sim.startSimulation();
						}
					}
				}	
			}
		}
	
	}
	
}
