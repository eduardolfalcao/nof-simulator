package novoskappas;

public class MainFixedD {

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

		for(int i = initialKappa; i <= finalKappa; i++){
			SimThreadsFixedD t = new SimThreadsFixedD(i, fdnof, numPeers, numFreeRiders, delta, lMin, lMax, outputDir, design, fixedDemand, variablePI, kappas);
			(new Thread(t)).start();
		}
	}

}
