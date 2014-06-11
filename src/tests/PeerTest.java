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
		fail("Not yet implemented");
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
		assertNull(p1.getThePeerWithNthBestReputation(1));
		assertNull(p1.getThePeerWithNthBestReputation(2));
		assertNull(p1.getThePeerWithNthBestReputation(10));
		
		/****************** END OF THIS CASE ******************/
		
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 1 peer.
		 */
		
		Peer p2 = new Peer(100, 2);
		Interaction interactionP1P2 = new Interaction(p1, p2);
		interactionP1P2.peerADonatesValue(50);
		p1.getPeersReputations().put(p2, NetworkOfFavors.calculateLocalReputation(interactionP1P2.getConsumedValueByPeerA(), interactionP1P2.getDonatedValueByPeerA(), true));
		p2.getPeersReputations().put(p1, NetworkOfFavors.calculateLocalReputation(interactionP1P2.getConsumedValueByPeerB(), interactionP1P2.getDonatedValueByPeerB(), true));
		
		assertNotNull(p1.getThePeerWithNthBestReputation(1));
		assertNotNull(p2.getThePeerWithNthBestReputation(1));
		assertNull(p1.getThePeerWithNthBestReputation(2));
		assertNull(p2.getThePeerWithNthBestReputation(2));
		assertNull(p1.getThePeerWithNthBestReputation(10));
		assertNull(p2.getThePeerWithNthBestReputation(10));
		
		assertEquals(p1.getThePeerWithNthBestReputation(1).getPeerId(),2);
		assertEquals(p2.getThePeerWithNthBestReputation(1).getPeerId(),1);
		
		/****************** END OF THIS CASE ******************/
		
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 2 peers.
		 */
		
		Peer p3 = new Peer(100, 3);
		Interaction interactionP1P3 = new Interaction(p1, p3);
		interactionP1P3.peerBDonatesValue(25);
		p1.getPeersReputations().put(p3, NetworkOfFavors.calculateLocalReputation(interactionP1P3.getConsumedValueByPeerA(), interactionP1P3.getDonatedValueByPeerA(), true));
		p3.getPeersReputations().put(p1, NetworkOfFavors.calculateLocalReputation(interactionP1P3.getConsumedValueByPeerB(), interactionP1P3.getDonatedValueByPeerB(), true));
		
		assertNotNull(p1.getThePeerWithNthBestReputation(1));
		assertNotNull(p1.getThePeerWithNthBestReputation(2));
		assertNotNull(p3.getThePeerWithNthBestReputation(1));
		
		assertNull(p1.getThePeerWithNthBestReputation(3));
		assertNull(p3.getThePeerWithNthBestReputation(2));
		assertNull(p1.getThePeerWithNthBestReputation(10));
		assertNull(p3.getThePeerWithNthBestReputation(10));
		
		/****************** END OF THIS CASE ******************/
		
		//peer1 consumed 25 from peer3 and donated 50 to peer2, then, peer3 has the best reputation
		assertEquals(p1.getThePeerWithNthBestReputation(1).getPeerId(),3);
		assertEquals(p1.getThePeerWithNthBestReputation(2).getPeerId(),2);
		assertEquals(p3.getThePeerWithNthBestReputation(1).getPeerId(),1);
		
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
