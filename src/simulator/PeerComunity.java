package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import nof.Interaction;
import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.PeerGroup;
import peer.State;
import peer.reputation.PeerInfo;

public class PeerComunity {
	
	public static Peer peers[];
	private ArrayList<PeerGroup> groupsOfPeers;	
	private int numSteps;
	
	public PeerComunity(ArrayList<PeerGroup> groupsOfPeers, int numSteps){
		
		this.groupsOfPeers = groupsOfPeers;		
		int numPeers = 0;
		for(PeerGroup pg : groupsOfPeers)
			numPeers += pg.getNumPeers();		
		PeerComunity.peers = new Peer[numPeers];
		
		this.numSteps = numSteps;
		createPeers();
	}	
	
	private void createPeers(){		
		int id = 0;	
		//here we create the peers
		for(PeerGroup group : groupsOfPeers){
			for(int i = 0; i < group.getNumPeers(); i++, id++){
				Peer p = null;
				if(!group.isFreeRider())
					p = new Collaborator(id, group.getCapacity(), group.getDemand(),
							group.getConsumingStateProbability(), group.getIdleStateProbability(), group.getProvidingStateProbability(), numSteps);
				else
					p = new FreeRider(id, group.getCapacity(), group.getDemand(),
							group.getConsumingStateProbability(), group.getIdleStateProbability(), group.getProvidingStateProbability(), numSteps);
				
				PeerComunity.peers[id] = p;
			}
		}
	}
}