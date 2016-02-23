package controller;

import java.util.Map.Entry;

import model.event.BarterEvent;
import model.peer.Peer;

public class Market {
	
	public static void consume(BarterEvent event){
		
		int index = event.getPeers().lastIndexOf(new Peer(event.getPeerId(),0, null, null));
		Peer consumer = event.getPeers().get(index);
		
		//first, check if the peer can consume locally without preemption
		if(areThereFreeLocalResources(consumer)){
			consume(consumer,consumer,event);
			return;
		}
		//later, check if the peer can consume locally by preempting a peer/user from federation
		else if(consumeByPreemption(consumer)){
			//TODO //execute preemption
			consume(consumer,consumer,event);
			return;
		}
		//if the peer can't consume locally, it tries to consume from federation
		else{
			
		}
		
	}
	
	private static void consume(Peer consumer, Peer provider, BarterEvent event){
		//increment amount of resources donated
		provider.getDonating().put(consumer, (provider.getDonating().get(consumer))+1);
		//add event to the list of ongoing events
		provider.getDonationEvents().add(event);
	}
	
	private static boolean areThereFreeLocalResources(Peer consumer){
		int donating = 0;
		for(Entry<Peer, Integer> entry : consumer.getDonating().entrySet())
			donating += entry.getValue();
		
		if(donating<consumer.getTotalCapacity())		
			return true;
		else
			return false;
		
	}
	
	private static boolean consumeByPreemption(Peer consumer){
		int donatingToFederation = 0;
		for(Entry<Peer, Integer> entry : consumer.getDonating().entrySet()){
			if(!(entry.getKey().equals(consumer)))
				donatingToFederation += entry.getValue();
		}
		
		if(donatingToFederation>0)
			return true;
		else
			return false;
	}

}
