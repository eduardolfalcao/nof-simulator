package utils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.Simulator;

public class WriteExcel {

	private String outputFile;
	private WritableSheet satisfactionSheet, satisfactionPerStepSheet, consumedSheet, donatedSheet, capacitySuppliedPerStepSheet, freeRiderSuccessSheet;
	private int numSteps;
	
	private WritableWorkbook workbook;
	private WritableFont times10pt, times10ptBoldUnderline;
	private WritableCellFormat times, timesBoldUnderline;
	
	/**
	 * @param outputFile the url address where the file will be stored
	 * @param numSteps the number of steps of the simulation
	 */
	public WriteExcel(String outputFile, int numSteps) {
		super();
		this.outputFile = outputFile;
		this.numSteps = numSteps;
		
		times10pt = new WritableFont(WritableFont.TIMES, 10);	//Lets create a times font		
		times = new WritableCellFormat(times10pt);				//Define the cell format	
		times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		
		try {													// Lets automatically wrap the cells
			times.setWrap(true);
			timesBoldUnderline.setWrap(true);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Setup the xsl file for output.
	 */
	public void setupFile() {
		File file = new File(this.outputFile);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		// creates the tabs
		workbook = null;
		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Satisfaction", 0);
			workbook.createSheet("Satisfaction per steps", 1);
			workbook.createSheet("Consumed", 2);
			workbook.createSheet("Donated", 3);
			workbook.createSheet("Capacity supplied per steps", 4);
			workbook.createSheet("Free riders success per steps", 5);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			satisfactionSheet = workbook.getSheet(0);
			createLabel(satisfactionSheet);
			satisfactionPerStepSheet = workbook.getSheet(1);
			createLabel(satisfactionPerStepSheet);
			consumedSheet = workbook.getSheet(2);
			createLabel(consumedSheet);
			donatedSheet = workbook.getSheet(3);
			createLabel(donatedSheet);
			capacitySuppliedPerStepSheet = workbook.getSheet(4);
			createLabel(capacitySuppliedPerStepSheet); 
			freeRiderSuccessSheet = workbook.getSheet(5);
			createLabel(freeRiderSuccessSheet);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates the label of the tabs.
	 * 
	 * @param sheet the tab to be created the titles
	 * @throws WriteException
	 */
	private void createLabel(WritableSheet sheet) throws WriteException {

//		CellView cv = new CellView();
//		cv.setFormat(times);
//		cv.setFormat(timesBoldUnderline);
//		cv.setAutosize(true);

		if (sheet.getName().equals("Satisfaction")) {
			addLabel(sheet, 0, 0, "Peers");
			addLabel(sheet, 1, 0, "ID");
			addLabel(sheet, 2, 0, "Satisfaction");
		} 
		else if (sheet.getName().equals("Consumed")
				|| sheet.getName().equals("Donated")
				|| sheet.getName().equals("Satisfaction per steps")
				|| sheet.getName().equals("Capacity supplied per steps")
				|| sheet.getName().equals("Free riders success per steps")) {
			addLabel(sheet, 0, 0, "Peers");
			addLabel(sheet, 1, 0, "ID");
			for (int i = 0; i < this.numSteps; i++)
				addLabel(sheet, i + 2, 0, "Step " + (i + 1));
		}
	}

	/**
	 * Write satisfaction summary data.
	 * 
	 * @param peers the peers of simulation
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillSatisfactions(Peer [] peers) throws WriteException, RowsExceededException {
		
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
			this.addNumber(this.satisfactionSheet, 2, i+1, Simulator.getSatisfaction(currentDonated, currentConsumed, this.numSteps));
		}
	}
	
	/**
	 * Write satisfactions data per step.
	 * 
	 * @param peers the peers of simulation
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillSatisfactionsPerSteps(Peer [] peers) throws WriteException, RowsExceededException {
		
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
				this.addNumber(this.satisfactionPerStepSheet, j+2, i + 1, Simulator.getSatisfaction(currentDonated, currentConsumed, this.numSteps));
			}
		}
	}
	
	
	
	/**
	 * Write consumption data.
	 * 
	 * @param peers the peers of simulation
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillConsumptionData(Peer [] peers) throws WriteException, RowsExceededException {
		
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
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillDonationData(Peer [] peers) throws WriteException, RowsExceededException {
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
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillCapacitySuppliedData(Peer [] peers) throws WriteException, RowsExceededException {
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
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillfreeRiderSuccessData(Peer [] peers) throws WriteException, RowsExceededException {
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
	 * 
	 * @throws WriteException
	 */
	public void writeFile() throws WriteException{
		try {
			this.workbook.write();
			this.workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param text the text to be written
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void addLabel(WritableSheet sheet, int column, int row, String text)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, text, timesBoldUnderline);
		sheet.addCell(label);
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
	private void addNumber(WritableSheet sheet, int column, int row,
			double currentSatisfaction) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, currentSatisfaction, times);
		sheet.addCell(number);
	}
	
	/**
	 * @param sheet the tab on which will be written
	 * @param column the column on which will be written
	 * @param row the row on which will be written
	 * @param text the text to be written
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void addText(WritableSheet sheet, int column, int row, String text)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, text, times);
		sheet.addCell(label);
	}

}