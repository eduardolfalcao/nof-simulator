package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class CsvReader {
	
	public double [] readDistribution(String fileName){
		//Build reader instance
		  //Read data.csv
		  //Default seperator is comma
		  //Default quote character is double quote
		  //Start reading from line number 2 (line numbers start from zero)
		
		double[] pis = new double[150];
		
	    CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(fileName), 
					  ' ' , '"' , 0);
			
			//Read CSV line by line and use the string array as you want
		      String[] nextLine = reader.readNext();
		      for(int i = 0; i< pis.length; i++){
		    	  pis[i] = Double.parseDouble(nextLine[i]);
		      }		      
		      
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pis;
	      
	      
	}
	

}
