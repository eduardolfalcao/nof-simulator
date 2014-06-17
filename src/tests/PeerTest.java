/**
 * 
 */
package tests;

import static org.junit.Assert.*;
import nof.Interaction;
import nof.NetworkOfFavors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import peer.Peer;
import peer.peerid.PeerReputation;

/**
 * @author eduardolfalcao
 *
 */
public class PeerTest {

	/**
	 * Test method for {@link peer.Peer#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		
		Peer p1 = new Peer(100, 1);
		Peer p2 = new Peer(100, 2);
		Peer p3 = new Peer(100, 1);
		Peer p4 = null;
		
		assertFalse(p1.equals(p2));		//when they're different
		assertTrue(p1.equals(p3));		//when they're equals
		assertTrue(p3.equals(p1));		//when they're equals
		assertFalse(p1.equals(p4));		//when one peer is null
	}

	/**
	 * Test method for {@link peer.Peer#getThePeerWithNthBestReputation(int)}.
	 */
	@Test
	public void testGetThePeerWithNthBestReputation() {
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation is empty.
		 */
		
		Peer p1 = new Peer(100,1);
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==-1?true:false);
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==-1?true:false);
		assertTrue(p1.getThePeerIdWithNthBestReputation(10)==-1?true:false);
		
		/****************** END OF THIS CASE ******************/
		
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 1 peer.
		 */
		
		Peer p2 = new Peer(100, 2);
		Interaction interactionP1P2 = new Interaction(p1, p2);
		interactionP1P2.peerADonatesValue(50);
		p1.getPeersReputations().add(new PeerReputation(p2.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P2.getConsumedValueByPeerA(), interactionP1P2.getDonatedValueByPeerA(), true)));
		p2.getPeersReputations().add(new PeerReputation(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P2.getConsumedValueByPeerB(), interactionP1P2.getDonatedValueByPeerB(), true)));
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==2);	
		assertTrue(p2.getThePeerIdWithNthBestReputation(1)==1);
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p1.getThePeerIdWithNthBestReputation(10)==-1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(10)==-1);
		
		/****************** END OF THIS CASE ******************/
		
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 2 peers.
		 */
		
		Peer p3 = new Peer(100, 3);
		Interaction interactionP1P3 = new Interaction(p1, p3);
		interactionP1P3.peerBDonatesValue(25);
		p1.getPeersReputations().add(new PeerReputation(p3.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P3.getConsumedValueByPeerA(), interactionP1P3.getDonatedValueByPeerA(), true)));
		p3.getPeersReputations().add(new PeerReputation(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P3.getConsumedValueByPeerB(), interactionP1P3.getDonatedValueByPeerB(), true)));
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==3);
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==2);
		assertTrue(p2.getThePeerIdWithNthBestReputation(1)==1);
		assertTrue(p3.getThePeerIdWithNthBestReputation(1)==1);
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(3)==-1);
		assertTrue(p1.getThePeerIdWithNthBestReputation(10)==-1);
		
		assertTrue(p2.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(10)==-1);
		
		assertTrue(p3.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p3.getThePeerIdWithNthBestReputation(10)==-1);
		
		/****************** END OF THIS CASE ******************/
		
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 5 peers.
		 */
		
		Peer p4 = new Peer(100, 4);
		Interaction interactionP1P4 = new Interaction(p1, p4);
		interactionP1P4.peerBDonatesValue(10);
		p1.getPeersReputations().add(new PeerReputation(p4.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P4.getConsumedValueByPeerA(), interactionP1P4.getDonatedValueByPeerA(), true)));
		p4.getPeersReputations().add(new PeerReputation(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P4.getConsumedValueByPeerB(), interactionP1P4.getDonatedValueByPeerB(), true)));
		
		Peer p5 = new Peer(100, 5);
		Interaction interactionP1P5 = new Interaction(p1, p5);
		interactionP1P5.peerBDonatesValue(50);
		p1.getPeersReputations().add(new PeerReputation(p5.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P5.getConsumedValueByPeerA(), interactionP1P5.getDonatedValueByPeerA(), true)));
		p5.getPeersReputations().add(new PeerReputation(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P5.getConsumedValueByPeerB(), interactionP1P5.getDonatedValueByPeerB(), true)));
		
		Peer p6 = new Peer(100, 6);
		Interaction interactionP1P6 = new Interaction(p1, p6);
		interactionP1P6.peerADonatesValue(75);
		p1.getPeersReputations().add(new PeerReputation(p6.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P6.getConsumedValueByPeerA(), interactionP1P6.getDonatedValueByPeerA(), true)));
		p6.getPeersReputations().add(new PeerReputation(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P6.getConsumedValueByPeerB(), interactionP1P6.getDonatedValueByPeerB(), true)));
		
		
		/**
		 * Peer1 reputation (descending order): P5(+50), P3(+25), P4(+10), P6(0), P2(0)
		 * 
		 * Peer 5. Rep: 53.912023005428146
		 * Peer 3. Rep: 28.2188758248682
		 * Peer 4. Rep: 12.302585092994047
		 * Peer 2. Rep: 0.0
		 * Peer 6. Rep: 0.0
		 */
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==5);
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==3);
		assertTrue(p1.getThePeerIdWithNthBestReputation(3)==4);
		int idRep4 = p1.getThePeerIdWithNthBestReputation(4);		//when we have many id with same reputation the call method might return them randomly
		assertTrue(idRep4==6 || idRep4==2);
		int idRep5 = p1.getThePeerIdWithNthBestReputation(5);		//when we have many id with same reputation the call method might return them randomly
		assertTrue(idRep5==2 || idRep5==6);
		assertTrue(p1.getThePeerIdWithNthBestReputation(6)==-1);	//only 5 peers
		assertTrue(p1.getThePeerIdWithNthBestReputation(10)==-1);
		
		
		/**
		 * Peer2 reputation (descending order): P1(+50)
		 */
		assertTrue(p2.getThePeerIdWithNthBestReputation(1)==1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(10)==-1);
		
		/**
		 * Peer3 reputation (descending order): P1(0)
		 */
		assertTrue(p3.getThePeerIdWithNthBestReputation(1)==1);
		assertTrue(p3.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p3.getThePeerIdWithNthBestReputation(10)==-1);
		
		/**
		 * Peer4 reputation (descending order): P1(0)
		 */
		assertTrue(p4.getThePeerIdWithNthBestReputation(1)==1);
		assertTrue(p4.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p4.getThePeerIdWithNthBestReputation(10)==-1);
		
		/**
		 * Peer5 reputation (descending order): P1(0)
		 */
		assertTrue(p5.getThePeerIdWithNthBestReputation(1)==1);
		assertTrue(p5.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p5.getThePeerIdWithNthBestReputation(10)==-1);

		/**
		 * Peer6 reputation (descending order): P1(+75)
		 */
		assertTrue(p5.getThePeerIdWithNthBestReputation(1)==1);
		assertTrue(p5.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p5.getThePeerIdWithNthBestReputation(10)==-1);
		
		
		/****************** END OF THIS CASE ******************/
		
		
	}

}
