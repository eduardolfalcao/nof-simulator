package model.peer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.event.Event;
import model.peer.history.History;
import model.peer.history.Interaction;
import model.peer.history.PeerInfo;
import model.peer.history.PeerRelations;
import nof.NetworkOfFavors;

public class Peer{
	
	private String id;
	
	private final int TOTAL_CAPACITY;	
	private HashMap<Peer, Integer> donating;
	private List<Event> donationEvents;
	
	private NetworkOfFavors nof;
	private PeerRelations relations;
	private History history;
	
	public Peer(String id, int totalCapacity, 
			NetworkOfFavors nof, History history) {
		super();
		this.id = id;
		TOTAL_CAPACITY = totalCapacity;
		donating = new HashMap<Peer, Integer>();
		donationEvents = new ArrayList<Event>();
		nof = new NetworkOfFavors(totalCapacity);
		history = new History();
	}
	
	/**
	 * id is unique
	 */
	public int hashCode() {
       return this.id.hashCode();
   }
	
	/**
	 * @return true if they have the same id, false otherwise
	 */
	public boolean equals(Object obj) {
	       if (obj == null || !(obj instanceof Peer))
	            return false;
	       else{
	    	   Peer p = (Peer) obj;
	    	   if(this.id.equals(p.getId()))
	    		   return true;
	    	   else
	    		   return false;
	       }
	}

	public String getId() {
		return this.id;
	}

	public int getTotalCapacity() {
		return TOTAL_CAPACITY;
	}

	public HashMap<Peer, Integer> getDonating() {
		return donating;
	}

	public List<Event> getDonationEvents() {
		return donationEvents;
	}

	public NetworkOfFavors getNof() {
		return nof;
	}

	public PeerRelations getRelations() {
		return relations;
	}

	public History getHistory() {
		return history;
	}
	
	
	
	
	
//	protected final int INITIAL_CAPACITY;
//	
//	protected int currentCapacity;
//	protected int currentConsumedFromFederation, currentDonatedToFederation, currentDonatedToLocalUsers;
//	
//	protected String id;
//

//	
//	private double consumedHistory[], donatedHistory[], donatedToFreeRidersHistory[], requestedHistory[], capacitySuppliedHistory[];
//	
//	public Peer(String id, int initialCapacity, int numSteps) {
//		super();
//		this.INITIAL_CAPACITY = currentCapacity = initialCapacity;
//		this.id = id;
//		
//		currentConsumedFromFederation = currentDonatedToFederation = currentDonatedToLocalUsers = 0;
//		
//		balances = new ArrayList<PeerInfo>();
//		interactions = new ArrayList<Interaction>();
//		
//		consumedHistory = new double[numSteps];
//		donatedHistory = new double[numSteps];
//		donatedToFreeRidersHistory = new double[numSteps];
//		requestedHistory = new double[numSteps];
//		capacitySuppliedHistory = new double[numSteps];
//	}
//	

//	

//	
//	public void setId(String id){
//		this.id = id;
//	}
//	
//	
//
//	public void setPeersReputations(ArrayList<PeerInfo> peersReputations) {
//		this.balances = peersReputations;
//	}
//	
	

//
//	private void setInteractions(ArrayList<Interaction> interactions) {
//		this.interactions = interactions;
//	}
//	
//	public double[] getConsumedHistory() {
//		return consumedHistory;
//	}
//	
//	public double getCurrentConsumed(int step) {
//		double currrentConsumed = 0;
//		for(int i = 0; i <= step; i++)
//			currrentConsumed += consumedHistory[i];
//		return currrentConsumed;
//	}
//	
//	public double getCurrentConsumed(int beginning, int end) {
//		double currrentConsumed = 0;
//		for(int i = beginning; i <= end; i++)
//			currrentConsumed += consumedHistory[i];
//		return currrentConsumed;
//	}
//	
////	public double[] getConsumedByTransitivityHistory() {
////		return consumedByTransitivityHistory;
////	}
////	
////	public double getCurrentConsumedByTransitivity(int step) {
////		double currrentConsumed = 0;
////		for(int i = 0; i <= step; i++)
////			currrentConsumed += consumedByTransitivityHistory[i];
////		return currrentConsumed;
////	}
////	
////	public double getCurrentConsumedByTransitivity(int beginning, int end) {
////		double currrentConsumed = 0;
////		for(int i = beginning; i <= end; i++)
////			currrentConsumed += consumedByTransitivityHistory[i];
////		return currrentConsumed;
////	}
//	
//	public double[] getDonatedHistory() {
//		return donatedHistory;
//	}
//	
//	public double getCurrentDonated(int step) {
//		double currrentDonated = 0;
//		for(int i = 0; i <= step; i++)
//			currrentDonated += donatedHistory[i];
//		return currrentDonated;
//	}
//	
//	public double getCurrentDonated(int beginning, int end) {
//		double currrentDonated = 0;
//		for(int i = beginning; i <= end; i++)
//			currrentDonated += donatedHistory[i];
//		return currrentDonated;
//	}
//	
////	public double[] getDonatedByTransitivityHistory() {
////		return donatedByTransitivityHistory;
////	}
////	
////	public double getCurrentDonatedByTransitivity(int step) {
////		double currrentDonated = 0;
////		for(int i = 0; i <= step; i++)
////			currrentDonated += donatedByTransitivityHistory[i];
////		return currrentDonated;
////	}
////	
////	public double getCurrentDonatedByTransitivity(int beginning, int end) {
////		double currrentDonated = 0;
////		for(int i = beginning; i <= end; i++)
////			currrentDonated += donatedByTransitivityHistory[i];
////		return currrentDonated;
////	}
//	
//	public double[] getDonatedToFreeRidersHistory() {
//		return donatedToFreeRidersHistory;
//	}
//	
//	public double getCurrentDonatedToFreeRiders(int step) {
//		double currrentDonated = 0;
//		for(int i = 0; i <= step; i++)
//			currrentDonated += donatedToFreeRidersHistory[i];
//		return currrentDonated;
//	}
//	
//	public double [] getRequestedHistory() {
//		return requestedHistory;
//	}
//	
//	public double getCurrentRequested(int step) {
//		double currrentRequested = 0;
//		for(int i = 0; i <= step; i++)
//			currrentRequested += requestedHistory[i];
//		return currrentRequested;
//	}
//	
//	public double[] getCapacitySuppliedHistory() {
//		return capacitySuppliedHistory;
//	}
//	
//	public int getCurrentConsumedFromFederation() {
//		return currentConsumedFromFederation;
//	}
//
//	public void setCurrentConsumedFromFederation(int currentConsumedFromFederation) {
//		this.currentConsumedFromFederation = currentConsumedFromFederation;
//	}
//
//	public int getCurrentDonatedToFederation() {
//		return currentDonatedToFederation;
//	}
//
//	public void setCurrentDonatedToFederation(int currentDonatedToFederation) {
//		this.currentDonatedToFederation = currentDonatedToFederation;
//	}
//
//	public int getCurrentDonatedToLocalUsers() {
//		return currentDonatedToLocalUsers;
//	}
//
//	public void setCurrentDonatedToLocalUsers(int currentDonatedToLocalUsers) {
//		this.currentDonatedToLocalUsers = currentDonatedToLocalUsers;
//	}
//	
//	public int getCurrentCapacity() {
//		return currentCapacity;
//	}
//
//	public void setCurrentCapacity(int currentCapacity) {
//		this.currentCapacity = currentCapacity;
//	}
		
}