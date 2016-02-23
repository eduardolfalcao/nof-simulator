package model.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.peer.Peer;

public abstract class Event implements Comparable<Event>{
	
	protected int time;
	
	public Event( int submitTime) {
		super();		
		this.time = submitTime;
	}
	
	public abstract void run();

	@Override
	public int compareTo(Event event) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
				
		if (time < event.getSubmitTime()) 
	    	return BEFORE;
		else if(time == event.getSubmitTime()){	//FIXME: which events have precedence over the others?  
//			if(this instanceof NoFEvent)
//				return BEFORE;
//			else if(this instanceof BarterEvent)
//				return AFTER;
//			else
				return EQUAL;
		}
		else	// (submitTime >= event.getSubmitTime) 
	    	return AFTER;
	}
	
	public static List<Event> readCurrentStepEvents(List<Event> events, int currentStep, int loopbackTime){
		
		int maxTime = currentStep * loopbackTime;
		List<Event> currentEvents = new ArrayList<Event>();
		for(Event event : events){
			if(event.getSubmitTime()<= maxTime)
				currentEvents.add(event);
			else
				break;
		}
		events.removeAll(currentEvents);
		Collections.sort(currentEvents);		
		return currentEvents;
	}	

	public int getSubmitTime() {
		return time;
	}
	
}
