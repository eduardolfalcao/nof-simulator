package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import model.peer.Peer;
import model.peer.PeerCommunity;
import model.peer.history.PeerInfo;

public class MemberPicker {
	
	public static Peer findProvider(Peer consumer){
		
		List<Peer> peers = PeerCommunity.getInstance().getPeers();
		
		//chose peer with the lowest balances first, because they might have more debt
		Collections.sort(consumer.getRelations().getBalances());
		for(PeerInfo balance : consumer.getRelations().getBalances()){
			int indexProvider = peers.indexOf(new Peer(balance.getId(), 0));
			Peer provider = peers.get(indexProvider);
			
			if(Market.areThereFreeLocalResources(provider))
				return provider;			
			else if(hasPreemptableResources(consumer, provider)){
				Peer peerToBePreempted = discoverPeerToPreempt(consumer, provider);
				//TODO run preemption
				return provider;
			}
		}
		
		return null;		
	}
	
	public static boolean hasPreemptableResources(Peer consumer, Peer provider){
		
		List<Peer> peersWithLowerPriority = new ArrayList<Peer>();
		
		double consumerBalance = (provider.getRelations().getBalance(consumer.getId())).getBalance();
		for(PeerInfo pi : provider.getRelations().getBalances()){
			if(pi.getBalance()<consumerBalance)
				peersWithLowerPriority.add(PeerCommunity.getInstance().getPeer(pi.getId()));
		}
		
		for(Entry<Peer, Integer> entry : provider.getDonating().entrySet()){
			for(Peer peer : peersWithLowerPriority){
				if(peer.equals(entry.getKey()))
					return true;
			}
		}
		
		return false;		
	}
	
	public static Peer discoverPeerToPreempt(Peer consumer, Peer provider){
		
		double consumerBalance = (provider.getRelations().getBalance(consumer.getId())).getBalance();
		
		Collections.sort(provider.getRelations().getBalances());
		List<PeerInfo> balancesOfProvider = provider.getRelations().getBalances();
		
		Peer peerToBePreempted = null;
		for(int i = 0; i < balancesOfProvider.size();i++){
			Peer peerAssessed = PeerCommunity.getInstance().getPeer(balancesOfProvider.get(i).getId());
			if(provider.getDonating().containsKey(peerAssessed)){	//the peer assessed is currently consuming
				if(consumerBalance < balancesOfProvider.get(i).getBalance()){
					peerToBePreempted = peerAssessed;
				}
			}			
		}		
		return peerToBePreempted;		
	}

}
