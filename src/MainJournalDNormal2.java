import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.Level;

import simulator.Simulator;
import utils.CsvReader;

public class MainJournalDNormal2 {

	public static void main(String[] args) {

		int numPeers = 200;
		int numSteps = 5000; // 5000
		
		boolean nofWithLog = false;
		long seed = 1;
		Level level = Level.SEVERE;

//		String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 3/journal cc elsevier/simulations/group-i/exp-vii/";
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/d-normal/";
		String kappa05 = "kappa05";
		String kappa1 = "kappa1";
		String kappa2 = "kappa2";
		String kappa4 = "kappa4";
		String design = "equal peers";
		DecimalFormat formatter = new DecimalFormat("0.0");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		
		CsvReader csv = new CsvReader();
		double[][] dK05 = new double[30][150];
		double[][] dK1 = new double[30][150];
		double[][] dK2 = new double[30][150];
		double[][] dK4 = new double[30][150];
		for (int i = 0; i < 30; i++) {
			String fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/d-normal/d-distribution/kappa05/d"
					+ i + ".csv";
			dK05[i] = csv.readDistribution(fileName);
			fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/d-normal/d-distribution/kappa1/d"
					+ i + ".csv";
			dK1[i] = csv.readDistribution(fileName);
			fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/d-normal/d-distribution/kappa2/d"
					+ i + ".csv";
			dK2[i] = csv.readDistribution(fileName);
			fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/d-normal/d-distribution/kappa4/d"
					+ i + ".csv";
			dK4[i] = csv.readDistribution(fileName);
		}
		
		
		
		int[] numberOfFreeRiders = new int[] { 50 };
		int[] numberOfCollaborators = new int[150];
		double[] capacitySupplied = new double[150];
		for(int i = 0; i < numberOfCollaborators.length; i++)
			capacitySupplied[i] = numberOfCollaborators[i] = 1;

		
		int count = 0;
		for (int i = 28; i<=29; i++) {
			
			count = i + 1;
			
			System.out.println("Normal distribution: "+(count));
			
			for (double changingValue : new double[] { 0.05 }) { // 0.05
				System.out.println("ChangingValue: " + changingValue);

				for (boolean dynamic : new boolean[] { false, true }) { 
					System.out.println("NoF: "
							+ (dynamic ? "FD-NoF" : "SD-NoF"));
					boolean pairwise = dynamic;
					
					for (double fairnessLowerThreshold : new double[] { 0.75 }) { 
						
						for (double maxTau : new double[] { 1 }) {
						
						System.out.println("TAU: " + fairnessLowerThreshold);

						 System.out.println("FR: "+((double)numberOfFreeRiders[0])/numberOfCollaborators[0]);
						
						String f = "25";

						double[] kappa = new double[]{0.5, 1, 2, 4};
						double[] d = new double[150];
						double[] pi = new double[150];
						for(int j = 0; j < d.length; j++){
							d[j] = (dK05[i][j])+1;	
							pi[j] = 0.5;													
						}
						
						


						Simulator sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir 
										+ "Dist" + (count)+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa05 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[0],
								design, f);
						sim.startSimulation();

						for(int j = 0; j < d.length; j++){
							d[j] = (dK1[i][j])+1;	
							pi[j] = 0.5;													
						}
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir 
										+ "Dist" + (count) + "-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa1 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[1],
								design, f);
						sim.startSimulation();

						for(int j = 0; j < d.length; j++){
							d[j] = (dK2[i][j])+1;	
							pi[j] = 0.5;													
						}
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir
										+ "Dist" + (count)+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa2 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[2],
								design, f);
						sim.startSimulation();

						for(int j = 0; j < d.length; j++){
							d[j] = (dK4[i][j])+1;	
							pi[j] = 0.5;													
						}
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir
										+ "Dist" + (count)+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa4 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[3],
								design, f);
						sim.startSimulation();

					}

				}
					}
			}
		}

		

	}
}