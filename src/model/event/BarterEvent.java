package model.event;

import java.util.List;

import model.peer.Peer;

public class BarterEvent extends Event{
	
	private final String TYPE;
	private final String CONSUMING = "CONSUMING";
	private final String ENDING_CONSUMING = "ENDING_CONSUMING";
	
	private String peerId;	
	private String jobId;
	private int runtime;
	
	private List<Peer> peers;

	public BarterEvent(String peerId, String jobId, int submitTime, int runtime, boolean starting, List<Peer> peers) {
		super(submitTime);
		this.peerId = peerId;
		this.jobId = jobId;
		this.runtime = runtime;
		TYPE = starting ? CONSUMING : ENDING_CONSUMING;
		this.peers = peers;
	}
	
	@Override
	public void run() {
		if(TYPE.equals(CONSUMING))
			runStartingEvent();
		else
			runEndingEvent();
	}
	
	private void runStartingEvent(){
		
	}
	
	private void runEndingEvent(){
		
	}
	
	@Override
	public String toString() {
		return "Peer id: "+peerId+", Job id: "+jobId+", submit time: "+time+", runtime: "+runtime+", starting: "+TYPE;
	}
	
	@Override
	public int hashCode() {		
		return jobId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BarterEvent))
            return false;
       else{
    	   BarterEvent be = (BarterEvent) obj;
    	   if(this.jobId == be.getJobId())
    		   return true;
    	   else
    		   return false;
       }
	}
	
	//FIXME: do we need to generate end events on the beginning of execution?
//	public static List<Event> generateEndEvents(List<Event> events){
//		List<BarterEvent> endEvents = new ArrayList<BarterEvent>();
//		boolean starting = false;
//		for(Event event : events){
//			BarterEvent ev = (BarterEvent) event;
//			endEvents.add(new BarterEvent(ev.getPeerId(), ev.getJobId(), ev.getSubmitTime()+ev.getRuntime(), ev.getRuntime(), starting));
//		}
//		events.addAll(endEvents);
//		Collections.sort(events);
//		return events;
//	}
	
	public String getPeerId() {
		return peerId;
	}

	public int getRuntime() {
		return runtime;
	}

	public String getJobId() {
		return jobId;
	}

	public List<Peer> getPeers() {
		return peers;
	}

	

}
