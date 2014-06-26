package peer.peerid;

import java.util.Comparator;

public class PeerReputationComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
	    if(!(o1 instanceof PeerReputation) || !(o2 instanceof PeerReputation))
			return EQUAL;
		
	    PeerReputation peer = (PeerReputation) o1;
		PeerReputation otherPeer = (PeerReputation) o2;
		
		if (peer.getReputation() < otherPeer.getReputation()) 
	    	return BEFORE;
		else if(peer.getReputation() == otherPeer.getReputation())
			return EQUAL;
		else	// (myReputation >= hisReputation) 
	    	return AFTER;
	}
	
	

}
