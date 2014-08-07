package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.Simulator;

public class WriteExcel2010 {
	
	private String outputFile;
	private Sheet fairnessSheet, fairnessPerStepSheet, consumedSheet, requestedSheet, satisfactionPerStepSheet, donatedSheet, capacitySuppliedPerStepSheet, freeRidersSatisfactionSheet;
	private int numSteps;
	
	private XSSFWorkbook workbook;
	private CellStyle timesBoldUnderline, timesBlue;
	private Font times10ptBoldUnderline, times10pt;
	
	
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
		this.times10ptBoldUnderline.setFontHeightInPoints((short) 10);
		this.times10ptBoldUnderline.setColor(IndexedColors.BLACK.getIndex());
		this.times10ptBoldUnderline.setBoldweight(Font.BOLDWEIGHT_BOLD);
		this.timesBoldUnderline.setFont(times10ptBoldUnderline);
		
		
		this.timesBoldUnderline = this.workbook.createCellStyle();
		this.times10ptBoldUnderline = this.workbook.createFont();		
		this.times10ptBoldUnderline.setFontHeightInPoints((short) 10);
		this.times10ptBoldUnderline.setColor(IndexedColors.BLACK.getIndex());
		this.times10ptBoldUnderline.setBoldweight(Font.BOLDWEIGHT_BOLD);
		this.timesBoldUnderline.setFont(times10ptBoldUnderline);
		
		
		this.timesBlue = this.workbook.createCellStyle();
		this.times10pt = this.workbook.createFont();
		this.times10pt.setFontHeightInPoints((short) 10);
		this.times10pt.setColor(IndexedColors.BLUE.getIndex());
		this.timesBlue.setFont(times10pt);
	}
	
	/**
	 * Setup the xslx file for output.
	 */
	public void setupFile() {		 
		
//		fairnessSheet = workbook.createSheet("Fairness");
		fairnessPerStepSheet = workbook.createSheet("Fairness per steps");
//		consumedSheet = workbook.createSheet("Consumed");
//		requestedSheet = workbook.createSheet("Requested");
		satisfactionPerStepSheet = workbook.createSheet("Satisfaction");
//		donatedSheet = workbook.createSheet("Donated");
		freeRidersSatisfactionSheet = workbook.createSheet("Free riders satisfactions");
		capacitySuppliedPerStepSheet = workbook.createSheet("Capacity supplied per steps");
		

//		createLabel(fairnessSheet);
		createLabel(fairnessPerStepSheet);
//		createLabel(consumedSheet);
//		createLabel(requestedSheet);
		createLabel(satisfactionPerStepSheet);
//		createLabel(donatedSheet);
		createLabel(freeRidersSatisfactionSheet);
		createLabel(capacitySuppliedPerStepSheet); 
		
	}
	
	/**
	 * Creates the label of the tabs.
	 * 
	 * @param sheet the tab to be created the titles
	 */
	private void createLabel(Sheet sheet){
		
		addLabel(sheet, 0, 0, "Peers");
		addLabel(sheet, 1, 0, "ID");
		
		if (sheet.getSheetName().equals("Fairness")) 
			addLabel(sheet, 2, 0, "Fairness");
		else if (sheet.getSheetName().equals("Consumed")
				|| sheet.getSheetName().equals("Requested")
				|| sheet.getSheetName().equals("Satisfaction")
				|| sheet.getSheetName().equals("Donated")
				|| sheet.getSheetName().equals("Fairness per steps")
				|| sheet.getSheetName().equals("Capacity supplied per steps")
				|| sheet.getSheetName().equals("Free riders satisfactions")) {
			for (int i = 0; i < this.numSteps; i++)
				this.addLabel(sheet, i + 2, 0, "Step " + (i + 1));
		}
	}
	
	/**
	 * Write satisfaction summary data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillFairness(Peer [] peers){
		
		int numCollaborators = 0;
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof Collaborator){
				String peer = "Collaborator";
				numCollaborators++;
				
				this.addLabel(this.fairnessSheet, 0, i+1, peer);
				this.addLabel(this.fairnessSheet, 1, i+1, ""+peers[i].getPeerId());
				
				double currentConsumed, currentDonated, fairness;
				
				currentConsumed = peers[i].getCurrentConsumed(this.numSteps-1);	
				currentDonated = peers[i].getCurrentDonated(this.numSteps-1);
				fairness = Simulator.getFairness(currentConsumed, currentDonated);
				
				this.addNumber(this.fairnessSheet, 2, i+1, fairness);
			}
		}
		
		this.addLabel(this.fairnessSheet, 4, 0, "Fairness(%)");
		String strFormulaCollab = "SUM(C2:C"+(numCollaborators+1)+")/"+numCollaborators;
		this.addFormula(this.fairnessSheet, 4, 1, strFormulaCollab);
	}
	
	/**
	 * Write fairness data per step.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillFairnessPerSteps(Peer [] peers){
		
		int numCollaborators = 0;
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof Collaborator){
				String peer = "Collaborator";
				numCollaborators++;
				
				this.addLabel(this.fairnessPerStepSheet, 0, i+1, peer);
				this.addLabel(this.fairnessPerStepSheet, 1, i+1, ""+peers[i].getPeerId());
				
				double currentConsumed, currentDonated, fairness;
				
				for(int j = 0; j < this.numSteps; j++){				
					currentConsumed = peers[i].getCurrentConsumed(j);			
					currentDonated = peers[i].getCurrentDonated(j);
					fairness = Simulator.getFairness(currentConsumed, currentDonated);
					
					this.addNumber(this.fairnessPerStepSheet, j+2, i+1, fairness);
				}
			}
		}
		
		
		int firstCollaborator = 2;
		int lastCollaborator = numCollaborators+1;
		
		int column = 2;					//'C'
		
		this.addLabel(this.fairnessPerStepSheet, 0, lastCollaborator, "Fairness(%)");
		for (int step = 0; step < this.numSteps; step++) {
			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstCollaborator+":"+CellReference.convertNumToColString(column)+""+lastCollaborator+")/"+numCollaborators;
			this.addFormula(this.fairnessPerStepSheet, column, lastCollaborator , strFormulaCollab);
			column++;			
		}
		
		column = 2;
		this.addLabel(this.fairnessPerStepSheet, 0, lastCollaborator+1, "Fairness(Average(last50))%");
		for (int step = 1; step <= 49; step++) {
			String strFormulaCollab = "AVERAGE("+CellReference.convertNumToColString(column-step+1)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(column)+""+(lastCollaborator+1)+")";
			this.addFormula(this.fairnessPerStepSheet, column, lastCollaborator+1 , strFormulaCollab);
			column++;			
		}
		column = 51;
		for (int step = 50; step <= this.numSteps; step++) {
			String strFormulaCollab = "AVERAGE("+CellReference.convertNumToColString(column-50)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(column)+""+(lastCollaborator+1)+")";
			this.addFormula(this.fairnessPerStepSheet, column, lastCollaborator+1 , strFormulaCollab);
			column++;			
		}
	}
	
	
	
	/**
	 * Write consumption data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillConsumptionData(Peer [] peers){
		
		int numCollaborators = 0;
		
		//fulfilling consumed peers cells
		for (int i = 0; i < peers.length; i++) {
			
			if(peers[i] instanceof Collaborator){
				String peer = "Collaborator";
				numCollaborators++;
			
				this.addLabel(this.consumedSheet, 0, i + 1, peer);
				this.addLabel(this.consumedSheet, 1, i + 1, ""+peers[i].getPeerId());
				
				for(int j = 0; j < this.numSteps; j++)
					this.addNumber(this.consumedSheet, j+2, i + 1, peers[i].getConsumedHistory()[j]);
			}
		}
		
		
		
		int firstCollaborator = 2;
		int lastCollaborator = numCollaborators+1;
		
		int column = 2;					//'C'
		
		this.addLabel(this.consumedSheet, 0, lastCollaborator, "Consumed");
		for (int step = 0; step < this.numSteps; step++) {
			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstCollaborator+":"+CellReference.convertNumToColString(column)+""+lastCollaborator+")";
			this.addFormula(this.consumedSheet, column, lastCollaborator , strFormulaCollab);
			column++;			
		}
		
		String strFormulaCollab = "SUM("+CellReference.convertNumToColString(2)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(this.numSteps+1)+""+(lastCollaborator+1)+")";
		this.addFormula(this.consumedSheet, 2, lastCollaborator+1 , strFormulaCollab);
	}
	
	/**
	 * Write requested data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillRequestedData(Peer [] peers){
		
		//fulfilling requested peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator) ? "Collaborator": "Free Rider";
			this.addLabel(this.requestedSheet, 0, i + 1, peer);
			this.addLabel(this.requestedSheet, 1, i + 1, ""+peers[i].getPeerId());
			
			for(int j = 0; j < this.numSteps; j++)
				this.addNumber(this.requestedSheet, j+2, i + 1, peers[i].getRequestedHistory()[j]);
		}
	}
	
	
	/**
	 * Write satisfactions (consumed/requested) data per step.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillSatisfactionPerSteps(Peer [] peers){
		
		int numCollaborators = 0;
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof Collaborator){
				String peer = "Collaborator";
				numCollaborators++;
				
				this.addLabel(this.satisfactionPerStepSheet, 0, i+1, peer);
				this.addLabel(this.satisfactionPerStepSheet, 1, i+1, ""+peers[i].getPeerId());
				
				double currentConsumed, currentRequested, satisfaction;
				
				for(int j = 0; j < this.numSteps; j++){				
					currentConsumed = peers[i].getCurrentConsumed(j);				
					currentRequested = peers[i].getCurrentRequested(j);
					satisfaction = Simulator.getFairness(currentConsumed, currentRequested);
					
					this.addNumber(this.satisfactionPerStepSheet, j+2, i+1, satisfaction);
				}
			}
		}
		
		
		int firstCollaborator = 2;
		int lastCollaborator = numCollaborators+1;
		
		int column = 2;					//'C'
		
		this.addLabel(this.satisfactionPerStepSheet, 0, lastCollaborator, "Satisfaction(%)");
		for (int step = 0; step < this.numSteps; step++) {
			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstCollaborator+":"+CellReference.convertNumToColString(column)+""+lastCollaborator+")/"+numCollaborators;
			this.addFormula(this.satisfactionPerStepSheet, column, lastCollaborator , strFormulaCollab);
			column++;			
		}
		
		column = 2;
		this.addLabel(this.satisfactionPerStepSheet, 0, lastCollaborator+1, "Satisfaction(Average(last50))%");
		for (int step = 1; step <= 49; step++) {
			String strFormulaCollab = "AVERAGE("+CellReference.convertNumToColString(column-step+1)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(column)+""+(lastCollaborator+1)+")";
			this.addFormula(this.satisfactionPerStepSheet, column, lastCollaborator+1 , strFormulaCollab);
			column++;			
		}
		
		column = 51;
		for (int step = 50; step <= this.numSteps; step++) {
			String strFormulaCollab = "AVERAGE("+CellReference.convertNumToColString(column-50)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(column)+""+(lastCollaborator+1)+")";
			this.addFormula(this.satisfactionPerStepSheet, column, lastCollaborator+1 , strFormulaCollab);
			column++;			
		}
	}
	
	/**
	 * Write donation data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillDonationData(Peer [] peers){
		
		int numCollaborators = 0;
		
		// fulfilling donated peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof Collaborator){
				String peer = "Collaborator";
				numCollaborators++;
				
				this.addLabel(this.donatedSheet, 0, i + 1, peer);
				this.addLabel(this.donatedSheet, 1, i + 1, ""+peers[i].getPeerId());
				
				for (int j = 0; j < this.numSteps; j++)
					this.addNumber(this.donatedSheet, j + 2, i + 1, peers[i].getDonatedHistory()[j]);
			}
		}	
		
		int firstCollaborator = 2;
		int lastCollaborator = numCollaborators+1;
		
		int column = 2;					//'C'
		
		this.addLabel(this.donatedSheet, 0, lastCollaborator, "Donated");
		for (int step = 0; step < this.numSteps; step++) {
			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstCollaborator+":"+CellReference.convertNumToColString(column)+""+lastCollaborator+")";
			this.addFormula(this.donatedSheet, column, lastCollaborator , strFormulaCollab);
			column++;			
		}
		
		String strFormulaCollab = "SUM("+CellReference.convertNumToColString(2)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(this.numSteps+1)+""+(lastCollaborator+1)+")";
		this.addFormula(this.donatedSheet, 2, lastCollaborator+1 , strFormulaCollab);
	}
	
	/**
	 * Write capacity supplied data of peers per step.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillCapacitySuppliedData(Peer [] peers){
		// fulfilling donated peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof Collaborator){
				String peer = "Collaborator";
				this.addLabel(this.capacitySuppliedPerStepSheet, 0, i + 1, peer);
				this.addLabel(this.capacitySuppliedPerStepSheet, 1, i + 1, ""+peers[i].getPeerId());

				Collaborator collab = (Collaborator) peers[i];
				for (int j = 0; j < this.numSteps; j++)
					this.addNumber(this.capacitySuppliedPerStepSheet, j + 2, i + 1, collab.getCapacitySuppliedHistory()[j]);
			}
		}	
	}
	
	/**
	 * Write free riders success per step data.
	 * 
	 * @param peers the peers of simulation
	 */
	public void fulfillfreeRiderSatisfactionsData(Peer [] peers){
		
		int numFreeRiders = 0;
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			if(peers[i] instanceof FreeRider){
				String peer = "Free Rider";
				
				this.addLabel(this.freeRidersSatisfactionSheet, 0, numFreeRiders+1, peer);
				this.addLabel(this.freeRidersSatisfactionSheet, 1, numFreeRiders+1, ""+peers[i].getPeerId());
				
				double currentConsumed, currentRequested, satisfaction;
				
				for(int j = 0; j < this.numSteps; j++){				
					currentConsumed = peers[i].getCurrentConsumed(j);				
					currentRequested = peers[i].getCurrentRequested(j);
					satisfaction = Simulator.getFairness(currentConsumed, currentRequested);
					
					this.addNumber(this.freeRidersSatisfactionSheet, j+2, numFreeRiders+1, satisfaction);
				}
				numFreeRiders++;
			}
		}
		
		
		int firstFreeRider = 2;
		int lastFreeRider = numFreeRiders+1;
		
		int column = 2;					//'C'
		
		this.addLabel(this.freeRidersSatisfactionSheet, 0, lastFreeRider, "Satisfaction(%)");
		for (int step = 0; step < this.numSteps; step++) {
			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstFreeRider+":"+CellReference.convertNumToColString(column)+""+lastFreeRider+")/"+numFreeRiders;
			this.addFormula(this.freeRidersSatisfactionSheet, column, lastFreeRider , strFormulaCollab);
			column++;			
		}
		
		column = 2;
		this.addLabel(this.freeRidersSatisfactionSheet, 0, lastFreeRider+1, "Satisfaction(Average(last50))%");
		for (int step = 1; step <= 49; step++) {
			String strFormulaCollab = "AVERAGE("+CellReference.convertNumToColString(column-step+1)+""+(lastFreeRider+1)+":"+CellReference.convertNumToColString(column)+""+(lastFreeRider+1)+")";
			this.addFormula(this.freeRidersSatisfactionSheet, column, lastFreeRider+1 , strFormulaCollab);
			column++;			
		}
		
		column = 51;
		for (int step = 50; step <= this.numSteps; step++) {
			String strFormulaCollab = "AVERAGE("+CellReference.convertNumToColString(column-50)+""+(lastFreeRider+1)+":"+CellReference.convertNumToColString(column)+""+(lastFreeRider+1)+")";
			this.addFormula(this.freeRidersSatisfactionSheet, column, lastFreeRider+1 , strFormulaCollab);
			column++;			
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
	private void addLabel(Sheet sheet, int column, int row, String text){		
		Row newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    Cell cell = newRow.getCell(column);
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
	private void addNumber(Sheet sheet, int column, int row, double currentSatisfaction){		
		Row newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    Cell cell = newRow.getCell(column);
	    if(cell==null)
	    	cell = newRow.createCell(column);
	    cell.setCellValue(currentSatisfaction);
	    cell.setCellStyle(this.timesBlue);
	}
	
	/**
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param text the text to be written
	 */
	private void addText(Sheet sheet, int column, int row, String text){		
		Row newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    Cell cell = newRow.getCell(column);
	    if(cell==null)
	    	cell = newRow.createCell(column);
	    cell.setCellValue(text);
	    cell.setCellStyle(this.timesBlue);
	}
	
	/**
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param formula the formula to be calculated
	 */
	private void addFormula(Sheet sheet, int column, int row, String formula){		
		Row newRow = sheet.getRow(row);
		if(newRow==null)
			newRow = sheet.createRow(row);
	    Cell cell = newRow.getCell(column);
	    if(cell==null)
	    	cell = newRow.createCell(column);
		cell.setCellType(Cell.CELL_TYPE_FORMULA);
		cell.setCellFormula(formula);
	    cell.setCellStyle(this.timesBlue);
	}
	
}
