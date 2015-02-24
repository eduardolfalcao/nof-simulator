import input.PeerGroup;


public class Main {
	
	public static void main(String [] args){
		
	
		PeerGroup group1 = new PeerGroup(new char [] {'A'}, new char [] {'B'}, 50);
		PeerGroup group2 = new PeerGroup(new char [] {'B'}, new char [] {'C'}, 50);
		PeerGroup group3 = new PeerGroup(new char [] {'C'}, new char [] {'A'}, 50);
		
		
		PeerGroup [] peerGroups = new PeerGroup [] {group1, group2, group3}; 
		
	}
	

}
