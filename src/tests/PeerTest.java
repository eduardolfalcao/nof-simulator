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
import peerid.PeerReputation;

/**
 * @author eduardolfalcao
 *
 */
public class PeerTest {

//	private Peer[] peers;
//	private final int numPeers = 50;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
//		for(int i = 0; i < numPeers; i++){
//			peers[i] = new Peer(demand, i+1);
//		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

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
		p1.getPeersReputations().put(new PeerReputation(p2.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P2.getConsumedValueByPeerA(), interactionP1P2.getDonatedValueByPeerA(), true)));
		p2.getPeersReputations().put(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P2.getConsumedValueByPeerB(), interactionP1P2.getDonatedValueByPeerB(), true));
		
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
		p1.getPeersReputations().put(p3.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P3.getConsumedValueByPeerA(), interactionP1P3.getDonatedValueByPeerA(), true));
		p3.getPeersReputations().put(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P3.getConsumedValueByPeerB(), interactionP1P3.getDonatedValueByPeerB(), true));
		
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
		p1.getPeersReputations().put(p4.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P4.getConsumedValueByPeerA(), interactionP1P4.getDonatedValueByPeerA(), true));
		p4.getPeersReputations().put(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P4.getConsumedValueByPeerB(), interactionP1P4.getDonatedValueByPeerB(), true));
		
		Peer p5 = new Peer(100, 5);
		Interaction interactionP1P5 = new Interaction(p1, p5);
		interactionP1P5.peerBDonatesValue(50);
		p1.getPeersReputations().put(p5.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P5.getConsumedValueByPeerA(), interactionP1P5.getDonatedValueByPeerA(), true));
		p5.getPeersReputations().put(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P5.getConsumedValueByPeerB(), interactionP1P5.getDonatedValueByPeerB(), true));
		
		Peer p6 = new Peer(100, 6);
		Interaction interactionP1P6 = new Interaction(p1, p6);
		interactionP1P6.peerADonatesValue(75);
		p1.getPeersReputations().put(p6.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P6.getConsumedValueByPeerA(), interactionP1P6.getDonatedValueByPeerA(), true));
		p6.getPeersReputations().put(p1.getPeerId(), NetworkOfFavors.calculateLocalReputation(interactionP1P6.getConsumedValueByPeerB(), interactionP1P6.getDonatedValueByPeerB(), true));
		
		
		/*
		 * Peer1 reputation (descending order): P5(+50), P3(+25), P4(+10), P6(log(75)), P2(log(50))
		 */
		
//		Peer 6. Rep: 0.0
//		Peer 5. Rep: 53.912023005428146
//		Peer 4. Rep: 12.302585092994047
//		Peer 3. Rep: 28.2188758248682
//		Peer 2. Rep: 0.0
		
		System.out.println("Peer "+p1.getThePeerIdWithNthBestReputation(1)+". Rep: "+p1.getPeersReputations().get(p1.getThePeerIdWithNthBestReputation(1)));
		System.out.println("Peer "+p1.getThePeerIdWithNthBestReputation(2)+". Rep: "+p1.getPeersReputations().get(p1.getThePeerIdWithNthBestReputation(2)));
		System.out.println("Peer "+p1.getThePeerIdWithNthBestReputation(3)+". Rep: "+p1.getPeersReputations().get(p1.getThePeerIdWithNthBestReputation(3)));
		System.out.println("Peer "+p1.getThePeerIdWithNthBestReputation(4)+". Rep: "+p1.getPeersReputations().get(p1.getThePeerIdWithNthBestReputation(4)));
		System.out.println("Peer "+p1.getThePeerIdWithNthBestReputation(5)+". Rep: "+p1.getPeersReputations().get(p1.getThePeerIdWithNthBestReputation(5)));
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==5);
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==3);
		assertTrue(p1.getThePeerIdWithNthBestReputation(3)==4);
		assertTrue(p1.getThePeerIdWithNthBestReputation(4)==6);
		assertTrue(p1.getThePeerIdWithNthBestReputation(5)==2);
		
//		
//		//peer1 consumed 25 from peer3 and donated 50 to peer2, then, peer3 has the best reputation
//		assertEquals(p1.getThePeerWithNthBestReputation(1).getPeerId(),3);
//		assertEquals(p1.getThePeerWithNthBestReputation(2).getPeerId(),2);
//		assertEquals(p3.getThePeerWithNthBestReputation(1).getPeerId(),1);
		
		//1 peer: retrieve it; try to retrieve 2nd and 10th peer with higher reputation
//		Peer p2 = new Peer(100, 2);
//		p2.
		
//		int peer2id = 2;
//		p1.getPeersReputations().put(new Peer(1, peer2id), 15.0);
//		assertNotNull(p1.getThePeerWithNthBestReputation(1));
//		assertNull(p1.getThePeerWithNthBestReputation(2));
//		assertNull(p1.getThePeerWithNthBestReputation(10));
//		assertEquals(peer2id, p1.getThePeerWithNthBestReputation(1).getPeerId());
//		
//		//2 peers: retrieve 1st and 2nd; try to retrieve 3rd and and 10th peer with higher reputation
//		int peer3id = 3;
//		p1.getPeersReputations().put(new Peer(1, peer3id), 10.0);
//		System.out.println(p1.getPeersReputations().size());
//		assertNotNull(p1.getThePeerWithNthBestReputation(1));
//		assertNotNull(p1.getThePeerWithNthBestReputation(2));
//		assertNull(p1.getThePeerWithNthBestReputation(3));
//		assertNull(p1.getThePeerWithNthBestReputation(10));
//		assertEquals(peer2id, p1.getThePeerWithNthBestReputation(1).getPeerId());
//		assertEquals(peer3id, p1.getThePeerWithNthBestReputation(2).getPeerId());
//		
//		//3 peers: retrieve 1st, 2nd and 3rd; try to retrieve 4thrd and and 10th peer with higher reputation
//		int peer4id = 4;
//		p1.getPeersReputations().put(new Peer(1, peer4id), 20.0);
//		assertNotNull(p1.getThePeerWithNthBestReputation(1));
//		assertNotNull(p1.getThePeerWithNthBestReputation(2));
//		assertNotNull(p1.getThePeerWithNthBestReputation(3));
//		assertNull(p1.getThePeerWithNthBestReputation(4));
//		assertNull(p1.getThePeerWithNthBestReputation(10));
//		assertEquals(peer4id, p1.getThePeerWithNthBestReputation(1).getPeerId());
//		assertEquals(peer2id, p1.getThePeerWithNthBestReputation(2).getPeerId());
//		assertEquals(peer3id, p1.getThePeerWithNthBestReputation(3).getPeerId());
//		
//		//6 peers: retrieve all of them 6; try to retrieve 7th and and 10th peer with higher reputation
//		int peer5id = 5;
//		p1.getPeersReputations().put(new Peer(1, peer5id), 17.5);
//		int peer6id = 6;
//		p1.getPeersReputations().put(new Peer(1, peer6id), 7.5);
//		int peer7id = 7;
//		p1.getPeersReputations().put(new Peer(1, peer7id), 12.5);
//		assertNotNull(p1.getThePeerWithNthBestReputation(1));
//		assertNotNull(p1.getThePeerWithNthBestReputation(2));
//		assertNotNull(p1.getThePeerWithNthBestReputation(3));
//		assertNotNull(p1.getThePeerWithNthBestReputation(4));
//		assertNotNull(p1.getThePeerWithNthBestReputation(5));
//		assertNotNull(p1.getThePeerWithNthBestReputation(6));
//		assertNull(p1.getThePeerWithNthBestReputation(7));
//		assertNull(p1.getThePeerWithNthBestReputation(10));
//		assertEquals(peer4id, p1.getThePeerWithNthBestReputation(1).getPeerId());
//		assertEquals(peer5id, p1.getThePeerWithNthBestReputation(2).getPeerId());
//		assertEquals(peer2id, p1.getThePeerWithNthBestReputation(3).getPeerId());
//		assertEquals(peer7id, p1.getThePeerWithNthBestReputation(4).getPeerId());
//		assertEquals(peer3id, p1.getThePeerWithNthBestReputation(5).getPeerId());
//		assertEquals(peer6id, p1.getThePeerWithNthBestReputation(6).getPeerId());
		
		/****************** END OF THIS CASE ******************/
		
		
	}

}
