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
import peer.balance.PeerInfo;

/**
 * @author eduardolfalcao
 *
 */
public class PeerTest {

	@Test
	public void testEqualsObject() {
		
		Peer p1 = new Peer(1, 0, 0, 0, 0, 0,0);
		Peer p2 = new Peer(2, 0, 0, 0, 0, 0,0);
		Peer p3 = new Peer(1, 0, 0, 0, 0, 0,0);
		Peer p4 = null;
		
		assertFalse(p1.equals(p2));		//when they're different
		assertTrue(p1.equals(p3));		//when they're equals
		assertTrue(p3.equals(p1));		//when they're equals
		assertFalse(p1.equals(p4));		//when one peer is null
	}

	@Test
	public void testGetThePeerWithNthBestReputation() {
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation is empty.
		 */
		
		Peer p1 = new Peer(1, 0, 0, 0, 0, 0,0);
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==-1?true:false);
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==-1?true:false);
		assertTrue(p1.getThePeerIdWithNthBestReputation(10)==-1?true:false);
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 1 peer.
		 */
		
		Peer p2 = new Peer(2, 0, 0, 0, 0, 0,0);
		Interaction interactionP1P2 = new Interaction(p2, 0, 1);
		p1.getInteractions().add(interactionP1P2);
		interactionP1P2.donate(50);
		
		PeerInfo peerInfo = new PeerInfo(p2.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P2.getConsumed(), interactionP1P2.getDonated()));
		p1.getBalances().add(peerInfo);
		
		peerInfo = new PeerInfo(p1.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P2.getDonated(), interactionP1P2.getConsumed()));
		p2.getBalances().add(peerInfo);
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(1)==2);	
		assertTrue(p2.getThePeerIdWithNthBestReputation(1)==1);
		
		assertTrue(p1.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(2)==-1);
		assertTrue(p1.getThePeerIdWithNthBestReputation(10)==-1);
		assertTrue(p2.getThePeerIdWithNthBestReputation(10)==-1);		
		
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 2 peers.
		 */
		
		Peer p3 = new Peer(3, 0, 0, 0, 0, 0,0);
		Interaction interactionP1P3 = new Interaction(p3, 0, 1);
		interactionP1P3.consume(25);
		
		peerInfo = new PeerInfo(p3.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P3.getConsumed(), interactionP1P3.getDonated()));
		p1.getBalances().add(peerInfo);
		
		peerInfo = new PeerInfo(p1.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P3.getDonated(), interactionP1P3.getConsumed()));
		p3.getBalances().add(peerInfo);
		
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
				
		
		/**
		 * Get peer with highest reputation, when the treeMap reputation has 5 peers.
		 */
		
		Peer p4 = new Peer(4, 0, 0, 0, 0, 0,0);
		Interaction interactionP1P4 = new Interaction(p4, 0, 1);
		interactionP1P4.consume(10);
		
		peerInfo = new PeerInfo(p4.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P4.getConsumed(), interactionP1P4.getDonated()));
		p1.getBalances().add(peerInfo);
		
		peerInfo = new PeerInfo(p1.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P4.getDonated(), interactionP1P4.getConsumed()));
		p4.getBalances().add(peerInfo);
		
		Peer p5 = new Peer(5, 0, 0, 0, 0, 0,0);
		Interaction interactionP1P5 = new Interaction(p5, 0, 1);
		interactionP1P5.consume(50);
		
		peerInfo = new PeerInfo(p5.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P5.getConsumed(), interactionP1P5.getDonated()));
		p1.getBalances().add(peerInfo);
		
		peerInfo = new PeerInfo(p1.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P5.getDonated(), interactionP1P5.getConsumed()));
		p5.getBalances().add(peerInfo);
		
		Peer p6 = new Peer(6, 0, 0, 0, 0, 0,0);
		Interaction interactionP1P6 = new Interaction(p6, 0, 1);
		interactionP1P6.donate(75);
		
		peerInfo = new PeerInfo(p6.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P6.getConsumed(), interactionP1P6.getDonated()));
		p1.getBalances().add(peerInfo);
		
		peerInfo = new PeerInfo(p1.getId());
		peerInfo.setBalance(NetworkOfFavors.calculateBalance(interactionP1P6.getDonated(), interactionP1P6.getConsumed()));
		p6.getBalances().add(peerInfo);
		
		/**
		 * Peer1 balance (descending order): 
		 * 
		 * Peer 5. Balance: 50
		 * Peer 3. Balance: 25
		 * Peer 4. Balance: 10
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
