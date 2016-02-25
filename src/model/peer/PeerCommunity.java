package model.peer;

import java.util.ArrayList;
import java.util.List;

public class PeerCommunity {
	
	private List<Peer> peers;	
	private static PeerCommunity uniqueInstance;
	
	private PeerCommunity(){
	}
	
	public static PeerCommunity getInstance(){
		if(uniqueInstance==null)
			uniqueInstance = new PeerCommunity();
		return uniqueInstance;
	}
	
	public List<Peer> getPeers(){
		return peers;
	}
	
	public void populate(int numPeers, int capacity){
		if(peers==null){
			peers = new ArrayList<Peer>();
			for(int i = 1; i<=numPeers; i++)
				peers.add(new Peer("P"+i, capacity));
		}
	}
	
	public Peer getPeer(String id){
		for(Peer peer : peers){
			if(peer.getId().equals(id))
				return peer;
		}
		return null;
	}

}
