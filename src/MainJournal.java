import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;
import utils.CsvReader;

public class MainJournal {

	public static void main(String[] args) {

		int numPeers = 150;
		int numSteps = 5000; // 5000
		
		boolean nofWithLog = false;
		long seed = 1;
		Level level = Level.SEVERE;

//		String outputDir = "/home/eduardolfalcao/Área de Trabalho/Dropbox/Doutorado/Disciplinas/Projeto de Tese 3/journal cc elsevier/simulations/group-i/exp-vii/";
		String outputDir = "/home/eduardolfalcao/Área de Trabalho/experimentos/12-08/pi-normal-0f/";
		String kappa05 = "kappa05";
		String kappa1 = "kappa1";
		String kappa2 = "kappa2";
		String kappa4 = "kappa4";
		String design = "equal peers";
		DecimalFormat formatter = new DecimalFormat("0.0");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		

		CsvReader csv = new CsvReader();
		double[][] allPis = new double[10][150];
		for (int i = 1; i <= 10; i++) {
			String fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/pi-normal/pi-distribution/pi"
					+ i + ".csv";
			allPis[i-1] = csv.readDistribution(fileName);
		}
		
		int[] numberOfFreeRiders = new int[] { 0 };
		int[] numberOfCollaborators = new int[150];
		double[] capacitySupplied = new double[150];
		for(int i = 0; i < numberOfCollaborators.length; i++)
			capacitySupplied[i] = numberOfCollaborators[i] = 1;

		int distribution = 0;
		for (double[] pi : allPis) {
			distribution++;
			System.out.println("Normal distribution: "+distribution);
			
			for (double changingValue : new double[] { 0.05 }) { // 0.05
				System.out.println("ChangingValue: " + changingValue);

				for (boolean dynamic : new boolean[] { false, true }) { 
					System.out.println("NoF: "
							+ (dynamic ? "FD-NoF" : "SD-NoF"));
					boolean pairwise = dynamic;
					
					for (double fairnessLowerThreshold : new double[] { 0.75 }) { 
						
						for (double maxTau : new double[] { 0.95 }) {
						
						System.out.println("TAU: " + fairnessLowerThreshold);

						 System.out.println("FR: "+((double)numberOfFreeRiders[0])/numberOfCollaborators.length);
						
						String f = "0";

						// double[] d = new double[]{1.5, 2, 3, 5};
						double[] d = new double[150];
						for(int i = 0; i < d.length; i++)
							d[i] = 1.5;
						double[] kappa = { 0.5, 1, 2, 4 };


						Simulator sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir 
										+ "Dist" + distribution+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa05 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[0],
								design, f);
						sim.startSimulation();
//						System.exit(0);

						for(int i = 0; i < d.length; i++)
							d[i] = 2;
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir 
										+ "Dist" + distribution + "-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa1 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[1],
								design, f);
						sim.startSimulation();

						for(int i = 0; i < d.length; i++)
							d[i] = 3;
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir
										+ "Dist" + distribution+"-"
										+ (dynamic ? "fdnof-" : "sdnof-")
										+ kappa2 + "tau"
										+ fairnessLowerThreshold + "delta"
										+ changingValue, pairwise, kappa[2],
								design, f);
						sim.startSimulation();

						for(int i = 0; i < d.length; i++)
							d[i] = 5;
						sim = new Simulator(numPeers, numSteps, pi,
								numberOfCollaborators, numberOfFreeRiders,
								dynamic, nofWithLog, fairnessLowerThreshold, maxTau, d,
								capacitySupplied, changingValue, seed, level,
								outputDir
										+ "Dist" + distribution+"-"
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