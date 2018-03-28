package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.State;
import simulator.Simulator;

public class GenerateCsv {

	private String outputFile;
	private int numSteps;
	private Simulator sim;

	public GenerateCsv(String outputFile, int numSteps, Simulator sim) {
		this.outputFile = outputFile;
		this.numSteps = numSteps;
		this.sim = sim;
	}

	public void outputPeers() {
		FileWriter writer = this.createHeaderForPeer();
		writer = this.writePeers(writer);
		this.flushFile(writer);
	}

	public void outputWelfareCollaborators() {
		FileWriter writer = this.createHeaderForWelfareCollaborator();
		writer = this.writeWelfareCollaborators(writer);
		this.flushFile(writer);
	}

	private FileWriter writeWelfareCollaborators(FileWriter writer) {
		List<Collaborator> collabs = new ArrayList<Collaborator>();

		for (int i = 0; i < sim.getPeers().length; i++) {
			if (sim.getPeers()[i] instanceof Collaborator)
				collabs.add((Collaborator) sim.getPeers()[i]);
		}

		double[] consumedByCollaborators = new double[numSteps];
		double[] donatedToEveryone = new double[numSteps];
		double[] welfare = new double[numSteps];
		// for(int turn = 0; turn < numSteps; turn++){
		// for(Collaborator c : collabs){
		// consumedByCollaborators[turn] += c.getConsumedHistory()[turn];
		// donatedToEveryone[turn] += c.getDonatedHistory()[turn];
		// }
		// welfare[turn] =
		// consumedByCollaborators[turn]/donatedToEveryone[turn];
		// }

		for (int turn = 0; turn < numSteps; turn++) {
			for (Collaborator c : collabs) {
				consumedByCollaborators[turn] += c.getCurrentConsumed(turn);
				donatedToEveryone[turn] += c.getCurrentDonated(turn);
			}
			double fairnessOfCollaborators = consumedByCollaborators[turn]
					/ donatedToEveryone[turn];
			welfare[turn] = fairnessOfCollaborators;
		}

		for (int turn = 0; turn < numSteps; turn++) {
			try {
				// turn
				writer.append((turn + 1) + "");
				writer.append(',');

				// welfareRA ==> welfareRunningAverage
				double welfareRunningAverage = 0;
				for (int i = turn; i >= Math.max(turn - 499, 0); i--) {
					welfareRunningAverage += welfare[i];
				}
				welfareRunningAverage = welfareRunningAverage
						/ Math.min(turn + 1, 500);

				writer.append((welfareRunningAverage) + "");
				writer.append(',');

				writer.append((welfare[turn]) + "");
				writer.append(',');

				// kappa
				writer.append(this.sim.getKappa() + "");
				writer.append(',');

				// f
				writer.append(this.sim.getF());
				writer.append(',');

				// nof
				writer.append((this.sim.isPairwise() ? "FD-NoF" : "SD-NoF")
						+ "");
				writer.append(',');

				// tau
				writer.append(this.sim.getFairnessLowerThreshold() + "");
				writer.append(',');

				// delta
				writer.append(this.sim.getChangingValue() + "");
				writer.append('\n');

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while writing free riders...");
			}
		}

		return writer;
	}

	private FileWriter createHeaderForWelfareCollaborator() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile
					+ "-WelfareColaborators.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.append("turn");
			writer.append(',');
			writer.append("welfareRA");
			writer.append(',');
			writer.append("welfare");
			writer.append(',');
			writer.append("kappa");
			writer.append(',');
			writer.append("f");
			writer.append(',');
			writer.append("NoF");
			writer.append(',');
			writer.append("tau");
			writer.append(',');
			writer.append("delta");
			writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer;
	}

	public void outputCollaborators() {
		FileWriter writer = this.createHeaderForCollaborator();
		writer = this.writeCollaborators(writer);
		this.flushFile(writer);
	}

	public void outputFreeRiders() {
		FileWriter writer = this.createHeaderForFreeRider();
		writer = this.writeFreeRiders(writer);
		this.flushFile(writer);
	}

	public void outputCapacitySupplied() {
		FileWriter writer = this.createHeaderForCapacitySupplied();
		writer = this.writeCapacitySupplied(writer);
		this.flushFile(writer);
	}

	private FileWriter createHeaderForPeer() {

		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile + ".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.append("pi");
			writer.append(',');
			writer.append("d");
			writer.append(',');
			writer.append("fairness");
			writer.append(',');
			writer.append("satisfaction");
			writer.append(',');
			writer.append("kappa");
			writer.append(',');
			writer.append("f");
			writer.append(',');
			writer.append("NoF");
			writer.append(',');
			writer.append("tauMin");
			writer.append(',');
			writer.append("tauMax");
			writer.append(',');
			writer.append("delta");
			writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer;

	}

	private FileWriter createHeaderForFreeRider() {

		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile + "-FreeRiders.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.append("turn");
			writer.append(',');
			writer.append("welfareRA");
			writer.append(',');
			writer.append("welfare");
			writer.append(',');
			writer.append("kappa");
			writer.append(',');
			writer.append("kappaRA");
			writer.append(',');
			writer.append("f");
			writer.append(',');
			writer.append("NoF");
			writer.append(',');
			writer.append("tauMin");
			writer.append(',');
			writer.append("tauMax");
			writer.append(',');
			writer.append("delta");
			writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer;

	}

	private FileWriter writePeers(FileWriter writer) {

		List<Collaborator> collabs = new ArrayList<Collaborator>();
		List<FreeRider> frs = new ArrayList<FreeRider>();

		for (int i = 0; i < sim.getPeers().length; i++) {
			if (sim.getPeers()[i] instanceof Collaborator)
				collabs.add((Collaborator) sim.getPeers()[i]);
			else if (sim.getPeers()[i] instanceof FreeRider)
				frs.add((FreeRider) sim.getPeers()[i]);
		}

		// peer, fairness, satisfaction, kappa, f, NoF, tau

		// collaborators
		for (Collaborator c : collabs) {
			try {
				writer.append(c.getConsumingStateProbability() + "");
				writer.append(',');

				writer.append(c.getInitialDemand() + "");
				writer.append(',');

				// fairness
				double currentConsumed, currentDonated, fairness;
				currentConsumed = c.getCurrentConsumed(numSteps - 1);
				currentDonated = c.getCurrentDonated(numSteps - 1);
				fairness = Simulator.getFairness(currentConsumed,
						currentDonated);
				writer.append(fairness + "");
				writer.append(',');

				// satisfaction
				double currentRequested = c.getCurrentRequested(numSteps - 1);
				double satisfaction = Simulator.getFairness(currentConsumed,
						currentRequested);
				writer.append(satisfaction + "");
				writer.append(',');

				// kappa
				writer.append(this.sim.getKappa() + "");
				writer.append(',');

				// f
				writer.append(this.sim.getF());
				writer.append(',');

				// nof
				writer.append((this.sim.isPairwise() ? "FD-NoF" : "SD-NoF")
						+ "");
				writer.append(',');

				// tauMin
				writer.append(this.sim.getFairnessLowerThreshold() + "");
				writer.append(',');

				// tauMax
				writer.append(this.sim.getFairnessUpperThreshold() + "");
				writer.append(',');

				// delta
				writer.append(this.sim.getChangingValue() + "");
				writer.append('\n');

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while writing collaboratos...");
			}
		}

		// //collaborators
		 for(FreeRider fr : frs){
			 try {
				 
				 //p
				 writer.append(1 + "");
				 writer.append(',');
	
				 //d
				 writer.append(fr.getInitialDemand() + "");
				 writer.append(',');
			
				 //fairness
				 writer.append(-1+"");
				 writer.append(',');
			
				 //satisfaction
				 double currentConsumed = fr.getCurrentConsumed(numSteps-1);
				 double currentRequested = fr.getCurrentRequested(numSteps-1);
				 double satisfaction = Simulator.getFairness(currentConsumed,currentRequested);
				 writer.append(satisfaction+"");
				 writer.append(',');
			
				 //kappa
				 writer.append(this.sim.getKappa()+"");
				 writer.append(',');
				
				 //f
				 writer.append(this.sim.getF());
				 writer.append(',');
				
				 //nof
				 writer.append((this.sim.isPairwise()?"FD-NoF":"SD-NoF")+"");
				 writer.append(',');
			
				// tauMin
				writer.append(this.sim.getFairnessLowerThreshold() + "");
				writer.append(',');
	
				// tauMax
				writer.append(this.sim.getFairnessUpperThreshold() + "");
				writer.append(',');
	
				// delta
				writer.append(this.sim.getChangingValue() + "");
				writer.append('\n');
			
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 System.out.println("Exception while writing free riders...");
			 }
		}

		return writer;
	}

	private FileWriter createHeaderForCollaborator() {

		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile + "Collaborator.csv");
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

	private FileWriter createHeaderForCapacitySupplied() {

		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile + "CapacitySupplied.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.append("demand");
			for (int i = 1; i <= numSteps; i++) {
				writer.append(',');
				writer.append(i + "");
			}
			writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer;

	}

	private FileWriter writeCollaborators(FileWriter writer) {
		for (int i = 0; i < sim.getPeers().length; i++) {
			if (sim.getPeers()[i] instanceof Collaborator) {

				try {

					// fairness
					Collaborator collab = (Collaborator) sim.getPeers()[i];
					double currentConsumed, currentDonated, fairness;
					currentConsumed = collab.getCurrentConsumed(numSteps - 1);
					currentDonated = collab.getCurrentDonated(numSteps - 1);
					fairness = Simulator.getFairness(currentConsumed,
							currentDonated);
					writer.append(fairness + "");
					writer.append(',');

					// satisfaction
					double currentRequested = sim.getPeers()[i]
							.getCurrentRequested(numSteps - 1);
					double satisfaction = Simulator.getFairness(
							currentConsumed, currentRequested);
					writer.append(satisfaction + "");
					writer.append(',');

					// //index
					// writer.append(Simulator.peers[i].getIndex()+"");
					// writer.append(',');

					// demand
					writer.append(sim.getPeers()[i].getInitialDemand() + "");
					writer.append(',');

					// kappa
					writer.append(this.sim.getKappa() + "");
					writer.append(',');

					// design
					writer.append(this.sim.getDesign() + "");
					writer.append(',');

					// nof
					writer.append((this.sim.isPairwise() ? "FD-NoF" : "SD-NoF")
							+ "");
					writer.append('\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return writer;
	}

	private FileWriter writeCapacitySupplied(FileWriter writer) {
		for (int i = 0; i < sim.getPeers().length; i++) {
			if (sim.getPeers()[i] instanceof Collaborator) {

				try {
					double demandRatio = (sim.getPeers()[i].getInitialDemand() - sim
							.getPeers()[i].getInitialCapacity())
							/ sim.getPeers()[i].getInitialCapacity();
					writer.append(demandRatio + "C");

					Collaborator c = (Collaborator) sim.getPeers()[i];
					for (int j = 0; j < numSteps; j++) {
						writer.append(',');
						writer.append(c.getCapacitySuppliedHistory()[j] + "");
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

	private void flushFile(FileWriter writer) {

		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void outputFreeRiders(){
	// FileWriter writer = this.createHeaderForFreeRider();
	// writer = this.writeFreeRiders(writer);
	// this.flushFile(writer);
	// }

	// private FileWriter createHeaderForFreeRider(){
	//
	// FileWriter writer = null;
	// try {
	// writer = new FileWriter(this.outputFile+"Freerider.csv");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// try {
	// writer.append("satisfaction");
	// writer.append(',');
	// writer.append("index");
	// writer.append(',');
	// writer.append("demand");
	// writer.append(',');
	// writer.append("kappa");
	// writer.append(',');
	// writer.append("design");
	// writer.append(',');
	// writer.append("NoF");
	// writer.append('\n');
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return writer;
	//
	// }

	// private FileWriter writeFreeRiders(FileWriter writer){
	// for (int i = 0; i < Simulator.peers.length; i++) {
	// if(Simulator.peers[i] instanceof FreeRider){
	//
	// try {
	//
	// //satisfaction
	// FreeRider freeRider = (FreeRider) Simulator.peers[i];
	// double currentConsumed = freeRider.getCurrentConsumed(numSteps-1);
	// double currentRequested =
	// Simulator.peers[i].getCurrentRequested(numSteps-1);
	// double satisfaction = Simulator.getFairness(currentConsumed,
	// currentRequested);
	// writer.append(satisfaction+"");
	// writer.append(',');
	//
	// //index
	// writer.append(Simulator.peers[i].getIndex()+"");
	// writer.append(',');
	//
	// //demand
	// writer.append(Simulator.peers[i].getInitialDemand()+"");
	// writer.append(',');
	//
	// //kappa
	// writer.append(this.sim.getKappa()+"");
	// writer.append(',');
	//
	// //design
	// writer.append(this.sim.getDesign()+"");
	// writer.append(',');
	//
	// //nof
	// writer.append((this.sim.isPairwise()?"FD-NoF":"SD-NoF")+"");
	// writer.append('\n');
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// return writer;
	// }

	private FileWriter writeFreeRiders(FileWriter writer) {

		List<Collaborator> collabs = new ArrayList<Collaborator>();
		List<FreeRider> frs = new ArrayList<FreeRider>();

		for (int i = 0; i < sim.getPeers().length; i++) {
			if (sim.getPeers()[i] instanceof Collaborator)
				collabs.add((Collaborator) sim.getPeers()[i]);
			else if (sim.getPeers()[i] instanceof FreeRider)
				frs.add((FreeRider) sim.getPeers()[i]);
		}

		double[] consumedByCollaborators = new double[numSteps];
		double[] donatedToEveryone = new double[numSteps];
		double[] welfare = new double[numSteps];

		for (int turn = 0; turn < numSteps; turn++) {
			for (Collaborator c : collabs) {
				consumedByCollaborators[turn] += c.getCurrentConsumed(turn);
				donatedToEveryone[turn] += c.getCurrentDonated(turn);
			}
			double fairnessOfCollaborators = consumedByCollaborators[turn]
					/ donatedToEveryone[turn];
			welfare[turn] = 1 - fairnessOfCollaborators;
		}

		for (int turn = 0; turn < numSteps; turn++) {
			try {
				// turn
				writer.append((turn + 1) + "");
				writer.append(',');

				// welfareRA ==> welfareRunningAverage
				double welfareRunningAverage = 0;
				for (int i = turn; i >= Math.max(turn - 499, 0); i--) {
					welfareRunningAverage += welfare[i];
				}
				welfareRunningAverage = welfareRunningAverage
						/ Math.min(turn + 1, 500);

				writer.append((welfareRunningAverage) + "");
				writer.append(',');

				writer.append((welfare[turn]) + "");
				writer.append(',');

				// kappa
				writer.append(this.sim.getKappa() + "");
				writer.append(',');

				// kappaRA == requested/supplied
				double kappaRunningAverage = 0;
				double requested = 0, supplied = 0;
				for (int i = turn; i >= Math.max(turn - 499, 0); i--) {
					for (Collaborator c : collabs) {
						requested += c.getRequestedHistory()[i];
						supplied += c.getDonatedHistory()[i]; // now
																// collaborators
																// donate
																// everything,
																// then,
																// supplied ==
																// donated
					}
				}
				kappaRunningAverage = (requested / (Math.min(turn + 1, 500)))
						/ (supplied / (Math.min(turn + 1, 500)));
				writer.append(kappaRunningAverage + "");
				writer.append(',');

				// f
				writer.append(this.sim.getF());
				writer.append(',');

				// nof
				writer.append((this.sim.isPairwise() ? "FD-NoF" : "SD-NoF")
						+ "");
				writer.append(',');

				// tauMin
				writer.append(this.sim.getFairnessLowerThreshold() + "");
				writer.append(',');

				// tauMax
				writer.append(this.sim.getFairnessUpperThreshold() + "");
				writer.append(',');

				// delta
				writer.append(this.sim.getChangingValue() + "");
				writer.append('\n');

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while writing free riders...");
			}
		}

		return writer;
	}

	public void outputSharingLevel() {
		FileWriter writer = this.createHeaderForSharingLevel();
		writer = writeSharingLevel(writer);
		flushFile(writer);
	}

	private FileWriter createHeaderForSharingLevel() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFile+"-SL.csv");
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
			writer.append('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}

		return writer;
	}

	private FileWriter writeSharingLevel(FileWriter writer) {

		String nof = (this.sim.isPairwise() ? "FD-NoF" : "SD-NoF");
		double tMin = sim.getFairnessLowerThreshold();
		double tMax = sim.getFairnessUpperThreshold();
		double delta = sim.getChangingValue();

		for (int step = 0; step < sim.getNumSteps(); step++) {
			double totalCapacity = 0, providedToCollab = 0, providedToFR = 0;
			for (Peer p : sim.getPeers()) {
				if (p instanceof Collaborator
						&& p.getStateHistory()[step] == State.PROVIDING) {
					totalCapacity += p.getInitialCapacity();
					providedToFR += p.getDonatedToFreeRidersHistory()[step];
					providedToCollab += ((Collaborator)p).getDonatedHistory()[step]
							- p.getDonatedToFreeRidersHistory()[step];
				}
			}
			try {
				writer.append((step + 1) + ","
						+ (providedToCollab / totalCapacity) + ","
						+ (providedToFR / totalCapacity) + ","
						+ ((providedToCollab + providedToFR) / totalCapacity)
						+ "," + nof + "," + tMin + "," + tMax + "," + delta + "\n");
			} catch (IOException e) {
				Simulator.logger
						.finest("Exception while writing to output (csv) the sharing level of the federation.");
				e.printStackTrace();
			}
		}

		return writer;
	}

}
