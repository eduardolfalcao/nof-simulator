package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import peer.Collaborator;
import peer.Peer;
import peer.State;
import peer.balance.PeerInfo;

public class MemberPicker {
	
	private Random randomGenerator;
	
	public MemberPicker(int seed){
		randomGenerator = new Random(seed);
	}	
	
	/**
	 * Randomly chooses an item of the list.
	 * @param peerList	 the list of integers (peer ids) on which we will randomly choose one
	 * @return the index of item randomly chosen
	 */
    private int anyPeer(List <Integer> peerList){    
        return this.randomGenerator.nextInt(peerList.size());
    }
	
	/**
	 * @param peerList	a list of integers
	 * @return the peer/collaborator randomly chosen, or null if something strange happens
	 */
	public Collaborator choosesRandomPeer(List <Integer> peerList){
		int id = peerList.get(anyPeer(peerList));
		return PeerComunity.peers[id] instanceof Collaborator?(Collaborator)PeerComunity.peers[id]:null;
	}
	
	//chooses a peer with balance > 0 to consume resources.
	public Peer choosesConsumerWithPositiveBalance(Collaborator provider, List <Integer> consumersList, List <Integer> alreadyConsumed){		
			
		List <Integer> consumingPeers = new ArrayList<Integer>();			//consuming peers that didn't consume
		consumingPeers.addAll(consumersList);							
		
		List <Integer> peersWithPositiveBalance = new ArrayList<Integer>();	//peers with balance>0 relative to the collaborator
		Collections.sort(provider.getBalances());							//assure the balance order
		for(int i = provider.getBalances().size()-1; i >= 0 ; i--){
			PeerInfo balance = provider.getBalances().get(i);
			if(balance.getBalance()>0 && !(alreadyConsumed.contains(balance.getId())))
				peersWithPositiveBalance.add(provider.getBalances().get(i).getId());
		}
		
		Simulator.logger.finest("Peers Balances\n");
		for (PeerInfo peerInfo : provider.getBalances()) 
			Simulator.logger.finest("Id: "+peerInfo.getId()+"; Balance: "+peerInfo.getBalance());
		Simulator.logger.finest("Peers With Balance>0 \n");
		Simulator.logger.finest(peersWithPositiveBalance.toString());
				
		for (Integer peerId : peersWithPositiveBalance) {
			if(consumingPeers.contains(peerId) && PeerComunity.peers[peerId].getState() == State.CONSUMING)
				return PeerComunity.peers[peerId];
		}
		
		//If we got here, there is no collaborator willing to consume with balance > 0, then return null
			return null;		
	}

}
