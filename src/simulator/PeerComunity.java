package simulator;

import java.util.ArrayList;
import java.util.Queue;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import peer.PeerGroup;
import peer.State;

public class PeerComunity {
	
	public static Peer peers[];
	private Queue<PeerGroup> groupsOfPeers;	
	private int numSteps;
	
	private int numCollaborators, numFreeRiders;
	
	public PeerComunity(Queue<PeerGroup> groupsOfPeers, int numSteps){
		
		this.groupsOfPeers = groupsOfPeers;	
		
		numCollaborators = numFreeRiders = 0;
		
		int numPeers = 0;
		for(PeerGroup pg : groupsOfPeers){
			numPeers += pg.getNumPeers();
			if(pg.isFreeRider())
				numFreeRiders += pg.getNumPeers();
			else
				numCollaborators += pg.getNumPeers();
		}		
		PeerComunity.peers = new Peer[numPeers];
		
		this.numSteps = numSteps;
		createPeers();
	}	
	
	private void createPeers(){		
		int index = 0, numberOfGroups = groupsOfPeers.size();
		int id=0;
		
		while(index < numberOfGroups){			
			PeerGroup group = groupsOfPeers.poll();	//with the poll we remove it from the queue, so we can access the next element
			groupsOfPeers.add(group);				//then, we add it back on the queue, now, on its tails			
			for(int i = 0; i < group.getNumPeers(); i++, id++){
				State state = null;
				Peer p = null;
				if(!group.isFreeRider())
					p = new Collaborator(id, group.getCapacity(), group.getDemand(), state, group.getGroupId(), group.getDeviation(), numSteps);
				else
					p = new FreeRider(id, group.getCapacity(), group.getDemand(), State.CONSUMING, group.getGroupId(), group.getDeviation(), numSteps);
				PeerComunity.peers[id] = p;	
			}		
			index++;
		}	
		
		for(Peer p : PeerComunity.peers)
			Simulator.logger.finest("Id: "+p.getId()+"; InitialDemand: "+p.getInitialDemand()+"; Demand: "+p.getDemand()+"InitialCapacity: "+p.getInitialCapacity());
	}
		
	public Queue<PeerGroup> getGroupsOfPeers(){
		return groupsOfPeers;
	}	

	public int getNumFreeRiders() {
		return numFreeRiders;
	}

	public int getNumCollaborators() {
		return numCollaborators;
	}
}