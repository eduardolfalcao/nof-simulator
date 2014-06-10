package peer;

import java.util.List;
import java.util.TreeMap;

import nof.Interaction;

public class Collaborator extends Peer{
	
	
	private int returnLevelVerificationTime;	//times in steps to measure the necessity of supplying more or less resources
	private double returnLevel;				//received/donated
	private double capacitySupplied;				//capacity supplied in the current step
	


	private double changingValue;					//value added or subtracted to/from capacitySupplied
	
	
	public Collaborator(double demand, int peerId, boolean consuming, double capacitySupplied, int returnLevelVerificationTime, 
			double changingValue) {
		super(demand, peerId, consuming);
		this.capacitySupplied = capacitySupplied;
		this.returnLevelVerificationTime = returnLevelVerificationTime;		
		this.changingValue = changingValue;
	}
	
	
	public double getCapacitySupplied() {
		return capacitySupplied;
	}
	
	public void setCapacitySupplied(double capacitySupplied) {
		this.capacitySupplied = capacitySupplied;
	}

}
