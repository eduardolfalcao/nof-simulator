package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.Simulator;

public class WriteExcel2010 {
	
	private String outputFile;
	private XSSFSheet satisfactionSheet, satisfactionPerStepSheet, consumedSheet, donatedSheet, capacitySuppliedPerStepSheet, freeRiderSuccessSheet;
	private int numSteps;
	
	private XSSFWorkbook workbook;
	private XSSFCellStyle timesBoldUnderline, times;
	private XSSFFont times10ptBoldUnderline, times10pt;
	
	
	/**
	 * @param outputFile the url address where the file will be stored
	 * @param numSteps the number of steps of the simulation
	 */
	public WriteExcel2010(String outputFile, int numSteps) {
		super();
		this.outputFile = outputFile;
		this.numSteps = numSteps;
		
		this.workbook = new XSSFWorkbook();
		
		this.timesBoldUnderline = this.workbook.createCellStyle();
		this.times10ptBoldUnderline = this.workbook.createFont();
		this.times10ptBoldUnderline.setFontName(HSSFFont.FONT_ARIAL);
		this.times10ptBoldUnderline.setFontHeightInPoints((short) 12);
		this.times10ptBoldUnderline.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		this.timesBoldUnderline.setFont(times10ptBoldUnderline);
		
		this.times = this.workbook.createCellStyle();
		this.times10pt = this.workbook.createFont();
		this.times10pt.setFontName(HSSFFont.FONT_ARIAL);
		this.times10pt.setFontHeightInPoints((short) 10);
		this.times.setFont(times10pt);
	}
	
	/**
	 * Setup the xslx file for output.
	 */
	public void setupFile() {		 
		
		satisfactionSheet = workbook.createSheet("Satisfaction");
		satisfactionPerStepSheet = workbook.createSheet("Satisfaction per steps");
		consumedSheet = workbook.createSheet("Consumed");
		donatedSheet = workbook.createSheet("Donated");
		capacitySuppliedPerStepSheet = workbook.createSheet("Capacity supplied per steps");
		freeRiderSuccessSheet = workbook.createSheet("Free riders success per steps");

		createLabel(satisfactionSheet);
		createLabel(satisfactionPerStepSheet);
		createLabel(consumedSheet);
		createLabel(donatedSheet);
		createLabel(capacitySuppliedPerStepSheet); 
		createLabel(freeRiderSuccessSheet);
	}
	
	/**
	 * Creates the label of the tabs.
	 * 
	 * @param sheet the tab to be created the titles
	 */
	private void createLabel(XSSFSheet sheet){
		
		addLabel(sheet, 0, 0, "Peers");
		addLabel(sheet, 1, 0, "ID");
		
		if (sheet.getSheetName().equals("Satisfaction")) 
			addLabel(sheet, 2, 0, "Satisfaction");
		else if (sheet.getSheetName().equals("Consumed")
				|| sheet.getSheetName().equals("Donated")
				|| sheet.getSheetName().equals("Satisfaction per steps")
				|| sheet.getSheetName().equals("Capacity supplied per steps")
				|| sheet.getSheetName().equals("Free riders success per steps")) {
			for (int i = 0; i < this.numSteps; i++)
				addLabel(sheet, i + 2, 0, "Step " + (i + 1));
		}
	}
	
	/**
	 * Write satisfaction summary data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillSatisfactions(Peer [] peers){
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator)?"Collaborator":"Free Rider"; 
			this.addText(this.satisfactionSheet, 0, i+1, peer);
			this.addNumber(this.satisfactionSheet, 1, i+1, peers[i].getPeerId());
			double currentDonated = 0, currentConsumed = 0;
			if(peers[i] instanceof Collaborator){	
				currentDonated = peers[i].getCurrentDonated(this.numSteps-1);						
				currentConsumed = peers[i].getCurrentConsumed(this.numSteps-1);
			}
			else{
				currentDonated = 0;
				currentConsumed = peers[i].getCurrentConsumed(this.numSteps-1);
			}
			double satisfaction = Simulator.getSatisfaction(currentDonated, currentConsumed);
			if(satisfaction==Double.POSITIVE_INFINITY)
				this.addText(this.satisfactionSheet, 2, i+1, "Infinity");
			else
				this.addNumber(this.satisfactionSheet, 2, i+1, satisfaction);
		}
	}
	
	/**
	 * Write satisfactions data per step.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillSatisfactionsPerSteps(Peer [] peers){
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator)?"Collaborator":"Free Rider"; 
			this.addText(this.satisfactionPerStepSheet, 0, i+1, peer);
			this.addNumber(this.satisfactionPerStepSheet, 1, i+1, peers[i].getPeerId());
			double currentDonated = 0, currentConsumed = 0;
			
			for(int j = 0; j < this.numSteps; j++){
				if(peers[i] instanceof Collaborator){
					currentDonated = peers[i].getCurrentDonated(j);						
					currentConsumed = peers[i].getCurrentConsumed(j);
				}
				else{
					currentDonated = 0;									//always ZERO
					currentConsumed = peers[i].getCurrentConsumed(j);
				}
				double satisfaction = Simulator.getSatisfaction(currentDonated, currentConsumed);
				if(satisfaction==Double.POSITIVE_INFINITY)
					this.addText(this.satisfactionPerStepSheet, j+2, i+1, "Infinity");
				else
					this.addNumber(this.satisfactionPerStepSheet, j+2, i+1, satisfaction);
			}
		}
	}
	
	
	
	/**
	 * Write consumption data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillConsumptionData(Peer [] peers){
		
		//fulfilling consumed peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator) ? "Collaborator": "Free Rider";
			this.addText(this.consumedSheet, 0, i + 1, peer);
			this.addNumber(this.consumedSheet, 1, i + 1, peers[i].getPeerId());
			
			for(int j = 0; j < this.numSteps; j++)
				this.addNumber(this.consumedSheet, j+2, i + 1, peers[i].getConsumedHistory()[j]);
		}
	}
	
	/**
	 * Write donation data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillDonationData(Peer [] peers){
		// fulfilling donated peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator) ? "Collaborator": "Free Rider";
			this.addText(this.donatedSheet, 0, i + 1, peer);
			this.addNumber(this.donatedSheet, 1, i + 1, peers[i].getPeerId());

			if(peers[i] instanceof Collaborator){
				for (int j = 0; j < this.numSteps; j++)
					this.addNumber(this.donatedSheet, j + 2, i + 1, peers[i].getDonatedHistory()[j]);
			}
			else{
				for (int j = 0; j < this.numSteps; j++)
					this.addNumber(this.donatedSheet, j + 2, i + 1, 0.0);
			}
		}	
	}
	
	/**
	 * Write capacity supplied data of peers per step.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillCapacitySuppliedData(Peer [] peers){
		// fulfilling donated peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator) ? "Collaborator": "Free Rider";
			this.addText(this.capacitySuppliedPerStepSheet, 0, i + 1, peer);
			this.addNumber(this.capacitySuppliedPerStepSheet, 1, i + 1, peers[i].getPeerId());

			if(peers[i] instanceof Collaborator){
				Collaborator collab = (Collaborator) peers[i];
				for (int j = 0; j < this.numSteps; j++)
					this.addNumber(this.capacitySuppliedPerStepSheet, j + 2, i + 1, collab.getCapacitySuppliedHistory()[j]);
			}
			else{
				for (int j = 0; j < this.numSteps; j++)
					this.addNumber(this.capacitySuppliedPerStepSheet, j + 2, i + 1, 0.0);
			}
		}	
	}
	
	/**
	 * Write free riders success per step data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillfreeRiderSuccessData(Peer [] peers){
		// fulfilling donated peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof FreeRider){
				this.addText(this.freeRiderSuccessSheet, 0, i + 1, "Free Rider");
				this.addNumber(this.freeRiderSuccessSheet, 1, i + 1, peers[i].getPeerId());
				for (int j = 0; j < this.numSteps; j++)
					this.addText(this.freeRiderSuccessSheet, j + 2, i + 1, (((FreeRider)peers[i]).getSuccessHistory()[j])==true?"true":"false");
			}
		}	
	}
	
	/**
	 * Write buffer to file.
	 */
	public void writeFile(){
		
		FileOutputStream fileOutputStream = null;		
		try {
            fileOutputStream = new FileOutputStream(new File(this.outputFile));
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }	
	}
	
	/**
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param text the text to be written
	 */
	private void addLabel(XSSFSheet sheet, int column, int row, String text){		
		XSSFRow newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    XSSFCell cell = newRow.getCell(column);
	    if(cell==null)
	    	cell = newRow.createCell(column);
	    cell.setCellValue(text);
		cell.setCellStyle(this.timesBoldUnderline);
	}
	
	/**
	 * 
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param currentSatisfaction the satisfaction (double) to be written
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void addNumber(XSSFSheet sheet, int column, int row, double currentSatisfaction){		
		XSSFRow newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    XSSFCell cell = newRow.getCell(column);
	    if(cell==null)
	    	cell = newRow.createCell(column);
	    cell.setCellValue(currentSatisfaction);
	    cell.setCellStyle(this.times);
	}
	
	/**
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param text the text to be written
	 */
	private void addText(XSSFSheet sheet, int column, int row, String text){		
		XSSFRow newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    XSSFCell cell = newRow.getCell(column);
	    if(cell==null)
	    	cell = newRow.createCell(column);
	    cell.setCellValue(text);
	    cell.setCellStyle(this.times);
	}
	
}
