package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nof.Interaction;
import nof.NetworkOfFavors;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.PeerComunity;
import simulator.Simulator;
 
public class GenerateCsv{
	
	private String outputFile;
	private Simulator simulator;	
		
	public GenerateCsv(String outputFile, Simulator simulator){
		this.outputFile = outputFile;
		this.simulator = simulator; 
	}
	
	public void outputPeers(){
		FileWriter writer = this.createHeaderForPeer();
		writer = writePeers(writer);
		flushFile(writer);
	}
	
	private FileWriter createHeaderForPeer(){
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile+".csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			 writer.append("fairness");
			 writer.append(',');
			 writer.append("satisfaction");
			 writer.append(',');
			 writer.append("overallBalance");
			 writer.append(',');			
			 writer.append("consumingPI");
			 writer.append(',');
			 writer.append("idlePI");
			 writer.append(',');
			 writer.append("providingPI");
			 writer.append(',');			 
			 writer.append("demand");
			 writer.append(',');
			 writer.append("capacity");
			 writer.append(',');			 
			 writer.append("kappa");
			 writer.append(',');
			 writer.append("numberOfCollaborators");
			 writer.append(',');
			 writer.append("numberOfFreeRiders");
			 writer.append(',');
			 writer.append("NoF");
			 writer.append(',');
			 writer.append("tMin");
			 writer.append(',');
			 writer.append("tMax");
			 writer.append(',');
			 writer.append("delta");
			 writer.append('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer;	    
	}
	
	private FileWriter writePeers(FileWriter writer){
		
		double kappa = simulator.getKappa();
		
		int numberOfCollaborators = simulator.getPeerComunity().getNumCollaborators();
		int numberOfFreeRiders = simulator.getPeerComunity().getNumFreeRiders();
		
		String nof = "";
		if(!simulator.isFdNof())
			nof = "SD-No";
		else
			nof = "FD-No";
		if(simulator.isTransitivity())
			nof += "TF";
		else
			nof += "F";
		
		double tMin = simulator.getTMin();
		double tMax = simulator.getTMax();
		double delta = simulator.getDeltaC();
		
		for(Peer p : PeerComunity.peers){
			double fairness = Simulator.getFairness(p.getCurrentConsumed(simulator.getNumSteps()-1), 
					p.getCurrentDonated(simulator.getNumSteps()-1));
			
			double satisfaction = Simulator.getSatisfaction(p.getCurrentConsumed(simulator.getNumSteps()-1), 
					p.getCurrentRequested(simulator.getNumSteps()-1));
			
			double overallBalance = 0;
			for(Interaction i : p.getInteractions())
				overallBalance += NetworkOfFavors.calculateBalance(i.getConsumed(), i.getDonated());
			
			double consumingPI = p.getConsumingStateProbability();
			double idlePI = p.getIdleStateProbability();
			double providingPI = p.getProvidingStateProbability();			
			double demand = p.getInitialDemand();
			double capacity = p.getInitialCapacity();			
			
			try {
				writer.append(fairness+","+satisfaction+","+overallBalance+","+consumingPI+","+idlePI+","+providingPI+","+demand+","+capacity+",");
				writer.append(kappa+","+numberOfCollaborators+","+numberOfFreeRiders+","+nof+","+tMin+","+tMax+","+delta+"\n");
			} catch (IOException e) {
				Simulator.logger.finest("Exception while writing to output (csv) the performance of peers.");
				e.printStackTrace();
			}			
		}	
		
		return writer;
	}
	
	private void flushFile(FileWriter writer){		
	    try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Simulator.logger.finest("Exception while flushing data to File.");
			e.printStackTrace();
		}		
	}
}
