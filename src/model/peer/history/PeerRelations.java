package model.peer.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeerRelations {

	private ArrayList<PeerInfo> balances;
	private ArrayList<Interaction> interactions;

	public PeerRelations() {
		super();
		this.balances = new ArrayList<PeerInfo>();
		this.interactions = new ArrayList<Interaction>();
	}

	public ArrayList<PeerInfo> getBalances() {
		return balances;
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}
	
	public PeerInfo getBalance(String peerId) {
		for(PeerInfo pi : balances)
			if(pi.getId().equals(peerId))
				return pi;
		return null;
	}

	/**
	 * The peer with highest balance might already been used. Therefore, we will
	 * seek the higher value before this. Where attempt = 2, means the second
	 * higher value in the SortedList, and so on.
	 * 
	 * @param nth
	 *            the nth highest balance
	 * @return the peer id with the nth highest balance, or null in case it
	 *         doesnt exist
	 */
	public String getThePeerIdWithNthBestBalance(int nth) {
		if (nth <= 0 || balances.size() == 0)
			return null;

		// sort the balance
		Collections.sort(balances);
		return this.balances.get(this.balances.size()-nth).getId();
	}

}
