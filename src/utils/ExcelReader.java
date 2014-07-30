package utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader{
	
	static double[][] fairness = new double [16][30];
	static 	double[][] collaboratorsSatisfacion = new double [16][30];
	static double[][] freeRidersSatisfacion = new double [16][30];
	static double[][] consumed = new double [16][30];
	static double[][] donated = new double [16][30];
	
    public static void main(String[] args) 
    {
    	ExcelReader.mountTables();
    	
    	
    }
    
    
    
    
    public static void mountTables(){
    	
    	String fileName = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation Debug/INDEX/Dynamic  (DYNAMIC) - 100 peers - 4000 steps - FREERIDERS.0% freeriders - CONSUMING.0% consuming probability - DEMAND.0 peers demand - 5.0% change value - NoF by SquareRoot.xlsx";
        
    	
    	boolean [] dynamic = {false, true};
    	int [] consumingStateProbability = {20, 50};
    	int [] percentageCollaborators = {20, 75};
    	int [] peersDemand = {2, 5};
    	
    	int linha = 0;
    	
    	for(boolean localDynamic : dynamic){
    		String aux4 = fileName;
    		fileName = fileName.replaceAll("DYNAMIC", ""+localDynamic);
    		for(int localConsumingStateProbability : consumingStateProbability){
    			String aux3 = fileName;
    			fileName = fileName.replaceAll("CONSUMING", ""+localConsumingStateProbability);
    			for(int localPercentageCollaborators : percentageCollaborators){
    				String aux2 = fileName;
    				fileName = fileName.replaceAll("FREERIDERS", ""+(100-localPercentageCollaborators));
    				for(int localPeersDemand : peersDemand){
    					String aux1 = fileName;
    					fileName = fileName.replaceAll("DEMAND", ""+localPeersDemand);
    						
    					int replications = 30;
    			    	int index = 1;    				    	
    			    	String fileNameLocal = fileName;
    				    	
    			    	while(index<=replications){
    			    		fileNameLocal = fileNameLocal.replaceAll("INDEX", ""+index);
    			    		double [] mainValues = ExcelReader.readValues(fileNameLocal);
    			    		ExcelReader.fulfillArrays(mainValues, linha, index-1);
    			    		index++;
    			    		fileNameLocal = fileName;
    			    	}
    					fileName = aux1;
    					linha++;
    					System.out.println("Linha "+(linha-1)+" pronta!");
    				}
    				fileName = aux2;
    			}
    			fileName = aux3;
    		}
    		fileName = aux4;
    	}
    	
    	ExcelReader.printValues();
    }
    
    
    public static double[] readValues(String fileName){
    	
    	double [] mainValues = new double [5];
    	
    	int [] sheetIndexArray = {1,4,7,2,5};
	    
    	try{
	            FileInputStream file = new FileInputStream(new File(fileName));
	 
	            //Create Workbook instance holding reference to .xlsx file
	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	            
	            for(int sheetIndex = 0; sheetIndex < sheetIndexArray.length; sheetIndex++){
	            	//Get first/desired sheet from the workbook
		            XSSFSheet sheet = workbook.getSheetAt(sheetIndexArray[sheetIndex]);
		            
		            Row lastRow = sheet.getRow(sheet.getLastRowNum());
		            Cell c = lastRow.getCell(lastRow.getLastCellNum()-1);

		            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		            CellReference cellReference = new CellReference(c); 
		            Row row = sheet.getRow(cellReference.getRow());
		            Cell cell = row.getCell(cellReference.getCol()); 

		            CellValue cellValue = evaluator.evaluate(cell);
		            
		            mainValues[sheetIndex] = cellValue.getNumberValue();
	            }		        
	            file.close();
	    } 
	    catch (Exception e){
	    	e.printStackTrace();
	    }
    	
    	return mainValues;
    }
    
    
    public static void fulfillArrays(double [] mainValues, int linha, int coluna){
    	
    	ExcelReader.fairness[linha][coluna] = mainValues[0];
    	ExcelReader.collaboratorsSatisfacion[linha][coluna] = mainValues[1];  	
    	ExcelReader.freeRidersSatisfacion[linha][coluna] = mainValues[2];  	
    	ExcelReader.consumed[linha][coluna] = mainValues[3];  	
    	ExcelReader.donated[linha][coluna] = mainValues[4];  	
    	
    }
    
    public static void printValues(){
    	
    	System.out.println("# FAIRNESS #");
    	for(int i = 0; i < fairness.length; i++){
    		for(int j = 0; j < fairness[0].length; j++)
    			System.out.print(String.format("%.10f", fairness[i][j])+"	");
    		System.out.println();
    	}
    	
    	System.out.println("# COLLABORATORS SATISFACTION #");
    	for(int i = 0; i < collaboratorsSatisfacion.length; i++){
    		for(int j = 0; j < collaboratorsSatisfacion[0].length; j++)
    			System.out.print(String.format("%.10f", collaboratorsSatisfacion[i][j])+"	");
    		System.out.println();
    	}
    	
    	System.out.println("# FREE RIDERS SATISFACTION #");
    	for(int i = 0; i < freeRidersSatisfacion.length; i++){
    		for(int j = 0; j < freeRidersSatisfacion[0].length; j++)
    			System.out.print(String.format("%.10f", freeRidersSatisfacion[i][j])+"	");
    		System.out.println();
    	}
    	
    	System.out.println("# CONSUMED #");
    	for(int i = 0; i < consumed.length; i++){
    		for(int j = 0; j < consumed[0].length; j++)
    			System.out.print(String.format("%.10f", consumed[i][j])+"	");
    		System.out.println();
    	}
    	
    	System.out.println("# DONATED #");
    	for(int i = 0; i < donated.length; i++){
    		for(int j = 0; j < donated[0].length; j++)
    			System.out.print(String.format("%.10f", donated[i][j])+"	");
    		System.out.println();
    	}
    	
    }
    
public static void readAllStep(String fileName, int sheetIndex){
    	
    	int replications = 30;
    	int index = 1;
    	
    	String fileNameLocal = fileName;
    	
    	while(index<=replications){
    		fileNameLocal = fileName.replaceAll("INDEX", ""+index);
	        try
	        {
	            FileInputStream file = new FileInputStream(new File(fileNameLocal));
	 
	            //Create Workbook instance holding reference to .xlsx file
	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
	            
	            Row lastRow = sheet.getRow(sheet.getLastRowNum());
	            
	            for(int i = 2; i<=2001; i++){
	            	Cell c = lastRow.getCell(i);
	            	FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		            CellReference cellReference = new CellReference(c); 
		            Row row = sheet.getRow(cellReference.getRow());
		            Cell cell = row.getCell(cellReference.getCol()); 

		            CellValue cellValue = evaluator.evaluate(cell);
		            System.out.print(cellValue.getNumberValue()+"	");
	            }
	            
	            System.out.println();
		        
	            file.close();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
        index++;
       }
    	
    	System.out.println();
    	
    }
    

public static void mountFairnessGraph(){
	
	String fileName = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation Debug/INDEX/Dynamic  (DYNAMIC) - 100 peers - 2000 steps - FREERIDERS.0% freeriders - CONSUMING.0% consuming probability - DEMAND.0 peers demand - 5.0% change value - NoF by SquareRoot.xlsx";
	
	int [] sheet = {1};
	boolean [] dynamic = {false, true};
	int [] consumingStateProbability = {20};
	int [] percentageCollaborators = {20};
	int [] peersDemand = {2};
	
	for(int localSheet : sheet){
		if(localSheet == 1)
			System.out.println("## FAIRNESS ##\n\n");
		
		for(boolean localDynamic : dynamic){
			System.out.println("## Dynamic ="+localDynamic+" ##\n\n");
			String aux4 = fileName;
			fileName = fileName.replaceAll("DYNAMIC", ""+localDynamic);
			for(int localConsumingStateProbability : consumingStateProbability){
				String aux3 = fileName;
				fileName = fileName.replaceAll("CONSUMING", ""+localConsumingStateProbability);
				for(int localPercentageCollaborators : percentageCollaborators){
					String aux2 = fileName;
					fileName = fileName.replaceAll("FREERIDERS", ""+(100-localPercentageCollaborators));
					for(int localPeersDemand : peersDemand){
						String aux1 = fileName;
						fileName = fileName.replaceAll("DEMAND", ""+localPeersDemand);
						ExcelReader.readAllStep(fileName, localSheet);
						fileName = aux1;
					}
					fileName = aux2;
				}
				fileName = aux3;
			}
			fileName = aux4;
		}
		
		System.out.println("==================================================================\n\n");
	}
	
}
    
}