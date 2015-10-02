package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nof.NetworkOfFavors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.PeerComunity;
import simulator.Simulator;

public class WriteExcel {
	
	private String outputFile;
	private Simulator simulator;
	
	private Sheet fairnessSheet, satisfactionSheet, consumedSheet, consumedByTransitivitySheet, providedSheet, providedToCollaboratorsSheet, providedByTransitivitySheet, providedToFreeRidersSheet,
					currentConsumedSheet, currentConsumedByTransitivitySheet, currentProvidedSheet, currentProvidedToCollaboratorsSheet, currentProvidedByTransitivitySheet, currentProvidedToFreeRidersSheet;
	
	private XSSFWorkbook workbook;
	private CellStyle timesBoldUnderline, timesBlue;
	private Font times10ptBoldUnderline, times10pt;
	
	
	public WriteExcel(String outputFile, Simulator simulator) {
		this.outputFile = outputFile + ".xlsx";
		this.simulator = simulator;
		
		this.workbook = new XSSFWorkbook();
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
	
	public void outputPeers() {
		setupFile();
		fulfillFairness();
		fulfillSatisfaction();
//		fulfillConsumed();
//		fulfillConsumedByTransitivity();
//		fulfillProvided();
//		fulfillProvidedToCollaborators();
//		fulfillProvidedByTransitivity();
//		fulfillProvidedToFreeRiders();
		fulfillCurrentConsumed();
		fulfillCurrentConsumedByTransitivity();
		fulfillCurrentProvided();
		fulfillCurrentProvidedToCollaborators();
		fulfillCurrentProvidedByTransitivity();
		fulfillCurrentProvidedToFreeRiders();
		writeFile();		
	}
	
	
	public void setupFile() {		
		fairnessSheet = workbook.createSheet("fairness");
		satisfactionSheet = workbook.createSheet("satisfaction");
//		consumedSheet = workbook.createSheet("consumed");
//		consumedByTransitivitySheet = workbook.createSheet("consumed by transitivity");
//		providedSheet = workbook.createSheet("provided");
//		providedToCollaboratorsSheet = workbook.createSheet("provided to collaborators");
//		providedByTransitivitySheet = workbook.createSheet("provided by transitivity");
//		providedToFreeRidersSheet = workbook.createSheet("provided to free riders");
		currentConsumedSheet = workbook.createSheet("current consumed");
		currentConsumedByTransitivitySheet = workbook.createSheet("current consumed by transitivity");
		currentProvidedSheet = workbook.createSheet("current provided");
		currentProvidedToCollaboratorsSheet = workbook.createSheet("current provided to collaborators");
		currentProvidedByTransitivitySheet = workbook.createSheet("current provided by transitivity");
		currentProvidedToFreeRidersSheet = workbook.createSheet("current provided to free riders");
		createLabel(fairnessSheet);
		createLabel(satisfactionSheet);
//		createLabel(consumedSheet);
//		createLabel(consumedByTransitivitySheet);
//		createLabel(providedSheet);
//		createLabel(providedToCollaboratorsSheet);
//		createLabel(providedByTransitivitySheet);
//		createLabel(providedToFreeRidersSheet);
		createLabel(currentConsumedSheet);
		createLabel(currentConsumedByTransitivitySheet);
		createLabel(currentProvidedSheet);
		createLabel(currentProvidedToCollaboratorsSheet);
		createLabel(currentProvidedByTransitivitySheet);
		createLabel(currentProvidedToFreeRidersSheet);
	}
	
	private void createLabel(Sheet sheet){		
		addLabel(sheet, 0, 0, "Peers");
		addLabel(sheet, 1, 0, "ID");
		addLabel(sheet, 2, 0, sheet.getSheetName());		
		for (int i = 0; i < this.simulator.getNumSteps(); i++)
				this.addLabel(sheet, i + 2, 0, "Step " + (i + 1));
	}
	
	public void fulfillFairness(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(fairnessSheet, 0, i+1, "collaborator");
				this.addLabel(fairnessSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				double currentConsumed, currentDonated, fairness;
				for(int j = 0; j < simulator.getNumSteps(); j++){				
					currentConsumed = PeerComunity.peers[i].getCurrentConsumed(j);			
					currentDonated = PeerComunity.peers[i].getCurrentDonated(j);
					fairness = NetworkOfFavors.getFairness(currentConsumed, currentDonated);
					addNumber(fairnessSheet, j+2, i+1, fairness);
				}
			}
		}
	}
	
	public void fulfillSatisfaction(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(satisfactionSheet, 0, i+1, "collaborator");
				this.addLabel(satisfactionSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				double currentConsumed, currentRequested, satisfaction;
				for(int j = 0; j < simulator.getNumSteps(); j++){				
					currentConsumed = PeerComunity.peers[i].getCurrentConsumed(j);			
					currentRequested = PeerComunity.peers[i].getCurrentRequested(j);
					satisfaction = NetworkOfFavors.getSatisfaction(currentConsumed, currentRequested);
					addNumber(satisfactionSheet, j+2, i+1, satisfaction);
				}
			}
		}
	}
	
	public void fulfillConsumed(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(consumedSheet, 0, i+1, "collaborator");
				this.addLabel(consumedSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(consumedSheet, j+2, i+1, PeerComunity.peers[i].getCurrentConsumed(j));
			}
		}
	}
	
	public void fulfillConsumedByTransitivity(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(consumedByTransitivitySheet, 0, i+1, "collaborator");
				this.addLabel(consumedByTransitivitySheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(consumedByTransitivitySheet, j+2, i+1, PeerComunity.peers[i].getCurrentConsumedByTransitivity(j));
			}
		}
	}
	
	public void fulfillProvided(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(providedSheet, 0, i+1, "collaborator");
				this.addLabel(providedSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(providedSheet, j+2, i+1, PeerComunity.peers[i].getCurrentDonated(j));
			}
		}
	}
	
	public void fulfillProvidedToCollaborators(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(providedToCollaboratorsSheet, 0, i+1, "collaborator");
				this.addLabel(providedToCollaboratorsSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(providedToCollaboratorsSheet, j+2, i+1, PeerComunity.peers[i].getCurrentDonated(j) - PeerComunity.peers[i].getCurrentDonatedToFreeRiders(j));
			}
		}
	}
	
	public void fulfillProvidedByTransitivity(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(providedByTransitivitySheet, 0, i+1, "collaborator");
				this.addLabel(providedByTransitivitySheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(providedByTransitivitySheet, j+2, i+1, PeerComunity.peers[i].getCurrentDonatedByTransitivity(j));
			}
		}
	}
	
	public void fulfillProvidedToFreeRiders(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(providedToFreeRidersSheet, 0, i+1, "collaborator");
				this.addLabel(providedToFreeRidersSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(providedToFreeRidersSheet, j+2, i+1, PeerComunity.peers[i].getCurrentDonatedToFreeRiders(j));
			}
		}
	}
	
	public void fulfillCurrentConsumed(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(currentConsumedSheet, 0, i+1, "collaborator");
				this.addLabel(currentConsumedSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(currentConsumedSheet, j+2, i+1, PeerComunity.peers[i].getConsumedHistory()[j]);
			}
		}
	}
	
	public void fulfillCurrentConsumedByTransitivity(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(currentConsumedByTransitivitySheet, 0, i+1, "collaborator");
				this.addLabel(currentConsumedByTransitivitySheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(currentConsumedByTransitivitySheet, j+2, i+1, PeerComunity.peers[i].getConsumedByTransitivityHistory()[j]);
			}
		}
	}
	
	public void fulfillCurrentProvided(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(currentProvidedSheet, 0, i+1, "collaborator");
				this.addLabel(currentProvidedSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(currentProvidedSheet, j+2, i+1, PeerComunity.peers[i].getDonatedHistory()[j]);
			}
		}
	}
	
	public void fulfillCurrentProvidedToCollaborators(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(currentProvidedToCollaboratorsSheet, 0, i+1, "collaborator");
				this.addLabel(currentProvidedToCollaboratorsSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(currentProvidedToCollaboratorsSheet, j+2, i+1, PeerComunity.peers[i].getDonatedHistory()[j] - PeerComunity.peers[i].getDonatedToFreeRidersHistory()[j]);
			}
		}
	}
	
	public void fulfillCurrentProvidedByTransitivity(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(currentProvidedByTransitivitySheet, 0, i+1, "collaborator");
				this.addLabel(currentProvidedByTransitivitySheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(currentProvidedByTransitivitySheet, j+2, i+1, PeerComunity.peers[i].getDonatedByTransitivityHistory()[j]);
			}
		}
	}
	
	public void fulfillCurrentProvidedToFreeRiders(){		
		for (int i = 0; i < PeerComunity.peers.length; i++) {
			if(PeerComunity.peers[i] instanceof Collaborator){
				this.addLabel(currentProvidedToFreeRidersSheet, 0, i+1, "collaborator");
				this.addLabel(currentProvidedToFreeRidersSheet, 1, i+1, ""+PeerComunity.peers[i].getId());
				
				for(int j = 0; j < simulator.getNumSteps(); j++)			
					addNumber(currentProvidedToFreeRidersSheet, j+2, i+1, PeerComunity.peers[i].getDonatedToFreeRidersHistory()[j]);
			}
		}
	}
	
	
	
	
//	/**
//	 * Write consumption data.
//	 * 
//	 * @param peers the peers of simulation
//	 */
//	public void fulfillConsumptionData(Peer [] peers){
//		
//		int numCollaborators = 0;
//		
//		//fulfilling consumed peers cells
//		for (int i = 0; i < peers.length; i++) {
//			
//			if(peers[i] instanceof Collaborator){
//				String peer = "Collaborator";
//				numCollaborators++;
//			
//				this.addLabel(this.consumedSheet, 0, i + 1, peer);
//				this.addLabel(this.consumedSheet, 1, i + 1, ""+peers[i].getId());
//				
//				for(int j = 0; j < this.numSteps; j++)
//					this.addNumber(this.consumedSheet, j+2, i + 1, peers[i].getConsumedHistory()[j]);
//			}
//		}
//		
//		
//		
//		int firstCollaborator = 2;
//		int lastCollaborator = numCollaborators+1;
//		
//		int column = 2;					//'C'
//		
//		this.addLabel(this.consumedSheet, 0, lastCollaborator, "Consumed");
//		for (int step = 0; step < this.numSteps; step++) {
//			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstCollaborator+":"+CellReference.convertNumToColString(column)+""+lastCollaborator+")";
//			this.addFormula(this.consumedSheet, column, lastCollaborator , strFormulaCollab);
//			column++;			
//		}
//		
//		String strFormulaCollab = "SUM("+CellReference.convertNumToColString(2)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(this.numSteps+1)+""+(lastCollaborator+1)+")";
//		this.addFormula(this.consumedSheet, 2, lastCollaborator+1 , strFormulaCollab);
//	}

	
	
//	/**
//	 * Write donation data.
//	 * 
//	 * @param peers the peers of simulation
//	 */
//	public void fulfillDonationData(Peer [] peers){
//		
//		int numCollaborators = 0;
//		
//		// fulfilling donated peers cells
//		for (int i = 0; i < peers.length; i++) {
//			if(peers[i] instanceof Collaborator){
//				String peer = "Collaborator";
//				numCollaborators++;
//				
//				this.addLabel(this.donatedSheet, 0, i + 1, peer);
//				this.addLabel(this.donatedSheet, 1, i + 1, ""+peers[i].getId());
//				
//				for (int j = 0; j < this.numSteps; j++)
//					this.addNumber(this.donatedSheet, j + 2, i + 1, ((Collaborator)peers[i]).getDonatedHistory()[j]);
//			}
//		}	
//		
//		int firstCollaborator = 2;
//		int lastCollaborator = numCollaborators+1;
//		
//		int column = 2;					//'C'
//		
//		this.addLabel(this.donatedSheet, 0, lastCollaborator, "Donated");
//		for (int step = 0; step < this.numSteps; step++) {
//			String strFormulaCollab = "SUM("+CellReference.convertNumToColString(column)+""+firstCollaborator+":"+CellReference.convertNumToColString(column)+""+lastCollaborator+")";
//			this.addFormula(this.donatedSheet, column, lastCollaborator , strFormulaCollab);
//			column++;			
//		}
//		
//		String strFormulaCollab = "SUM("+CellReference.convertNumToColString(2)+""+(lastCollaborator+1)+":"+CellReference.convertNumToColString(this.numSteps+1)+""+(lastCollaborator+1)+")";
//		this.addFormula(this.donatedSheet, 2, lastCollaborator+1 , strFormulaCollab);
//	}
	
//	/**
//	 * Write capacity supplied data of peers per step.
//	 * 
//	 * @param peers the peers of simulation
//	 */
//	public void fulfillCapacitySuppliedData(Peer [] peers){
//		// fulfilling capacity supplied peers cells
//		for (int i = 0; i < peers.length; i++) {
//			if(peers[i] instanceof Collaborator){
//				String peer = "Collaborator";
//				this.addLabel(this.capacitySuppliedPerStepSheet, 0, i + 1, peer);
//				this.addLabel(this.capacitySuppliedPerStepSheet, 1, i + 1, ""+peers[i].getId());
//
//				Collaborator collab = (Collaborator) peers[i];
//				for (int j = 0; j < this.numSteps; j++)
//					this.addNumber(this.capacitySuppliedPerStepSheet, j + 2, i + 1, collab.getCapacitySuppliedHistory()[j]);
//			}
//		}	
//	}
	
	
//	public void fulfillContentionData(Peer [] peers){
//		// fulfilling contention peers cells
//		
//		double [] demandRequestedByCollaborators = new double[this.numSteps];
//		double [] demandRequestedByFreeRiders = new double[this.numSteps];
//		double [] capacitySupplied = new double[this.numSteps];
//		double [] contention = new double[this.numSteps];
//		
//		
//		for (int i = 0; i < peers.length; i++) {		
//			
//			if(peers[i] instanceof Collaborator){
//				Collaborator collab = (Collaborator) peers[i];
//				for (int j = 0; j < this.numSteps; j++){					
//					if(collab.getRequestedHistory()[j]==0)
//						capacitySupplied[j] += collab.getCapacitySuppliedHistory()[j];
//					else{
//						demandRequestedByCollaborators[j] += collab.getRequestedHistory()[j];
//					}
//				}
//			}
//			else{
//				for (int j = 0; j < this.numSteps; j++)
//					demandRequestedByFreeRiders[j] += peers[i].getRequestedHistory()[j]; 				
//			}
//		}
//		
//		for (int i = 0; i < this.numSteps; i++)
//			contention[i] = demandRequestedByCollaborators[i]/capacitySupplied[i];
//		
//		//fulfilling requested peers cells
//		for (int i = 0; i < this.numSteps; i++) {
//			this.addNumber(this.kPerStepSheet, i+1, 1, demandRequestedByFreeRiders[i]);
//			this.addNumber(this.kPerStepSheet, i+1, 2, demandRequestedByCollaborators[i]);
//			this.addNumber(this.kPerStepSheet, i+1, 3, capacitySupplied[i]);
//			this.addNumber(this.kPerStepSheet, i+1, 4, contention[i]);
//		}
//	}	
	
	
	/**
	 * Write buffer to file.
	 */
	public void writeFile(){
		
		FileOutputStream fileOutputStream = null;		
		try {
            fileOutputStream = new FileOutputStream(new File(outputFile));
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
