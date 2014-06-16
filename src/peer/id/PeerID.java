package peer.id;

import peer.Peer;

public class PeerID implements Comparable{
	
	private int id;

	
	
	public PeerID(int id) {
		super();
		this.id = id;
	}	
	
	@Override
	public int compareTo(Object o) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
		if(!(o instanceof Peer))
			return EQUAL;
		
		Peer otherPeer = (Peer) o;
		
		double myReputation = otherPeer.getPeersReputations().get(this.peerId)!=null?otherPeer.getPeersReputations().get(this.peerId):0;
		double hisReputation = this.getPeersReputations().get(otherPeer.getPeerId())!=null?this.getPeersReputations().get(otherPeer.getPeerId()):0;
		
		if (myReputation < hisReputation) 
	    	return BEFORE;
		else	// (myReputation >= hisReputation) 
	    	return AFTER;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
