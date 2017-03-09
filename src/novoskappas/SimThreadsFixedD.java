package novoskappas;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;

public class SimThreadsFixedD implements Runnable{
	
	final long seed = 1;
	final Level level = Level.SEVERE;

	final int numSteps = 5000;
	final double[] capacitySupplied = new double[] { 1 };
	final boolean nofWithLog = false;

	private boolean fdnof;
	private int numPeers, numFreeRiders;
	private double delta, lMin, lMax;
	private String outputDir, design;
	
	private double[] fixedD;
	private double[][] variablePi;
	private double[] kappas;
	
	private int kappaIndex;
	
	private DecimalFormat formatter;
	
	public SimThreadsFixedD(int kappaIndex, boolean fdnof, int numPeers, int numFreeRiders,
			double delta, double lMin, double lMax, String outputDir,
			String design, double[] fixedD, double[][] variablePi, double[] kappas) {
		super();
		this.kappaIndex = kappaIndex;
		this.fdnof = fdnof;
		this.numPeers = numPeers;
		this.numFreeRiders = numFreeRiders;
		this.delta = delta;
		this.lMin = lMin;
		this.lMax = lMax;
		this.outputDir = outputDir;
		this.design = design;
		this.fixedD = fixedD;
		this.variablePi = variablePi;
		this.kappas = kappas;
		formatter = new DecimalFormat("0.00");
		formatter.setRoundingMode(RoundingMode.DOWN);
	}
	
	@Override
	public void run() {
		String f = "" +(double) Math.round(((double)numFreeRiders/(double)numPeers) * 100) / 100;		
		double[] piKappa = variablePi[kappaIndex];
		System.out.println("kappa: "+ kappas[kappaIndex]);
		
		for (int i = 0; i < fixedD.length; i++) {
			System.out.println("d: "+ fixedD[i]+", and pi: "+piKappa[i]);
		
			double[] d = new double[1];
			d[0] = fixedD[i];
			double[] pi = new double[1];
			pi[0] = piKappa[i];

			String outputFile = outputDir + (fdnof ? "fdnof" : "sdnof") + "-" 
				+ design + "-" 
				+ "D" + (formatter.format(fixedD[i])).replace(",", ".") + "-" 
				+ "PI" + (formatter.format(piKappa[i])).replace(",", ".") + "-"				
				+ "kappa" + kappas[kappaIndex] + "-" 
				+ "FR" + f + "-" 
				+ "lMin" + lMin + "-" 
				+ "lMax" + lMax + "-" 
				+ "delta" + delta;
			
			Simulator sim = new Simulator(numPeers, numSteps, pi,
					new int[] { numPeers - numFreeRiders },
					new int[] { numFreeRiders }, fdnof, nofWithLog, lMin,
					lMax, d, capacitySupplied, delta, seed, level,
					outputFile, fdnof, kappas[kappaIndex], design, f);
			System.out.println("Running: " + outputFile);
			sim.startSimulation();
		}
		
	}

}
