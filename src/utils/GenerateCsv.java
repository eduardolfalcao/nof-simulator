package utils;

import java.io.FileWriter;
import java.io.IOException;

import peer.Collaborator;
import peer.FreeRider;
import simulator.Simulator;
 
public class GenerateCsv{
	
	private String outputFile;
	private int numSteps;
	private Simulator sim;
	
	public GenerateCsv(String outputFile, int numSteps, Simulator sim){
		this.outputFile = outputFile;
		this.numSteps = numSteps;
		this.sim = sim; 
	}
	
	public void outputCollaborators(){
		FileWriter writer = this.createHeaderForCollaborator();
		writer = this.writeCollaborators(writer);
		this.flushFile(writer);
	}
	
	public void outputCapacitySupplied(){
		FileWriter writer = this.createHeaderForCapacitySupplied();
		writer = this.writeCapacitySupplied(writer);
		this.flushFile(writer);
	}	
	
	
	private FileWriter createHeaderForCollaborator(){
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile+"Collaborator.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 writer.append("fairness");
			 writer.append(',');
			 writer.append("satisfaction");
			 writer.append(',');
			 writer.append("index");
			 writer.append(',');
			 writer.append("demand");
			 writer.append(',');
			 writer.append("kappa");
			 writer.append(',');
			 writer.append("design");
			 writer.append(',');
			 writer.append("NoF");
			 writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writer;
	    
	}
	
	private FileWriter createHeaderForCapacitySupplied(){
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile+"CapacitySupplied.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer.append("demand");
			for(int i = 1; i <= numSteps; i++){
				writer.append(',');
				writer.append(i+"");
			}
		    writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writer;
	    
	}
	
	private FileWriter writeCollaborators(FileWriter writer){
		for (int i = 0; i < Simulator.peers.length; i++) {
			if(Simulator.peers[i] instanceof Collaborator){				
							
				try {
					
					//fairness
					Collaborator collab = (Collaborator) Simulator.peers[i];
					double currentConsumed, currentDonated, fairness;
					currentConsumed = collab.getCurrentConsumed(numSteps-1);			
					currentDonated = collab.getCurrentDonated(numSteps-1);
					fairness = Simulator.getFairness(currentConsumed, currentDonated);				
					writer.append(fairness+"");
					writer.append(',');
					
					//satisfaction
					double currentRequested = Simulator.peers[i].getCurrentRequested(numSteps-1);
					double satisfaction = Simulator.getFairness(currentConsumed, currentRequested);
					writer.append(satisfaction+"");
					writer.append(',');
					
					//index
					writer.append(Simulator.peers[i].getIndex()+"");
					writer.append(',');
					
					//demand
					writer.append(Simulator.peers[i].getInitialDemand()+"");
					writer.append(',');
					
					//kappa
					writer.append(this.sim.getKappa()+"");
					writer.append(',');
					
					//design
					writer.append(this.sim.getDesign()+"");
					writer.append(',');
					
					//nof
					writer.append((this.sim.isPairwise()?"FD-NoF":"SD-NoF")+"");
					writer.append('\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
		return writer;
	}
	
	private FileWriter writeCapacitySupplied(FileWriter writer){
		for (int i = 0; i < Simulator.peers.length; i++) {
			if(Simulator.peers[i] instanceof Collaborator){				
							
				try {
					double demandRatio = (Simulator.peers[i].getInitialDemand()-Simulator.peers[i].getInitialCapacity())/Simulator.peers[i].getInitialCapacity();	
					writer.append(demandRatio+"C");					
					
					Collaborator c = (Collaborator) Simulator.peers[i];
					for(int j = 0; j < numSteps; j++){
						writer.append(',');
						writer.append(c.getCapacitySuppliedHistory()[j]+"");
					}
					writer.append('\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
		return writer;
	}
	
	private void flushFile(FileWriter writer){
		
	    try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void outputFreeRiders(){
		FileWriter writer = this.createHeaderForFreeRider();
		writer = this.writeFreeRiders(writer);
		this.flushFile(writer);
	}
	
	private FileWriter createHeaderForFreeRider(){
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile+"Freerider.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 writer.append("satisfaction");
			 writer.append(',');
			 writer.append("index");
			 writer.append(',');
			 writer.append("demand");
			 writer.append(',');
			 writer.append("kappa");
			 writer.append(',');
			 writer.append("design");
			 writer.append(',');
			 writer.append("NoF");
			 writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writer;
	    
	}	
   
	private FileWriter writeFreeRiders(FileWriter writer){
		for (int i = 0; i < Simulator.peers.length; i++) {
			if(Simulator.peers[i] instanceof FreeRider){		
				
				try {
					
					//satisfaction
					FreeRider freeRider = (FreeRider) Simulator.peers[i];
					double currentConsumed = freeRider.getCurrentConsumed(numSteps-1);		
					double currentRequested = Simulator.peers[i].getCurrentRequested(numSteps-1);
					double satisfaction = Simulator.getFairness(currentConsumed, currentRequested);
					writer.append(satisfaction+"");
					writer.append(',');
					
					//index
					writer.append(Simulator.peers[i].getIndex()+"");
					writer.append(',');
					
					//demand
					writer.append(Simulator.peers[i].getInitialDemand()+"");
					writer.append(',');
					
					//kappa
					writer.append(this.sim.getKappa()+"");
					writer.append(',');
					
					//design
					writer.append(this.sim.getDesign()+"");
					writer.append(',');
					
					//nof
					writer.append((this.sim.isPairwise()?"FD-NoF":"SD-NoF")+"");
					writer.append('\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return writer;
	}
	

}
