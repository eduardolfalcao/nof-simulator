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
import peer.Peer;

public class WriteExcel {

	private String outputFile;
	private WritableSheet satisfactionSheet, consumedSheet, donatedSheet;
	private int numSteps;
	
	private WritableWorkbook workbook;
	private WritableFont times10pt, times10ptBoldUnderline;
	private WritableCellFormat times, timesBoldUnderline;
	

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

	public void setupFile() {
		File file = new File(this.outputFile);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		// creates the tabs
		workbook = null;
		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Satisfaction", 0);
			workbook.createSheet("Consumed", 1);
			workbook.createSheet("Donated", 2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			satisfactionSheet = workbook.getSheet(0);
			createLabel(satisfactionSheet);
			consumedSheet = workbook.getSheet(1);
			createLabel(consumedSheet);
			donatedSheet = workbook.getSheet(2);
			createLabel(donatedSheet);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates the tabs.
	 * 
	 * @param sheet
	 * @throws WriteException
	 */
	private void createLabel(WritableSheet sheet) throws WriteException {

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		if (sheet.getName().equals("Satisfaction")) {
			addCaption(sheet, 0, 0, "Peers");
			addCaption(sheet, 1, 0, "ID");
			addCaption(sheet, 2, 0, "Satisfaction");
		} else if (sheet.getName().equals("Consumed")
				|| sheet.getName().equals("Donated")) {
			addCaption(sheet, 0, 0, "Peers");
			addCaption(sheet, 1, 0, "ID");
			for (int i = 0; i < this.numSteps; i++)
				addCaption(sheet, i + 2, 0, "Step " + (i + 1));
		}
	}

	/**
	 * Fulfills the excel file.
	 * 
	 * @param peers
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public void fulfillFile(Peer [] peers) throws WriteException, RowsExceededException {
		
		//fulfilling satisfaction peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator)?"Collaborator":"Free Rider"; 
			this.addLabel(this.satisfactionSheet, 0, i+1, peer);
			this.addNumber(this.satisfactionSheet, 1, i+1, peers[i].getPeerId());
			double currentDonated = 0, currentConsumed = 0, currentSatisfaction = 0;
			if(peers[i] instanceof Collaborator){
				Collaborator collaborator = (Collaborator) peers[i];	
				currentDonated = peers[i].getCurrentDonated(this.numSteps-1)!=0?peers[i].getCurrentDonated(this.numSteps-1):1;						
				currentConsumed = peers[i].getCurrentConsumed(this.numSteps-1);
			}
			else{
				currentDonated = 1;
				currentConsumed = peers[i].getCurrentConsumed(this.numSteps-1);
			}
			currentSatisfaction = currentConsumed/currentDonated;
			this.addNumber(this.satisfactionSheet, 2, i+1, currentSatisfaction);
		}
		
		//fulfilling consumed peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator) ? "Collaborator": "Free Rider";
			this.addLabel(this.consumedSheet, 0, i + 1, peer);
			this.addNumber(this.consumedSheet, 1, i + 1, peers[i].getPeerId());
			
			for(int j = 0; j < this.numSteps; j++)
				this.addNumber(this.consumedSheet, j+2, i + 1, peers[i].getConsumedHistory()[j]);
		}
		
		// fulfilling donated peers cells
		for (int i = 0; i < peers.length; i++) {
			String peer = (peers[i] instanceof Collaborator) ? "Collaborator": "Free Rider";
			this.addLabel(this.donatedSheet, 0, i + 1, peer);
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
		
		try {
			this.workbook.write();
			this.workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * 
	 * @param sheet
	 * @param column
	 * @param row
	 * @param text
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void addCaption(WritableSheet sheet, int column, int row, String text)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, text, timesBoldUnderline);
		sheet.addCell(label);
	}

	/**
	 * 
	 * @param sheet
	 * @param column
	 * @param row
	 * @param currentSatisfaction
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
	 * 
	 * @param sheet
	 * @param column
	 * @param row
	 * @param text
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void addLabel(WritableSheet sheet, int column, int row, String text)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, text, times);
		sheet.addCell(label);
	}

}