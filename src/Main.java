import java.util.logging.Level;

import nof.NetworkOfFavors;
import simulator.Simulator;


public class Main {
	
	public final static int replications = 1;
	
	public static void main(String [] args){
		
		long startTime = System.currentTimeMillis();
		
		int replicationIndex = 1;
		double capacitySupplied = 1;
		double changingValue = 0.05;		
		boolean nofWithLog = false;			//with sqrt
		
		
		String file = "";
		int [] numPeersSimulations = {100};									//100
		int [] numStepsSimulations = {4000};								//2000
		boolean [] dynamicSimulations = {false,true};						//{true,false};
		double [] percentageCollaboratorsSimulations = {0.2, 0.75};			//0.2, 0.75
		double [] consumingStateProbabilitySimulations = {0.2, 0.5};		//0.2, 0.5
		double [] peersDemandSimulations = {2, 5};								//2
		
		
		
	
		
		while(replicationIndex <= Main.replications){
			for(boolean dynamic : dynamicSimulations){
				for(int numPeers : numPeersSimulations){
					for(int numSteps : numStepsSimulations){
						for(double consumingStateProbability : consumingStateProbabilitySimulations){
							for(double percentageCollaborators : percentageCollaboratorsSimulations){
								for(double peersDemand : peersDemandSimulations){
										String path = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation Debug/";
										file = "Dynamic  ("+dynamic+") - "
												+numPeers+" peers - "
												+numSteps+" steps - "
												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
												+peersDemand+" peers demand - "
												+(changingValue*100)+"% change value - "
												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
												+ ".xlsx";
										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, Level.SEVERE, path+replicationIndex+"/"+file);
										s1.startSimulation();
								}
							}
						}
					}
				}
			}
			replicationIndex++;
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("tempo: "+(estimatedTime));
	}
	

}
