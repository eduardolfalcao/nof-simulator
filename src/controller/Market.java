package controller;

import java.util.List;
import java.util.Map.Entry;

import model.event.BarterEvent;
import model.peer.Peer;
import model.peer.PeerCommunity;

public class Market {
	
	public static void consume(BarterEvent event){
		
		List<Peer> peers = PeerCommunity.getInstance().getPeers();
		
		int index = peers.lastIndexOf(new Peer(event.getPeerId(),0));
		Peer consumer = peers.get(index);
		
		//first, check if the peer can consume locally without preemption
		if(areThereFreeLocalResources(consumer)){
			consume(consumer,consumer,event);
			return;
		}
		//later, check if the peer can consume locally by preempting a peer/user from federation
		else if(areThereLocalResourcesBeingDonatedToTheFederation(consumer)){
			//TODO //execute preemption
			consume(consumer,consumer,event);
			return;
		}
		//if the peer can't consume locally, it tries to consume from federation
		else{
			Peer provider = MemberPicker.findProvider(consumer);
			if(provider != null)
				consume(consumer, provider, event);
		}
		
	}
	
	private static void consume(Peer consumer, Peer provider, BarterEvent event){
		//increment amount of resources donated
		provider.getDonating().put(consumer, (provider.getDonating().get(consumer))+1);
		//add event to the list of ongoing events
		provider.getDonationEvents().add(event);
	}
	
	public static boolean areThereFreeLocalResources(Peer provider){
		int donating = 0;
		for(Entry<Peer, Integer> entry : provider.getDonating().entrySet())
			donating += entry.getValue();
		
		if(donating<provider.getTotalCapacity())		
			return true;
		else
			return false;
		
	}
	
	private static boolean areThereLocalResourcesBeingDonatedToTheFederation(Peer provider){
		int donatingToFederation = 0;
		for(Entry<Peer, Integer> entry : provider.getDonating().entrySet()){
			if(!(entry.getKey().equals(provider)))
				donatingToFederation += entry.getValue();
		}
		
		if(donatingToFederation>0)
			return true;
		else
			return false;
	}

}
