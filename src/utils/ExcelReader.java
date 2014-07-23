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
    public static void main(String[] args) 
    {
    	ExcelReader.mountFairnessGraph();
    	
    }
    
    public static void mountFairnessGraph(){
    	
    	String fileName = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation/INDEX/Dynamic  (DYNAMIC) - 100 peers - 2000 steps - FREERIDERS.0% freeriders - CONSUMING.0% consuming probability - DEMAND.0 peers demand - 5.0% change value - NoF by SquareRoot.xlsx";
    	
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
    
    
    public static void mountTables(){
    	
    	String fileName = "/home/eduardolfalcao/Área de Trabalho/grive/Doutorado - UFCG/LSD/NoF Simulation/INDEX/Dynamic  (DYNAMIC) - 100 peers - 2000 steps - FREERIDERS.0% freeriders - CONSUMING.0% consuming probability - DEMAND.0 peers demand - 5.0% change value - NoF by SquareRoot.xlsx";
        
    	//fairness, satisfaction, free riders success
//    	int [] sheet = {1,4,7};
    	int [] sheet = {2, 5};
    	
    	boolean [] dynamic = {false, true};
    	int [] consumingStateProbability = {20, 50};
    	int [] percentageCollaborators = {20, 75};
    	int [] peersDemand = {2, 5};
    	
    	for(int localSheet : sheet){
    		if(localSheet == 1)
    			System.out.println("## FAIRNESS ##\n\n");
    		if(localSheet == 2)
    			System.out.println("## CONSUMED AMOUNT ##\n\n");
    		else if(localSheet == 4)
    			System.out.println("## SATISFACTION ##\n\n");
    		else if(localSheet == 5)
    			System.out.println("## DONATED AMOUNT ##\n\n");
    		else if(localSheet == 7)
    			System.out.println("## Free Riders Success ##\n\n");
    		
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
    						ExcelReader.readLastStep(fileName, localSheet);
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
    
    
    public static void readLastStep(String fileName, int sheetIndex){
    	
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
	            Cell c = lastRow.getCell(lastRow.getLastCellNum()-1);

	            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	            CellReference cellReference = new CellReference(c); 
	            Row row = sheet.getRow(cellReference.getRow());
	            Cell cell = row.getCell(cellReference.getCol()); 

	            CellValue cellValue = evaluator.evaluate(cell);
	            System.out.print(cellValue.getNumberValue()+"	");
		        
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
    
    
}