package model.event;

import java.util.Collections;
import java.util.List;

import model.peer.Peer;

public class NoFEvent extends Event{

	public NoFEvent(String peerId, int submitTime) {
		super(peerId, submitTime);
		// TODO Auto-generated constructor stub
	}
	
	public static List<Event> generateNoFEvents(List<Event> events, List<Peer> peers, int grainTime, int endTime){
		for(int i = grainTime; i <= endTime; i+=grainTime){
			for(Peer peer : peers)
				events.add(new NoFEvent(peer.getId(), i));
		}
		Collections.sort(events);
		return events;
	}
	
}
