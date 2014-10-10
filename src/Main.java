import java.util.logging.Level;

import nof.NetworkOfFavors;
import simulator.Simulator;


public class Main {
	
	public final static int replications = 2;
	
	public static void main(String [] args){
		
//		double [] d = new double[15];
//		
//		double initialDemand = 2;
//		double finalDemand = 4;
//		double delta = ((finalDemand - initialDemand)/14);
//		d[0] = initialDemand;
//		for(int i = 1; i <= 14; i++)
//			d[i] = (double)(initialDemand+(i*delta));
//		
//		double [] kappa = {0.5, 1, 2, 4};
//		double [][] pi = new double[15][4];
//		
//		for(int i = 0; i<15; i++){
//			for(int j = 0; j<4; j++)
//				pi[i][j]= (kappa[j]/(d[i]-1+kappa[j]));
//		}
//		
//		for(int i =0; i < 15; i++)
//			System.out.println((d[i]+"").replace(".", ","));
//		
//		for(int i =0; i < 15; i++){
//			System.out.println("PI["+i+"]");
//			for(int j = 0; j<4; j++)
//				System.out.println((pi[i][j]+"").replace(".", ","));
//		}
//		
//		double [] probability = new double[15];
//		double initialProbability = 0.2;
//		double finalProbability = 0.8;
//		delta = ((finalProbability - initialProbability)/14);
//		probability[0] = initialProbability;		
//		for(int i = 1; i <= 14; i++)
//			probability[i] = (double)(initialProbability+(i*delta));
//		
//		System.out.println("Probability");
//		for(int i =0; i < 15; i++)
//			System.out.println((probability[i]+"").replace(".", ","));
//		
//		double [][] demand = new double[15][4];
//		
//		for(int i = 0; i<15; i++){
//			for(int j = 0; j<4; j++)
//				demand[i][j]= (((kappa[j]-(kappa[j]*probability[i]))/probability[i])+1);
//		}
//		
//		for(int i =0; i < 15; i++){
//			System.out.println("D["+i+"]");
//			for(int j = 0; j<4; j++)
//				System.out.println((demand[i][j]+"").replace(".", ","));
//		}
//		
//		System.exit(0);
		
		long startTime = System.currentTimeMillis();
		
		int replicationIndex = 2;
		double capacitySupplied = 1;
		double changingValue = 0.05;		
		boolean nofWithLog = false;										//with sqrt
		int [] numPeersSimulations = {100};									//100
		double [] percentageCollaboratorsSimulations = {0.25, 0.5, 0.75};	//0.2, 0.75
		int [] numStepsSimulations = {4000};										
		boolean [] dynamicSimulations = {false,true};
		double [] consumingStateProbabilitySimulations = {0,0,0,0};
		double [] peersDemandSimulations = {0};
		
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 1...");
		
		String pathAux = "D fixo e PI variável";
		consumingStateProbabilitySimulations[0] = 0.3333333333;
		consumingStateProbabilitySimulations[1] = 0.5;
		consumingStateProbabilitySimulations[2] = 0.6666666667;
		consumingStateProbabilitySimulations[3] = 0.8;
		peersDemandSimulations[0] = 2;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
				
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 2...");
		
		consumingStateProbabilitySimulations[0] = 0.3043478261;
		consumingStateProbabilitySimulations[1] = 0.4666666667;
		consumingStateProbabilitySimulations[2] = 0.6363636364;
		consumingStateProbabilitySimulations[3] = 0.7777777778;
		peersDemandSimulations[0] = 2.142857143;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 3...");
		
		consumingStateProbabilitySimulations[0] = 0.28;
		consumingStateProbabilitySimulations[1] = 0.4375;
		consumingStateProbabilitySimulations[2] = 0.6086956522;
		consumingStateProbabilitySimulations[3] = 0.7567567568;
		peersDemandSimulations[0] = 2.285714286;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		//System.out.println("D fixo e PI variável");
		System.out.println("Case 4...");
		
		consumingStateProbabilitySimulations[0] = 0.2592592593;
		consumingStateProbabilitySimulations[1] = 0.4117647059;
		consumingStateProbabilitySimulations[2] = 0.5833333333;
		consumingStateProbabilitySimulations[3] = 0.7368421053;
		peersDemandSimulations[0] = 2.428571429;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);

		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 5...");
		
		consumingStateProbabilitySimulations[0] = 0.2413793103;
		consumingStateProbabilitySimulations[1] = 0.3888888889;
		consumingStateProbabilitySimulations[2] = 0.56;
		consumingStateProbabilitySimulations[3] = 0.7179487179;
		peersDemandSimulations[0] = 2.571428571;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 6...");
		
		consumingStateProbabilitySimulations[0] = 0.2258064516;
		consumingStateProbabilitySimulations[1] = 0.3684210526;
		consumingStateProbabilitySimulations[2] = 0.5384615385;
		consumingStateProbabilitySimulations[3] = 0.7;
		peersDemandSimulations[0] = 2.714285714;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 7...");
		
		consumingStateProbabilitySimulations[0] = 0.2121212121;
		consumingStateProbabilitySimulations[1] = 0.35;
		consumingStateProbabilitySimulations[2] = 0.5185185185;
		consumingStateProbabilitySimulations[3] = 0.6829268293;
		peersDemandSimulations[0] = 2.857142857;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 8...");
		
		consumingStateProbabilitySimulations[0] = 0.2;
		consumingStateProbabilitySimulations[1] = 0.3333333333;
		consumingStateProbabilitySimulations[2] = 0.5;
		consumingStateProbabilitySimulations[3] = 0.6666666667;
		peersDemandSimulations[0] = 3;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 9...");
		
		consumingStateProbabilitySimulations[0] = 0.1891891892;
		consumingStateProbabilitySimulations[1] = 0.3181818182;
		consumingStateProbabilitySimulations[2] = 0.4827586207;
		consumingStateProbabilitySimulations[3] = 0.6511627907;
		peersDemandSimulations[0] = 3.142857143;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 10...");
		
		consumingStateProbabilitySimulations[0] = 0.1794871795;
		consumingStateProbabilitySimulations[1] = 0.3043478261;
		consumingStateProbabilitySimulations[2] = 0.4666666667;
		consumingStateProbabilitySimulations[3] = 0.6363636364;
		peersDemandSimulations[0] = 3.285714286;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 11...");
		
		consumingStateProbabilitySimulations[0] = 0.1707317073;
		consumingStateProbabilitySimulations[1] = 0.2916666667;
		consumingStateProbabilitySimulations[2] = 0.4516129032;
		consumingStateProbabilitySimulations[3] = 0.6222222222;
		peersDemandSimulations[0] = 3.428571429;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 12...");
		
		consumingStateProbabilitySimulations[0] = 0.1627906977;
		consumingStateProbabilitySimulations[1] = 0.28;
		consumingStateProbabilitySimulations[2] = 0.4375;
		consumingStateProbabilitySimulations[3] = 0.6086956522;
		peersDemandSimulations[0] = 3.571428571;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);

		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 13...");
		
		consumingStateProbabilitySimulations[0] = 0.1555555556;
		consumingStateProbabilitySimulations[1] = 0.2692307692;
		consumingStateProbabilitySimulations[2] = 0.4242424242;
		consumingStateProbabilitySimulations[3] = 0.5957446809;
		peersDemandSimulations[0] = 3.714285714;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 14...");
		
		consumingStateProbabilitySimulations[0] = 0.1489361702;
		consumingStateProbabilitySimulations[1] = 0.2592592593;
		consumingStateProbabilitySimulations[2] = 0.41176470594;
		consumingStateProbabilitySimulations[3] = 0.5833333333;
		peersDemandSimulations[0] = 3.857142857;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 15...");
		
		consumingStateProbabilitySimulations[0] = 0.1428571429;
		consumingStateProbabilitySimulations[1] = 0.25;
		consumingStateProbabilitySimulations[2] = 0.4;
		consumingStateProbabilitySimulations[3] = 0.5714285714;
		peersDemandSimulations[0] = 4;	
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		/*****************************************************************************************************************************************/
		/*****************************************************************************************************************************************/
		/*****************************************************************************************************************************************/
		
		
		
		
		/*****************************************************************************************************************************************/
		consumingStateProbabilitySimulations = new double[1];
		peersDemandSimulations = new double[4];
		pathAux = "D variável e PI fixo";
		
		System.out.println("D variável e PI fixo");
		System.out.println("Case 1...");
		
		
		consumingStateProbabilitySimulations[0] = 0.2;
		peersDemandSimulations[0] = 3;
		peersDemandSimulations[1] = 5;
		peersDemandSimulations[2] = 9;
		peersDemandSimulations[3] = 17;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 2...");
		
		consumingStateProbabilitySimulations[0] = 0.2428571429;
		peersDemandSimulations[0] = 2.558823529;
		peersDemandSimulations[1] = 4.117647059;
		peersDemandSimulations[2] = 7.235294118;
		peersDemandSimulations[3] = 13.47058824;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
				
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 3...");
		
		consumingStateProbabilitySimulations[0] = 0.2857142857;
		peersDemandSimulations[0] = 2.25;
		peersDemandSimulations[1] = 3.5;
		peersDemandSimulations[2] = 6;
		peersDemandSimulations[3] = 11;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 4...");
		
		consumingStateProbabilitySimulations[0] = 0.3285714286;
		peersDemandSimulations[0] = 2.02173913;
		peersDemandSimulations[1] = 3.043478261;
		peersDemandSimulations[2] = 5.086956522;
		peersDemandSimulations[3] = 9.173913043;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 5...");
		
		consumingStateProbabilitySimulations[0] = 0.3714285714;
		peersDemandSimulations[0] = 1.846153846;
		peersDemandSimulations[1] = 2.692307692;
		peersDemandSimulations[2] = 4.384615385;
		peersDemandSimulations[3] = 7.769230769;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
				
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 6...");
		
		consumingStateProbabilitySimulations[0] = 0.4142857143;
		peersDemandSimulations[0] = 1.706896552;
		peersDemandSimulations[1] = 2.413793103;
		peersDemandSimulations[2] = 3.827586207;
		peersDemandSimulations[3] = 6.655172414;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 7...");
		
		consumingStateProbabilitySimulations[0] = 0.4571428571;
		peersDemandSimulations[0] = 1.59375;
		peersDemandSimulations[1] = 2.1875;
		peersDemandSimulations[2] = 3.375;
		peersDemandSimulations[3] = 5.75;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 8...");
		
		consumingStateProbabilitySimulations[0] = 0.5;
		peersDemandSimulations[0] = 1.5;
		peersDemandSimulations[1] = 2;
		peersDemandSimulations[2] = 3;
		peersDemandSimulations[3] = 5;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 9...");
		
		consumingStateProbabilitySimulations[0] = 0.5428571429;
		peersDemandSimulations[0] = 1.421052632;
		peersDemandSimulations[1] = 1.842105263;
		peersDemandSimulations[2] = 2.684210526;
		peersDemandSimulations[3] = 4.368421053;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 10...");
		
		consumingStateProbabilitySimulations[0] = 0.5857142857;
		peersDemandSimulations[0] = 1.353658537;
		peersDemandSimulations[1] = 1.707317073;
		peersDemandSimulations[2] = 2.414634146;
		peersDemandSimulations[3] = 3.829268293;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 11...");
				
		consumingStateProbabilitySimulations[0] = 0.6285714286;
		peersDemandSimulations[0] = 1.295454545;
		peersDemandSimulations[1] = 1.590909091;
		peersDemandSimulations[2] = 2.181818182;
		peersDemandSimulations[3] = 3.363636364;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 12...");
		
		consumingStateProbabilitySimulations[0] = 0.6714285714;
		peersDemandSimulations[0] = 1.244680851;
		peersDemandSimulations[1] = 1.489361702;
		peersDemandSimulations[2] = 1.978723404;
		peersDemandSimulations[3] = 2.957446809;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 13...");
		
		consumingStateProbabilitySimulations[0] = 0.7142857143;
		peersDemandSimulations[0] = 1.2;
		peersDemandSimulations[1] = 1.4;
		peersDemandSimulations[2] = 1.8;
		peersDemandSimulations[3] = 2.6;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
				
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 14...");
		
		consumingStateProbabilitySimulations[0] = 0.7571428571;
		peersDemandSimulations[0] = 1.160377358;
		peersDemandSimulations[1] = 1.320754717;
		peersDemandSimulations[2] = 1.641509434;
		peersDemandSimulations[3] = 2.283018868;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 15...");
		
		consumingStateProbabilitySimulations[0] = 0.8;
		peersDemandSimulations[0] = 1.125;
		peersDemandSimulations[1] = 1.25;
		peersDemandSimulations[2] = 1.5;
		peersDemandSimulations[3] = 2;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulations, percentageCollaboratorsSimulations, nofWithLog, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/

		

		
		long estimatedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("tempo: "+(estimatedTime));
	}
	
	private static void runSimulation(int replicationIndex, boolean [] dynamicSimulations, int [] numStepsSimulations, int [] numPeersSimulations,
			double [] consumingStateProbabilitySimulations, double [] peersDemandSimulations, double [] percentageCollaboratorsSimulations, 
			boolean nofWithLog, double capacitySupplied, double changingValue, String pathAux){
		
		while(replicationIndex <= Main.replications){
			for(boolean dynamic : dynamicSimulations){
				for(int numPeers : numPeersSimulations){
					for(int numSteps : numStepsSimulations){
						for(double consumingStateProbability : consumingStateProbabilitySimulations){
							for(double percentageCollaborators : percentageCollaboratorsSimulations){
								for(double peersDemand : peersDemandSimulations){
										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese/CCGRID 2015/experimentos/"+pathAux+"/";
										
										double k = ((consumingStateProbability*(peersDemand-1))/(1-consumingStateProbability));
										
										String file =  "k-"+(String.format("%.1f", k).replace(",", "."))+" "
												+"C-"+(dynamic==true?"dynamic":"static")+" "
												+"n-"+numPeers+" "
												+"fr-"+((int) ((1-percentageCollaborators)*100))+" "
												+"pi-"+(String.format("%.1f", consumingStateProbability*100)).replace(",", ".")+" "
												+"D-"+(String.format("%.1f", peersDemand)).replace(",", ".")
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
	}
	

}
