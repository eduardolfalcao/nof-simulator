package peer.reputation;


public class PeerReputation implements Comparable<Object>{

	private int id;
	private double reputation;	
	
	public PeerReputation(int id, double reputation) {
		super();
		this.id = id;
		this.reputation = reputation;
	}
	
	/**
	 * The objects are equal if they have the same peerId.
	 */
	public boolean equals(Object obj) {
	       if (obj == null || !(obj instanceof PeerReputation))
	            return false;
	       else{
	    	   PeerReputation peerRep = (PeerReputation) obj;
	    	   if(this.id == peerRep.getId())
	    		   return true;
	    	   else
	    		   return false;
	       }
	}
	
	/**
	 * The list is sorted based on peers reputations.
	 */
	@Override
	public int compareTo(Object o) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
	    if(!(o instanceof PeerReputation))
			return EQUAL;
		
		PeerReputation otherPeer = (PeerReputation) o;
		
		if (this.reputation < otherPeer.getReputation()) 
	    	return BEFORE;
		else if(this.reputation == otherPeer.getReputation())
			return EQUAL;
		else	// (myReputation >= hisReputation) 
	    	return AFTER;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getReputation() {
		return reputation;
	}

	public void setReputation(double reputation) {
		this.reputation = reputation;
	}



	

}
