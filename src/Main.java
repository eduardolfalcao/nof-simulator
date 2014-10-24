import java.util.logging.Level;

import simulator.Simulator;


public class Main {
	
	public final static int replications = 2;
	
	public static void main(String [] args){
		
		long startTime = System.currentTimeMillis();
		
		int replicationIndex = 2;
		double capacitySupplied = 1;
		double changingValue = 0.05;		
		boolean nofWithLog = false;										
		int [] numPeersSimulations = {100};									
		double [] percentageFreeRidersSimulationsLowK = {0.0};				
		double [] percentageFreeRidersSimulationsHighK = {0.01,0.1};		 
		int [] numStepsSimulations = {4000};										
		boolean [] dynamicSimulations = {false,true};							
		double [] consumingStateProbabilitySimulationsLowK = {0};
		double [] consumingStateProbabilitySimulationsHighK = {0,0,0};
		double [] peersDemandSimulations = {0};
		double fairnessThreshold = 0.95;
		
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 1...");
		
		String pathAux = "D fixo e PI variável";
		consumingStateProbabilitySimulationsLowK[0] = 0.3333333333;
		consumingStateProbabilitySimulationsHighK[0] = 0.5;
		consumingStateProbabilitySimulationsHighK[1] = 0.6666666667;
		consumingStateProbabilitySimulationsHighK[2] = 0.8;
		peersDemandSimulations[0] = 2;
		percentageFreeRidersSimulationsLowK[0] = 0.25;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		System.exit(0);
				
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 2...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.3043478261;
		consumingStateProbabilitySimulationsHighK[0] = 0.4666666667;
		consumingStateProbabilitySimulationsHighK[1] = 0.6363636364;
		consumingStateProbabilitySimulationsHighK[2] = 0.7777777778;
		peersDemandSimulations[0] = 2.142857143;
		percentageFreeRidersSimulationsLowK[0] = 0.2333333333;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 3...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.28;
		consumingStateProbabilitySimulationsHighK[0] = 0.4375;
		consumingStateProbabilitySimulationsHighK[1] = 0.6086956522;
		consumingStateProbabilitySimulationsHighK[2] = 0.7567567568;
		peersDemandSimulations[0] = 2.285714286;	
		percentageFreeRidersSimulationsLowK[0] = 0.21875;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		//System.out.println("D fixo e PI variável");
		System.out.println("Case 4...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.2592592593;
		consumingStateProbabilitySimulationsHighK[0] = 0.4117647059;
		consumingStateProbabilitySimulationsHighK[1] = 0.5833333333;
		consumingStateProbabilitySimulationsHighK[2] = 0.7368421053;
		peersDemandSimulations[0] = 2.428571429;	
		percentageFreeRidersSimulationsLowK[0] = 0.2058823529;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);

		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 5...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.2413793103;
		consumingStateProbabilitySimulationsHighK[0] = 0.3888888889;
		consumingStateProbabilitySimulationsHighK[1] = 0.56;
		consumingStateProbabilitySimulationsHighK[2] = 0.7179487179;
		peersDemandSimulations[0] = 2.571428571;
		percentageFreeRidersSimulationsLowK[0] = 0.1944444444;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 6...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.2258064516;
		consumingStateProbabilitySimulationsHighK[0] = 0.3684210526;
		consumingStateProbabilitySimulationsHighK[1] = 0.5384615385;
		consumingStateProbabilitySimulationsHighK[2] = 0.7;
		peersDemandSimulations[0] = 2.714285714;	
		percentageFreeRidersSimulationsLowK[0] = 0.1842105263;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 7...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.2121212121;
		consumingStateProbabilitySimulationsHighK[0] = 0.35;
		consumingStateProbabilitySimulationsHighK[1] = 0.5185185185;
		consumingStateProbabilitySimulationsHighK[2] = 0.6829268293;
		peersDemandSimulations[0] = 2.857142857;
		percentageFreeRidersSimulationsLowK[0] = 0.175;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 8...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.2;
		consumingStateProbabilitySimulationsHighK[0] = 0.3333333333;
		consumingStateProbabilitySimulationsHighK[1] = 0.5;
		consumingStateProbabilitySimulationsHighK[2] = 0.6666666667;
		peersDemandSimulations[0] = 3;	
		percentageFreeRidersSimulationsLowK[0] = 0.1666666667;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 9...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1891891892;
		consumingStateProbabilitySimulationsHighK[0] = 0.3181818182;
		consumingStateProbabilitySimulationsHighK[1] = 0.4827586207;
		consumingStateProbabilitySimulationsHighK[2] = 0.6511627907;
		peersDemandSimulations[0] = 3.142857143;
		percentageFreeRidersSimulationsLowK[0] = 0.1590909091;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 10...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1794871795;
		consumingStateProbabilitySimulationsHighK[0] = 0.3043478261;
		consumingStateProbabilitySimulationsHighK[1] = 0.4666666667;
		consumingStateProbabilitySimulationsHighK[2] = 0.6363636364;
		peersDemandSimulations[0] = 3.285714286;	
		percentageFreeRidersSimulationsLowK[0] = 0.152173913;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 11...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1707317073;
		consumingStateProbabilitySimulationsHighK[0] = 0.2916666667;
		consumingStateProbabilitySimulationsHighK[1] = 0.4516129032;
		consumingStateProbabilitySimulationsHighK[2] = 0.6222222222;
		peersDemandSimulations[0] = 3.428571429;
		percentageFreeRidersSimulationsLowK[0] = 0.1458333333;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 12...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1627906977;
		consumingStateProbabilitySimulationsHighK[0] = 0.28;
		consumingStateProbabilitySimulationsHighK[1] = 0.4375;
		consumingStateProbabilitySimulationsHighK[2] = 0.6086956522;
		peersDemandSimulations[0] = 3.571428571;	
		percentageFreeRidersSimulationsLowK[0] = 0.14;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);

		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 13...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1555555556;
		consumingStateProbabilitySimulationsHighK[0] = 0.2692307692;
		consumingStateProbabilitySimulationsHighK[1] = 0.4242424242;
		consumingStateProbabilitySimulationsHighK[2] = 0.5957446809;
		peersDemandSimulations[0] = 3.714285714;	
		percentageFreeRidersSimulationsLowK[0] = 0.1346153846;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 14...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1489361702;
		consumingStateProbabilitySimulationsHighK[0] = 0.2592592593;
		consumingStateProbabilitySimulationsHighK[1] = 0.41176470594;
		consumingStateProbabilitySimulationsHighK[2] = 0.5833333333;
		peersDemandSimulations[0] = 3.857142857;	
		percentageFreeRidersSimulationsLowK[0] = 0.1296296296;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D fixo e PI variável");
		System.out.println("Case 15...");
		
		consumingStateProbabilitySimulationsLowK[0] = 0.1428571429;
		consumingStateProbabilitySimulationsHighK[0] = 0.25;
		consumingStateProbabilitySimulationsHighK[1] = 0.4;
		consumingStateProbabilitySimulationsHighK[2] = 0.5714285714;
		peersDemandSimulations[0] = 4;	
		percentageFreeRidersSimulationsLowK[0] = 0.125;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsLowK, 
				peersDemandSimulations, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulationsHighK, 
			peersDemandSimulations, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		/*****************************************************************************************************************************************/
		/*****************************************************************************************************************************************/
		/*****************************************************************************************************************************************/
		
		
		
		
		/*****************************************************************************************************************************************/
		double [] consumingStateProbabilitySimulations = new double[1];
		double [] peersDemandSimulationsLowK = {0};
		double [] peersDemandSimulationsHighK = {0, 0, 0};
		pathAux = "D variável e PI fixo";
		
		System.out.println("D variável e PI fixo");
		System.out.println("Case 1...");
		
		
		consumingStateProbabilitySimulations[0] = 0.2;
		peersDemandSimulationsLowK[0] = 3;
		peersDemandSimulationsHighK[0] = 5;
		peersDemandSimulationsHighK[1] = 9;
		peersDemandSimulationsHighK[2] = 17;
		percentageFreeRidersSimulationsLowK[0] = 0.1666666667;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 2...");
		
		consumingStateProbabilitySimulations[0] = 0.2428571429;
		peersDemandSimulationsLowK[0] = 2.558823529;
		peersDemandSimulationsHighK[0] = 4.117647059;
		peersDemandSimulationsHighK[1] = 7.235294118;
		peersDemandSimulationsHighK[2] = 13.47058824;
		percentageFreeRidersSimulationsLowK[0] = 0.1954022989;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
				
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 3...");
		
		consumingStateProbabilitySimulations[0] = 0.2857142857;
		peersDemandSimulationsLowK[0] = 2.25;
		peersDemandSimulationsHighK[0] = 3.5;
		peersDemandSimulationsHighK[1] = 6;
		peersDemandSimulationsHighK[2] = 11;
		percentageFreeRidersSimulationsLowK[0] = 0.2222222222;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 4...");
		
		consumingStateProbabilitySimulations[0] = 0.3285714286;
		peersDemandSimulationsLowK[0] = 2.02173913;
		peersDemandSimulationsHighK[0] = 3.043478261;
		peersDemandSimulationsHighK[1] = 5.086956522;
		peersDemandSimulationsHighK[2] = 9.173913043;
		percentageFreeRidersSimulationsLowK[0] = 0.247311828;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 5...");
		
		consumingStateProbabilitySimulations[0] = 0.3714285714;
		peersDemandSimulationsLowK[0] = 1.846153846;
		peersDemandSimulationsHighK[0] = 2.692307692;
		peersDemandSimulationsHighK[1] = 4.384615385;
		peersDemandSimulationsHighK[2] = 7.769230769;
		percentageFreeRidersSimulationsLowK[0] = 0.2708333333;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
				
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 6...");
		
		consumingStateProbabilitySimulations[0] = 0.4142857143;
		peersDemandSimulationsLowK[0] = 1.706896552;
		peersDemandSimulationsHighK[0] = 2.413793103;
		peersDemandSimulationsHighK[1] = 3.827586207;
		peersDemandSimulationsHighK[2] = 6.655172414;
		percentageFreeRidersSimulationsLowK[0] = 0.2929292929;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 7...");
		
		consumingStateProbabilitySimulations[0] = 0.4571428571;
		peersDemandSimulationsLowK[0] = 1.59375;
		peersDemandSimulationsHighK[0] = 2.1875;
		peersDemandSimulationsHighK[1] = 3.375;
		peersDemandSimulationsHighK[2] = 5.75;
		percentageFreeRidersSimulationsLowK[0] = 0.3137254902;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 8...");
		
		consumingStateProbabilitySimulations[0] = 0.5;
		peersDemandSimulationsLowK[0] = 1.5;
		peersDemandSimulationsHighK[0] = 2;
		peersDemandSimulationsHighK[1] = 3;
		peersDemandSimulationsHighK[2] = 5;
		percentageFreeRidersSimulationsLowK[0] = 0.3333333333;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 9...");
		
		consumingStateProbabilitySimulations[0] = 0.5428571429;
		peersDemandSimulationsLowK[0] = 1.421052632;
		peersDemandSimulationsHighK[0] = 1.842105263;
		peersDemandSimulationsHighK[1] = 2.684210526;
		peersDemandSimulationsHighK[2] = 4.368421053;
		percentageFreeRidersSimulationsLowK[0] = 0.3518518519;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 10...");
		
		consumingStateProbabilitySimulations[0] = 0.5857142857;
		peersDemandSimulationsLowK[0] = 1.353658537;
		peersDemandSimulationsHighK[0] = 1.707317073;
		peersDemandSimulationsHighK[1] = 2.414634146;
		peersDemandSimulationsHighK[2] = 3.829268293;
		percentageFreeRidersSimulationsLowK[0] = 0.3693693694;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 11...");
				
		consumingStateProbabilitySimulations[0] = 0.6285714286;
		peersDemandSimulationsLowK[0] = 1.295454545;
		peersDemandSimulationsHighK[0] = 1.590909091;
		peersDemandSimulationsHighK[1] = 2.181818182;
		peersDemandSimulationsHighK[2] = 3.363636364;
		percentageFreeRidersSimulationsLowK[0] = 0.3859649123;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 12...");
		
		consumingStateProbabilitySimulations[0] = 0.6714285714;
		peersDemandSimulationsLowK[0] = 1.244680851;
		peersDemandSimulationsHighK[0] = 1.489361702;
		peersDemandSimulationsHighK[1] = 1.978723404;
		peersDemandSimulationsHighK[2] = 2.957446809;
		percentageFreeRidersSimulationsLowK[0] = 0.4017094017;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 13...");
		
		consumingStateProbabilitySimulations[0] = 0.7142857143;
		peersDemandSimulationsLowK[0] = 1.2;
		peersDemandSimulationsHighK[0] = 1.4;
		peersDemandSimulationsHighK[1] = 1.8;
		peersDemandSimulationsHighK[2] = 2.6;
		percentageFreeRidersSimulationsLowK[0] = 0.4166666667;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
				
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 14...");
		
		consumingStateProbabilitySimulations[0] = 0.7571428571;
		peersDemandSimulationsLowK[0] = 1.160377358;
		peersDemandSimulationsHighK[0] = 1.320754717;
		peersDemandSimulationsHighK[1] = 1.641509434;
		peersDemandSimulationsHighK[2] = 2.283018868;
		percentageFreeRidersSimulationsLowK[0] = 0.4308943089;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/
		System.out.println("D variável e PI fixo");
		System.out.println("Case 15...");
		
		consumingStateProbabilitySimulations[0] = 0.8;
		peersDemandSimulationsLowK[0] = 1.125;
		peersDemandSimulationsHighK[0] = 1.25;
		peersDemandSimulationsHighK[1] = 1.5;
		peersDemandSimulationsHighK[2] = 2;
		percentageFreeRidersSimulationsLowK[0] = 0.4444444444;
		
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
				peersDemandSimulationsLowK, percentageFreeRidersSimulationsLowK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux); 
		Main.runSimulation(replicationIndex, dynamicSimulations, numStepsSimulations, numPeersSimulations, consumingStateProbabilitySimulations, 
			peersDemandSimulationsHighK, percentageFreeRidersSimulationsHighK, nofWithLog, fairnessThreshold, capacitySupplied, changingValue, pathAux);
		
		
		/*****************************************************************************************************************************************/

		

		
		long estimatedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("tempo: "+(estimatedTime));
	}
	
	private static void runSimulation(int replicationIndex, boolean [] dynamicSimulations, int [] numStepsSimulations, int [] numPeersSimulations,
			double [] consumingStateProbabilitySimulations, double [] peersDemandSimulations, double [] percentageFreeRidersSimulations, 
			boolean nofWithLog, double fairnessThreshold, double capacitySupplied, double changingValue, String pathAux){
		
		while(replicationIndex <= Main.replications){
			for(boolean dynamic : dynamicSimulations){
				for(int numPeers : numPeersSimulations){
					for(int numSteps : numStepsSimulations){
						for(double consumingStateProbability : consumingStateProbabilitySimulations){
							for(double percentageFreeRiders : percentageFreeRidersSimulations){
								for(double peersDemand : peersDemandSimulations){
										String path = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese/CCGRID 2015/experimentos/"+pathAux+"/";
										
										double k = ((consumingStateProbability*(peersDemand-1))/(1-consumingStateProbability));
										
										String file =  "k-"+(String.format("%.1f", k).replace(",", "."))+" "
												+"C-"+(dynamic==true?"dynamic":"static")+" "
												+"n-"+numPeers+" "
												+"fr-"+((int) (percentageFreeRiders*100))+" "
												+"pi-"+(String.format("%.1f", consumingStateProbability*100)).replace(",", ".")+" "
												+"D-"+(String.format("%.1f", peersDemand)).replace(",", ".")
												+ ".xlsx";
										Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, 1-percentageFreeRiders, dynamic, nofWithLog, fairnessThreshold, peersDemand, capacitySupplied, changingValue, replicationIndex, Level.SEVERE, path+replicationIndex+"/"+file);
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
