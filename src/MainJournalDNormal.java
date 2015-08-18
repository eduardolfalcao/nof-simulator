import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.Level;

import simulator.Simulator;
import utils.CsvReader;

public class MainJournalDNormal {

	public static void main(String[] args) {

		int numPeers = 200;
		int numSteps = 5000; // 5000
		
		boolean nofWithLog = false;
		long seed = 1;
		Level level = Level.SEVERE;

//		String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 3/journal cc elsevier/simulations/group-i/exp-vii/";
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/12-08/d-normal/";
		String kappa05 = "kappa05";
		String kappa1 = "kappa1";
		String kappa2 = "kappa2";
		String kappa4 = "kappa4";
		String design = "equal peers";
		DecimalFormat formatter = new DecimalFormat("0.0");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		int n = 15;

		CsvReader csv = new CsvReader();
		double[][] allPis = new double[n][150];
		for (int i = 16; i <= 30; i++) {
			String fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/pi-normal/pi-distribution/pi"
					+ i + ".csv";
			allPis[i-16] = csv.readDistribution(fileName);
		}
		
		double [][] dsK05 = new double[n][150];
		double [][] dsK1 = new double[n][150];
		double [][] dsK2 = new double[n][150];
		double [][] dsK4 = new double[n][150];
		
		double piD = 0.5;	//k=0.5
		
		int count = 0;
		for(double [] piSet : allPis){
			for (int i = 0; i < piSet.length; i++) {
				dsK05[count][i] = piSet[i]*0.5/piD;
				dsK1[count][i] = piSet[i]*1/piD;
				dsK2[count][i] = piSet[i]*2/piD;
				dsK4[count][i] = piSet[i]*4/piD;
			}
			count++;
		}
		
		int[] numberOfFreeRiders = new int[] { 50 };
		int[] numberOfCollaborators = new int[150];
		double[] capacitySupplied = new double[150];
		for(int i = 0; i < numberOfCollaborators.length; i++)
			capacitySupplied[i] = numberOfCollaborators[i] = 1;

		
		for (int i = 0; i<15; i++) {
			System.out.println("Normal distribution: "+(i+16));
			
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
							pi[j] = piD;
							d[j] = (dsK05[i][j])+1;							
						}
						
						


						Simulator sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir 
										+ "Dist" + (i+16)+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa05 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[0],
								design, f);
						sim.startSimulation();

						for(int j = 0; j < d.length; j++)
							d[j] = (dsK1[i][j])+1;
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir 
										+ "Dist" + (i+16) + "-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa1 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[1],
								design, f);
						sim.startSimulation();

						for(int j = 0; j < d.length; j++)
							d[j] = (dsK2[i][j])+1;
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir
										+ "Dist" + (i+16)+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa2 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[2],
								design, f);
						sim.startSimulation();

						for(int j = 0; j < d.length; j++)
							d[j] = (dsK4[i][j])+1;
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir
										+ "Dist" + (i+16)+"-"
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