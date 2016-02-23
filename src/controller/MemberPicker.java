package controller;

import model.event.BarterEvent;
import model.peer.Peer;

public class MemberPicker {
	
	public static Peer findProviders(BarterEvent event){
		
		int index = event.getPeers().lastIndexOf(new Peer(event.getPeerId(),0, null, null));
		Peer consumer = event.getPeers().get(index);
		
		if(consumer.getRelations())
		
		return null;
		
	}

}
