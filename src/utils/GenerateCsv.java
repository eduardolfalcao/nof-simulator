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
import peer.State;
import simulator.PeerComunity;
import simulator.Simulator;
 
public class GenerateCsv{
	
	private String outputFile;
	private Simulator simulator;	
		
	public GenerateCsv(String outputFile, Simulator simulator){
		this.outputFile = outputFile + ".csv";
		this.simulator = simulator; 
	}
	
	public void outputPeers(){
		FileWriter writer = this.createHeaderForPeer();
		writer = writePeers(writer);
		flushFile(writer);
	}
	
	public void outputSharingLevel(){
		FileWriter writer = this.createHeaderForSharingLevel();
		writer = writeSharingLevel(writer);
		flushFile(writer);
	}
	
	private FileWriter createHeaderForPeer(){
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile);
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
			 writer.append("groupId");
			 writer.append(',');
			 writer.append("deviation");
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
			 writer.append(',');
			 writer.append("seed");
			 writer.append('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer;	    
	}
	
	private FileWriter createHeaderForSharingLevel() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			 writer.append("step");
			 writer.append(',');
			 writer.append("sharingLevelColab");
			 writer.append(',');
			 writer.append("sharingLevelFR");
			 writer.append(',');
			 writer.append("sharingLevelTotal");
			 writer.append(',');
			 writer.append("NoF");
			 writer.append(',');
			 writer.append("tMin");
			 writer.append(',');
			 writer.append("tMax");
			 writer.append(',');
			 writer.append("delta");
			 writer.append(',');
			 writer.append("deviation");
			 writer.append(',');
			 writer.append("seed");
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
		if(simulator.isTransitivity())
			nof += "Transitive ";
		if(!simulator.isFdNof())
			nof += "SD-NoF";
		else
			nof += "FD-NoF";
		
		double tMin = simulator.getTMin();
		double tMax = simulator.getTMax();
		double delta = simulator.getDeltaC();
		
		for(Peer p : PeerComunity.peers){
			double fairness = NetworkOfFavors.getFairness(p.getCurrentConsumed(simulator.getNumSteps()-1), 
					p.getCurrentDonated(simulator.getNumSteps()-1));
			
			double satisfaction = NetworkOfFavors.getSatisfaction(p.getCurrentConsumed(simulator.getNumSteps()-1), 
					p.getCurrentRequested(simulator.getNumSteps()-1));
			
			double overallBalance = 0;
			for(Interaction i : p.getInteractions())
				overallBalance += NetworkOfFavors.calculateBalance(i.getConsumed(), i.getDonated());
			
			int groupId = p.getGroupId();
			double deviation = p.getDeviation();			
			double demand = p.getInitialDemand();
			double capacity = p.getInitialCapacity();			
			
			int seed = simulator.getSeed();
			
			try {
				writer.append(fairness+","+satisfaction+","+overallBalance+","+groupId+","+deviation+","+demand+","+capacity+",");
				writer.append(kappa+","+numberOfCollaborators+","+numberOfFreeRiders+","+nof+","+tMin+","+tMax+","+delta+","+seed+"\n");
			} catch (IOException e) {
				Simulator.logger.finest("Exception while writing to output (csv) the performance of peers.");
				e.printStackTrace();
			}			
		}	
		
		return writer;
	}
	
	private FileWriter writeSharingLevel(FileWriter writer){
		
		String nof = "";
		if(simulator.isTransitivity())
			nof += "Transitive ";
		if(!simulator.isFdNof())
			nof += "SD-NoF";
		else
			nof += "FD-NoF";
		
		double tMin = simulator.getTMin();
		double tMax = simulator.getTMax();
		double delta = simulator.getDeltaC();
		double deviation = 0;
		int seed = simulator.getSeed();
				
		for(int step = 0; step < simulator.getNumSteps(); step++){
			double totalCapacity = 0, providedToCollab = 0, providedToFR = 0;
			for(Peer p : PeerComunity.peers){
				if(p instanceof Collaborator && p.getStateHistory()[step]==State.PROVIDING){
					totalCapacity += p.getInitialCapacity();
					providedToFR += p.getDonatedToFreeRidersHistory()[step];
					providedToCollab += p.getDonatedHistory()[step] - p.getDonatedToFreeRidersHistory()[step];
					
					deviation = p.getDeviation();
				}
			}
			try {
				writer.append((step+1)+","+(providedToCollab/totalCapacity)+","+(providedToFR/totalCapacity)+","+((providedToCollab+providedToFR)/totalCapacity)+","+
								nof+","+tMin+","+tMax+","+delta+","+deviation+","+seed+"\n");
			} catch (IOException e) {
				Simulator.logger.finest("Exception while writing to output (csv) the sharing level of the federation.");
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
