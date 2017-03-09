package novoskappas;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;

import simulator.Simulator;

public class MainFixedPI {

	public static void main(String[] args) {

		boolean fdnof = false;
		int numPeers = 0, numFreeRiders = 0;
		double delta = 0, lMin = 0, lMax = 0;
		String outputDir = "", design = "";
		
		int initialKappa = 0, finalKappa = 0;

		if (args == null) {
			System.out
					.println("Args are missing!\n Run with \"help\" args to understand the parameters!");
			System.exit(0);
		} else {
			if (args.length != 10) {
				System.out.println("Number of args are incorrect!");
				System.out.println("#########################################");
				System.out.println("################# Help ##################");
				System.out.println("#########################################");
				System.out.println("# args[0]: boolean indicating if nof is Fairness Driven");
				System.out.println("# args[1]: number of peers");
				System.out.println("# args[2]: number of free riders");
				System.out.println("# args[3]: delta");
				System.out.println("# args[4]: minimum threshold");
				System.out.println("# args[5]: max threshold");
				System.out.println("# args[6]: output path");
				System.out.println("# args[7]: design id");
				System.out.println("# args[8]: index of initial kappa");
				System.out.println("# args[9]: index of final kappa");
				System.out.println("#########################################");
				System.out.println("# example: true 200 50 0.05 0.75 0.95 /home/eduardolfalcao/Desktop/ homogeneous");
				System.exit(0);
			} else {
				fdnof = Boolean.parseBoolean(args[0]); // true or false
				numPeers = Integer.parseInt(args[1]); // 200
				numFreeRiders = Integer.parseInt(args[2]); // 50 or 150
				delta = Double.parseDouble(args[3]); // 0.01, 0.05, or 0.1
				lMin = Double.parseDouble(args[4]); // 0.75, 0.85
				lMax = Double.parseDouble(args[5]); // 0.9, 1
				outputDir = args[6]; // "/home/eduardolfalcao/Área de Trabalho/simulacoes-novos-kappas/"
				design = args[7]; // homogeneous
				initialKappa = Integer.parseInt(args[8]); // 0
				finalKappa = Integer.parseInt(args[9]); // 3
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

		double[][] variableD = new double[][] { variableDK025, variableDK05,
				variableDK075, variableDK1, variableDK2, variableDK4 };
		double[] kappas = new double[] { 0.25, 0.5, 0.75, 1, 2, 4 };

		for(int i = initialKappa; i <= finalKappa; i++){
			SimThreadsFixedPI t = new SimThreadsFixedPI(i, fdnof, numPeers, numFreeRiders, delta, lMin, lMax, outputDir, design, fixedPI, variableD, kappas);
			(new Thread(t)).start();
		}
		
	}

}
