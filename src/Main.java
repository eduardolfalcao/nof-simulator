import java.util.logging.Level;

import nof.NetworkOfFavors;
import simulator.Simulator;


public class Main {
	
	public final static int replications = 1;
	
	public static void main(String [] args){
		
		int replication = 1;
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 5;
		double changingValue = 0.05;		
		boolean nofWithLog = false;			//with sqrt
		
		
		String file = "";
		
		boolean [] fairnessBased = {true,false};
		int [] numPeersSimulations = {100};
		int [] numStepsSimulations = {1000};
		boolean [] dynamicSimulations = {true,false};
		double [] percentageCollaboratorsSimulations = {0.25, 0.75};			//0.25, 0.75
		double [] consumingStateProbabilitySimulations = {0.2, 0.5};			//0.5
		double [] peersDemandSimulations = {2, 5};								//2, 5
		
		
		
	
		
		while(replication <= Main.replications){
			for(boolean fairness : fairnessBased){
				for(boolean dynamic : dynamicSimulations){
					for(int numPeers : numPeersSimulations){
						for(int numSteps : numStepsSimulations){
							for(double consumingStateProbability : consumingStateProbabilitySimulations){
								for(double percentageCollaborators : percentageCollaboratorsSimulations){
									for(double peersDemand : peersDemandSimulations){
										String path = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation/"+(fairness==true?"consumed over donated":"granted over requested")+"/";
										file = "Dynamic  "+dynamic+" - Control "+(fairness==true?"consumedOverDonated":"grantedOverRequested")+" - "							
												+numPeers+" peers - "+numSteps+" steps - "+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
												+ ""+peersDemand+" peers demand - "+returnLevelVerificationTime+" in "+returnLevelVerificationTime+" steps - "+(changingValue*100)+"% change value - NoF by "+(nofWithLog==false?"SquareRoot":"Log")
												+ ".xlsx";
										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, fairness, Level.SEVERE, path+replication+"/"+file);
										s1.setupSimulation();
										s1.startSimulation();
									}
								}
							}
						}
					}
				}
			}
			replication++;
		}
		
	}

}
