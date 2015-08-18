import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.Level;

import simulator.Simulator;
import utils.CsvReader;

public class TestDistribution {

	public static void main(String[] args) {
	
		

		CsvReader csv = new CsvReader();
		double[][] allPis = new double[30][150];
		for (int i = 1; i <= 30; i++) {
			String fileName = "/home/eduardolfalcao/Área de Trabalho/experimentos/two-thresholds/pi-normal/pi-distribution/pi"
					+ i + ".csv";
			allPis[i-1] = csv.readDistribution(fileName);
		}
		
		int i = 1;
		for(double kappa : new double [] {0.5, 1, 2, 4}){
			System.out.println("Kappa: "+kappa);
			for(double [] piSet : allPis){
				System.out.println("Distribution: "+i);			
				for(double pi : piSet){
					double d = (kappa-(kappa*pi))/(pi);
					if(d<0.1)
						System.out.print("PI["+pi+"]/D["+d+"], ");
//					System.out.print("PI["+pi+"]/D["+d+"], ");					
				}
				System.out.println();
				i++;
			}
			
			
		}
	
	}
}