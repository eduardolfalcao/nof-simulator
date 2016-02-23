import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import model.peer.PeerGroup;
import simulator.Simulator;


public class ScenarioI {
	
	public static void main(String[] args) {
	
		int numSteps = 5000;
		boolean fdNof[] = {true};
		boolean transitive[] = {false, true};
		double tMin = 0.75;
		double tMax = 1;
		double deltaC[] = {0.05};
		int replication = 30;
		Level level = Level.SEVERE;
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/14-10/-fr/";
		
		boolean isFreeRider = false, whiteWasher = false;
		double capacity = 1, demand = 0.5;
		
		Queue<PeerGroup> groupsOfPeers = new LinkedList<PeerGroup>();
		PeerGroup consumersGroup = new PeerGroup(1, 100, 0, capacity, demand, isFreeRider);
		PeerGroup idlePeersGroup = new PeerGroup(2, 100, 0, capacity, demand, isFreeRider);
		PeerGroup providersGroup = new PeerGroup(3, 100, 0, capacity, demand, isFreeRider);
		groupsOfPeers.add(consumersGroup);
		groupsOfPeers.add(idlePeersGroup);
		groupsOfPeers.add(providersGroup);
		
		double kappa = 0.5;		
		int n = 0;
		for(PeerGroup gp : groupsOfPeers)
			n += gp.getNumPeers();		
		double fr = 0;
		
		for(int seed = 11; seed<=replication; seed++){
			System.out.println("Seed: "+seed);			
			for(boolean nof: fdNof){
				System.out.println("NoF: "+(nof ? "fdnof": "sdnof"));
				for(boolean transitivity : transitive){
					System.out.println("Transitive: "+transitivity);
					for(double delta : deltaC){
						System.out.println("DeltaC: "+delta);
						String outputFile = outputDir + "n"+ n + "|" + "fr" + fr + "|" +"K" + kappa +"|" 
								+ (nof ? "fdnof": "sdnof") + "|" + (transitivity ? "transitive": "") 
								+ "|D" + demand + "|tMin" + tMin + "|tMax" + tMax + "|deltaC" + delta 
								+ "|rep"+seed;						
						Simulator sim = new Simulator(groupsOfPeers, null, whiteWasher, numSteps, nof, transitivity, tMin, tMax, delta, seed, level, outputFile, kappa);
						sim.startSimulation();
					}
				}
			}	
		}
		
	
	}

}
