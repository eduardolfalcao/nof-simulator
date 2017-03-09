import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;

public class MainKappa025e075 {

	public static void main(String[] args) {

		int numPeers = 200;
		int numFreeRiders = 50;
		int numSteps = 5000;
		double[] capacitySupplied = new double[] { 1 };
		boolean nofWithLog = false;
		long seed = 1;
		Level level = Level.SEVERE;

		double delta = 0.05, lMin = 0.75, lMax = 0.95;

		String outputDir = "/home/eduardolfalcao/Área de Trabalho/simulacoes-novos-kappas/";
		String kappa025 = "kappa025";
		String kappa05 = "kappa05";
		String kappa075 = "kappa075";
		String kappa1 = "kappa1";
		String kappa2 = "kappa2";
		String kappa4 = "kappa4";
		String design = "equal peers";
		DecimalFormat formatter = new DecimalFormat("0.0");
		formatter.setRoundingMode(RoundingMode.DOWN);

		// D FIXO e PI VARIAVEL ==> design simples do CCGRID

		double[] fixedDemand = { 2, 2.142857143, 2.285714286, 2.428571429,
				2.571428571, 2.714285714, 2.857142857, 3, 3.142857143,
				3.285714286, 3.428571429, 3.571428571, 3.714285714,
				3.857142857, 4 };
		double[] variablePIK025 = { 0.2, 0.1794871795, 0.1627906977,
				0.1489361702, 0.137254902, 0.1272727273, 0.1186440678,
				0.1111111111, 0.1044776119, 0.0985915493, 0.09333333333,
				0.08860759494, 0.0843373494, 0.08045977011, 0.07692307692 }; 
		double[] variablePIK05 = { 0.3333333333, 0.3043478261, 0.28,
				0.2592592593, 0.2413793103, 0.2258064516, 0.2121212121, 0.2,
				0.1891891892, 0.1794871795, 0.1707317073, 0.1627906977,
				0.1555555556, 0.1489361702, 0.1428571429 };
		double[] variablePIK075 = { 0.4285714286, 0.3962264151, 0.3684210526,
				0.3442622951, 0.3230769231, 0.3043478261, 0.2876712329,
				0.2727272727, 0.2592592593, 0.2470588235, 0.2359550562,
				0.2258064516, 0.2164948454, 0.2079207921, 0.2 };
		double[] variablePIK1 = { 0.5, 0.4666666667, 0.4375, 0.4117647059,
				0.3888888889, 0.3684210526, 0.35, 0.3333333333, 0.3181818182,
				0.3043478261, 0.2916666667, 0.28, 0.2692307692, 0.2592592593,
				0.25 };
		double[] variablePIK2 = { 0.6666666667, 0.6363636364, 0.6086956522,
				0.5833333333, 0.56, 0.5384615385, 0.5185185185, 0.5,
				0.4827586207, 0.4666666667, 0.4516129032, 0.4375, 0.4242424242,
				0.4117647059, 0.4 };
		double[] variablePIK4 = { 0.8, 0.7777777778, 0.7567567568,
				0.7368421053, 0.7179487179, 0.7, 0.6829268293, 0.6666666667,
				0.6511627907, 0.6363636364, 0.6222222222, 0.6086956522,
				0.5957446809, 0.5833333333, 0.5714285714 };

		double[][] variablePI = new double[][] { variablePIK025, variablePIK05,
				variablePIK075, variablePIK1, variablePIK2, variablePIK4 };
		double[] kappas = new double[] { 0.25, 0.5, 0.75, 1, 2, 4 };

		for (boolean dynamic : new boolean[] { false, true }) {
			int cont = 0;
			for (double[] piKappa : variablePI) {
				String f = "" + kappas[cont];				
				for (int i = 0; i < fixedDemand.length; i++) {
					double[] d = new double[1];
					d[0] = fixedDemand[i];
					double[] pi = new double[1];
					pi[0] = piKappa[i];

					String outputFile = outputDir
							+ (dynamic ? "fdnof-" : "sdnof-")
							+ design
							+ "D"
							+ (formatter.format(fixedDemand[i])).replace(",",
									".") + "kappa" + kappas[cont] + "FR" + numFreeRiders
							+ "lMin" + lMin + "lMax" + lMax + "delta" + delta;
					
					Simulator sim = new Simulator(numPeers, numSteps, pi,
							new int[] { numPeers - numFreeRiders },
							new int[] { numFreeRiders }, dynamic, nofWithLog,
							lMin, lMax, d, capacitySupplied, delta, seed,
							level, outputFile, dynamic, kappas[cont], design, f);
					System.out.println("Running: "+outputFile);
					sim.startSimulation();
				}
				cont++;
			}
		}

		// PI FIXO e D VARIAVEL
		double[] fixedPI = { 0.2, 0.2428571429, 0.2857142857, 0.3285714286,
				0.3714285714, 0.4142857143, 0.4571428571, 0.5, 0.5428571429,
				0.5857142857, 0.6285714286, 0.6714285714, 0.7142857143,
				0.7571428571, 0.8 };
		double[] variableDK025 = { 2, 1.779411765, 1.625, 1.510869565,
				1.423076923, 1.353448276, 1.296875, 1.25, 1.210526316,
				1.176829268, 1.147727273, 1.122340426, 1.1, 1.080188679, 1.0625 };
		double[] variableDK05 = { 3, 2.558823529, 2.25, 2.02173913,
				1.846153846, 1.706896552, 1.59375, 1.5, 1.421052632,
				1.353658537, 1.295454545, 1.244680851, 1.2, 1.160377358, 1.125 };
		double[] variableDK075 = { 4, 3.338235294, 2.875, 2.532608696,
				2.269230769, 2.060344828, 1.890625, 1.75, 1.631578947,
				1.530487805, 1.443181818, 1.367021277, 1.3, 1.240566038, 1.1875 };
		double[] variableDK1 = { 5, 4.117647059, 3.5, 3.043478261, 2.692307692,
				2.413793103, 2.1875, 2, 1.842105263, 1.707317073, 1.590909091,
				1.489361702, 1.4, 1.320754717, 1.25 };
		double[] variableDK2 = { 9, 7.235294118, 6, 5.086956522, 4.384615385,
				3.827586207, 3.375, 3, 2.684210526, 2.414634146, 2.181818182,
				1.978723404, 1.8, 1.641509434, 1.5 };
		double[] variableDK4 = { 17, 13.47058824, 11, 9.173913043, 7.769230769,
				6.655172414, 5.75, 5, 4.368421053, 3.829268293, 3.363636364,
				2.957446809, 2.6, 2.283018868, 2 };
	}

}
