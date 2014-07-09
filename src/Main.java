import java.util.logging.Level;

import nof.NetworkOfFavors;
import simulator.Simulator;


public class Main {
	
	public static void main(String [] args){
		
//		boolean dynamic = false;
//		int numPeers = 10;
//		int numSteps = 100;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
		
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 5;
		double changingValue = 0.1;		
		boolean nofWithLog = false;			//with sqrt
		
		String path = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation/granted over requested/";
		String file = "";
		
		
		int [] numPeersSimulations = {100};
		int [] numStepsSimulations = {100};
		boolean [] dynamicSimulations = {true,false};
		double [] percentageCollaboratorsSimulations = {0.25, 0.75};
		double [] consumingStateProbabilitySimulations = {0.2, 0.5};
		double [] peersDemandSimulations = {2, 5};
		
		for(boolean dynamic : dynamicSimulations){
			for(int numPeers : numPeersSimulations){
				for(int numSteps : numStepsSimulations){
					for(double consumingStateProbability : consumingStateProbabilitySimulations){
						for(double percentageCollaborators : percentageCollaboratorsSimulations){
							for(double peersDemand : peersDemandSimulations){
								file = "Dynamic  "+dynamic+" -  "+numPeers+" peers - "+numSteps+" steps - "+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
										+ ""+peersDemand+" peers demand - "+returnLevelVerificationTime+" in "+returnLevelVerificationTime+" steps - "+(changingValue*100)+"% change value - NoF by "+(nofWithLog==false?"SquareRoot":"Log")
										+ ".xlsx";
								Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.SEVERE, path+file);
								s1.setupSimulation();
								s1.startSimulation();
							}
						}
					}
				}
			}
		}
		
	}

}
