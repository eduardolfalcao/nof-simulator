package tests;

import static org.junit.Assert.*;
import nof.Interaction;

import org.junit.Test;

import peer.Collaborator;
import peer.Peer;

public class InteractionTest {

	/**
	 * Test method for {@link Interaction#equals(Object)}.
	 */
	@Test
	public void testEqualsObject() {
		
		/**
		 * Creates two equivalent interactions and compares them with equals.
		 * Compares different interactions.
		 * Compares to Object and null.
		 */
		
		Interaction interactionAB = new Interaction(new Peer(0, 1, false, 0), new Peer(0, 2, false, 0));
		Interaction interactionBA = new Interaction(new Peer(0, 2, false, 0), new Peer(0, 1, false, 0));
		Interaction interactionAC = new Interaction(new Peer(0, 1, false, 0), new Peer(0, 3, false, 0));
		
		assertTrue(interactionAB.equals(interactionBA));
		assertTrue(interactionBA.equals(interactionAB));
		assertFalse(interactionAB.equals(interactionAC));		
		assertFalse(interactionAB.equals(new Object()));
		assertFalse(interactionAB.equals(null));
	}

	/**
	 * Test method for {@link Interaction#getDonatedValueByPeerA(), Interaction#getDonatedValueByPeerB(),
	 * Interaction#getConsumedValueByPeerA(), Interaction#getConsumedValueByPeerB()}.
	 */
	@Test
	public void testDonationMethods() {
		
		/**
		 * Compares the donation of Peer A and B (point of view).
		 */
		
		Collaborator donator = new Collaborator(100, 1, false, 0, 0);
		Collaborator consumer = new Collaborator(100, 2, true, 100, 0);		
		Interaction interactionAB = new Interaction(donator, consumer);
		
		interactionAB.peerADonatesValue(25);
		
		assertEquals(25.0, interactionAB.getDonatedValueByPeerA(),0.01);
		assertEquals(25.0, interactionAB.getConsumedValueByPeerB(),0.01);
		
		interactionAB.peerBDonatesValue(25);
		
		assertEquals(25.0, interactionAB.getDonatedValueByPeerB(),0.01);
		assertEquals(25.0, interactionAB.getConsumedValueByPeerA(),0.01);
		
	}

}
