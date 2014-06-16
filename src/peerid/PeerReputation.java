package peerid;

public class PeerReputation implements Comparable<Object>{

	private int id;
	private double reputation;
	
	
	
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



	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
	    if(!(o instanceof PeerReputation))
			return EQUAL;
		
		PeerReputation otherPeer = (PeerReputation) o;
		
		if (this.reputation < otherPeer.getReputation()) 
	    	return BEFORE;
		else	// (myReputation >= hisReputation) 
	    	return AFTER;
	}

}
