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
		int [] numPeersSimulations = {500};									//100
		int [] numStepsSimulations = {4000};								//2000
		boolean [] dynamicSimulations = {false};						//{true,false};
		double [] percentageCollaboratorsSimulations = {0.25,0.5,0.75};			//0.2, 0.75
		double [] consumingStateProbabilitySimulations = {0.5};		//0.2, 0.5
		double [] peersDemandSimulations = {2};								//2
		
		
		
	
		
		while(replicationIndex <= Main.replications){
			for(boolean dynamic : dynamicSimulations){
				for(int numPeers : numPeersSimulations){
					for(int numSteps : numStepsSimulations){
						for(double consumingStateProbability : consumingStateProbabilitySimulations){
							for(double percentageCollaborators : percentageCollaboratorsSimulations){
								for(double peersDemand : peersDemandSimulations){
										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese/CCGRID 2015/preliminares/k colaboradores/";
										
										file = "Dynamic  ("+dynamic+") - "
												+numPeers+" peers - "
												+numSteps+" steps - "
												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
												+peersDemand+" peers demand - "
												+(changingValue*100)+"% change value - "
												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
												+ ".xlsx";
										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, replicationIndex, Level.SEVERE, path+replicationIndex+"/"+file);
										s1.startSimulation();
										
								}
							}
						}
					}
				}
			}
			replicationIndex++;
		}
		
		
		
//		
//		double [] percentageCollaboratorsSimulations2 = {0.8};			//0.2, 0.75
//		double [] consumingStateProbabilitySimulations2 = {0.166, 0.375, 0.525, 0.7145};		//0.2, 0.5
//		double [] peersDemandSimulations2 = {2};								//2
//		replicationIndex = 1;
//		
//		
//	
//		
//		while(replicationIndex <= Main.replications){
//			for(boolean dynamic : dynamicSimulations){
//				for(int numPeers : numPeersSimulations){
//					for(int numSteps : numStepsSimulations){
//						for(double consumingStateProbability : consumingStateProbabilitySimulations2){
//							for(double percentageCollaborators : percentageCollaboratorsSimulations2){
//								for(double peersDemand : peersDemandSimulations2){
//										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Projeto - NoF/NoF/";
//										
//										file = "Dynamic  ("+dynamic+") - "
//												+numPeers+" peers - "
//												+numSteps+" steps - "
//												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
//												+peersDemand+" peers demand - "
//												+(changingValue*100)+"% change value - "
//												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
//												+ ".xlsx";
//										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, Level.SEVERE, path+replicationIndex+"/"+file);
//										s1.startSimulation();
//								}
//							}
//						}
//					}
//				}
//			}
//			replicationIndex++;
//		}
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		double [] percentageCollaboratorsSimulations3 = {0.8};			//0.2, 0.75
//		double [] consumingStateProbabilitySimulations3 = {0.3};		//0.2, 0.5
//		double [] peersDemandSimulations3 = {1.64, 2.27, 3.55, 6.09};								//2
//		replicationIndex = 1;
//		
//		
//	
//		
//		while(replicationIndex <= Main.replications){
//			for(boolean dynamic : dynamicSimulations){
//				for(int numPeers : numPeersSimulations){
//					for(int numSteps : numStepsSimulations){
//						for(double consumingStateProbability : consumingStateProbabilitySimulations3){
//							for(double percentageCollaborators : percentageCollaboratorsSimulations3){
//								for(double peersDemand : peersDemandSimulations3){
//										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Projeto - NoF/NoF/";
//										
//										file = "Dynamic  ("+dynamic+") - "
//												+numPeers+" peers - "
//												+numSteps+" steps - "
//												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
//												+peersDemand+" peers demand - "
//												+(changingValue*100)+"% change value - "
//												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
//												+ ".xlsx";
//										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, Level.SEVERE, path+replicationIndex+"/"+file);
//										s1.startSimulation();
//								}
//							}
//						}
//					}
//				}
//			}
//			replicationIndex++;
//		}
//		
//		
//		
//		
//		double [] percentageCollaboratorsSimulations4 = {0.8325, 0.5};			//0.2, 0.75
//		double [] consumingStateProbabilitySimulations4 = {0.6};		//0.2, 0.5
//		double [] peersDemandSimulations4 = {2};								//2
//		replicationIndex = 1;
//		
//		while(replicationIndex <= Main.replications){
//			for(boolean dynamic : dynamicSimulations){
//				for(int numPeers : numPeersSimulations){
//					for(int numSteps : numStepsSimulations){
//						for(double consumingStateProbability : consumingStateProbabilitySimulations4){
//							for(double percentageCollaborators : percentageCollaboratorsSimulations4){
//								for(double peersDemand : peersDemandSimulations4){
//										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Projeto - NoF/NoF/";
//										
//										file = "Dynamic  ("+dynamic+") - "
//												+numPeers+" peers - "
//												+numSteps+" steps - "
//												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
//												+peersDemand+" peers demand - "
//												+(changingValue*100)+"% change value - "
//												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
//												+ ".xlsx";
//										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, Level.SEVERE, path+replicationIndex+"/"+file);
//										s1.startSimulation();
//								}
//							}
//						}
//					}
//				}
//			}
//			replicationIndex++;
//		}
//		
//		
//		
//		double [] percentageCollaboratorsSimulations5 = {0.4};			//0.2, 0.75
//		double [] consumingStateProbabilitySimulations5 = {0.167, 0.5};		//0.2, 0.5
//		double [] peersDemandSimulations5 = {2};								//2
//		replicationIndex = 1;
//		
//		while(replicationIndex <= Main.replications){
//			for(boolean dynamic : dynamicSimulations){
//				for(int numPeers : numPeersSimulations){
//					for(int numSteps : numStepsSimulations){
//						for(double consumingStateProbability : consumingStateProbabilitySimulations5){
//							for(double percentageCollaborators : percentageCollaboratorsSimulations5){
//								for(double peersDemand : peersDemandSimulations5){
//										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Projeto - NoF/NoF/";
//										
//										file = "Dynamic  ("+dynamic+") - "
//												+numPeers+" peers - "
//												+numSteps+" steps - "
//												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
//												+peersDemand+" peers demand - "
//												+(changingValue*100)+"% change value - "
//												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
//												+ ".xlsx";
//										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, Level.SEVERE, path+replicationIndex+"/"+file);
//										s1.startSimulation();
//								}
//							}
//						}
//					}
//				}
//			}
//			replicationIndex++;
//		}
//		
//		
//		
//		
//		double [] percentageCollaboratorsSimulations6 = {0.3};			//0.2, 0.75
//		double [] consumingStateProbabilitySimulations6 = {0.6};		//0.2, 0.5
//		double [] peersDemandSimulations6 = {1.2725, 1.545};								//2
//		replicationIndex = 1;
//		
//		while(replicationIndex <= Main.replications){
//			for(boolean dynamic : dynamicSimulations){
//				for(int numPeers : numPeersSimulations){
//					for(int numSteps : numStepsSimulations){
//						for(double consumingStateProbability : consumingStateProbabilitySimulations6){
//							for(double percentageCollaborators : percentageCollaboratorsSimulations6){
//								for(double peersDemand : peersDemandSimulations6){
//										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Projeto - NoF/NoF/";
//										
//										file = "Dynamic  ("+dynamic+") - "
//												+numPeers+" peers - "
//												+numSteps+" steps - "
//												+((1-percentageCollaborators)*100)+"% freeriders - "+(consumingStateProbability*100)+"% consuming probability - "
//												+peersDemand+" peers demand - "
//												+(changingValue*100)+"% change value - "
//												+ "NoF by "+(nofWithLog==false?"SquareRoot":"Log")
//												+ ".xlsx";
//										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, nofWithLog, peersDemand, capacitySupplied, changingValue, Level.SEVERE, path+replicationIndex+"/"+file);
//										s1.startSimulation();
//								}
//							}
//						}
//					}
//				}
//			}
//			replicationIndex++;
//		}
		
		
		
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("tempo: "+(estimatedTime));
	}
	

}
