import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;

public class MainValidationTese {

	public static void main(String[] args) {

		Level level = Level.SEVERE;		
		int numSteps = 2880; // 86400/30
		double[] capacitySupplied = new double[]{20};
		boolean nofWithLog = true;
		double fairnessLowerThreshold = 0.75, tauMax = 0.95, changingValue = 0.05;
		long seedRep = 6;
		
		String kappa05 = "kappa05";
		String design = "comcaronas";
		String outputDir = "/home/eduardolfalcao/git/fogbow-manager/experiments/data scripts r/done/40peers-20capacity/fixingSatisfaction/cycle10/resultados/simulator/";
		
		DecimalFormat formatter = new DecimalFormat("0.0");
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		int numPeers = 50;
		double pi[] = new double[]{0.515};
		int [] numberOfCollaborators = new int[]{40}, numberOfFreeRiders = new int[]{10};		
		double [] d = new double[]{14.769+20};
		String f = "comcaronas-rep"+seedRep;
		double kappa = 0.5;
		boolean dynamic = true, pairwise = dynamic;
		
		Simulator sim = new Simulator(numPeers,numSteps,pi,numberOfCollaborators,
				numberOfFreeRiders, dynamic, nofWithLog, fairnessLowerThreshold,
				tauMax,	d, capacitySupplied, changingValue, seedRep, level,
				outputDir + (dynamic ? "fdnof-": "sdnof-")
						  + design + "PI" + (formatter.format(pi[0])).replace(",", ".")
						  + kappa05 + "FR" + f + "lMin" + fairnessLowerThreshold
						  + "delta" + changingValue,
				pairwise, kappa, design, f);
		sim.startSimulation();		
	}
}