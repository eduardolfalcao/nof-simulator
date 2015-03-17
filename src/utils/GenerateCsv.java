package utils;

import java.io.FileWriter;
import java.io.IOException;

import peer.Collaborator;
import peer.FreeRider;
import simulator.Simulator;
 
public class GenerateCsv{
	
	private String outputFile;
	private int numSteps;
	
	public GenerateCsv(String outputFile, int numSteps){
		this.outputFile = outputFile;
		this.numSteps = numSteps;
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
			writer.append("demand");
			writer.append(',');
		    writer.append("fairness");
		    writer.append(',');
		    writer.append("satisfaction");
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
					double demandRatio = (Simulator.peers[i].getInitialDemand()-Simulator.peers[i].getInitialCapacity())/Simulator.peers[i].getInitialCapacity();	
					writer.append(demandRatio+"C");					
					writer.append(',');
					
					Collaborator collab = (Collaborator) Simulator.peers[i];
					
					double currentConsumed, currentDonated, fairness;
					currentConsumed = collab.getCurrentConsumed(numSteps-1);			
					currentDonated = collab.getCurrentDonated(numSteps-1);
					fairness = Simulator.getFairness(currentConsumed, currentDonated);				
					writer.append(fairness+"");
					writer.append(',');
					
					double currentRequested = Simulator.peers[i].getCurrentRequested(numSteps-1);
					double satisfaction = Simulator.getFairness(currentConsumed, currentRequested);
					writer.append(satisfaction+"");
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
			writer.append("demand");
			writer.append(',');
		    writer.append("satisfaction");
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
					double demandRatio = (Simulator.peers[i].getInitialDemand()-Simulator.peers[i].getInitialCapacity())/Simulator.peers[i].getInitialCapacity();	
					writer.append(demandRatio+"C");					
					writer.append(',');
					
					double currentConsumed = Simulator.peers[i].getCurrentConsumed(numSteps-1);			
					double currentRequested = Simulator.peers[i].getCurrentRequested(numSteps-1);
					double satisfaction = Simulator.getFairness(currentConsumed, currentRequested);
					writer.append(satisfaction+"");
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
