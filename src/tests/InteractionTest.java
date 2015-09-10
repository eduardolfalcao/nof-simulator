package tests;

import static org.junit.Assert.*;
import nof.Interaction;

import org.junit.Test;

import peer.Collaborator;
import peer.Peer;

public class InteractionTest {

	
	@Test
	public void testEqualsObject() {
		
		Peer p1 = new Peer(1, 0, 0, 0, 0, 0,0);
		Peer p2 = new Peer(2, 0, 0, 0, 0, 0,0);
		Peer p3 = new Peer(1, 0, 0, 0, 0, 0,0);
		
		Interaction interactionP1P2 = new Interaction(p2, 0, 1);
		Interaction interactionP2P1 = new Interaction(p1, 0, 1);
		Interaction interactionP1P3 = new Interaction(p3, 0, 1);
		Interaction interactionP2P3 = new Interaction(p3, 0, 1);
		
		assertFalse(interactionP1P2.equals(interactionP2P1));
		assertFalse(interactionP2P1.equals(interactionP1P2));
		assertFalse(interactionP1P2.equals(interactionP1P3));
		assertTrue(interactionP2P3.equals(interactionP1P3));
	}

	@Test
	public void testDonationAndConsumptionMethods() {
		
		Collaborator c1 = new Collaborator(1, 1, 1, 0, 0, 0, 1);
		Collaborator c2 = new Collaborator(2, 1, 1, 0, 0, 0, 1);
		
		Interaction interactionc1c2 = new Interaction(c2, 0, 1);
		Interaction interactionc2c1 = new Interaction(c1, 0, 1);
		interactionc1c2.donate(25);
		interactionc2c1.consume(25);
				
		assertEquals(25.0, interactionc1c2.getDonated(),0.01);
		assertEquals(25.0, interactionc2c1.getConsumed(),0.01);
		
		interactionc1c2.donate(25);
		
		assertEquals(50.0, interactionc1c2.getDonated(),0.01);
		
	}
	
	@Test
	public void testSaveLastValues(){
		Collaborator c2 = new Collaborator(2, 1, 1, 0, 0, 0, 1);
		Interaction interactionc1c2 = new Interaction(c2, 0, 1);
		interactionc1c2.donate(25);
		interactionc1c2.consume(15);
		
		interactionc1c2.saveLastValues();
		assertEquals(25.0, interactionc1c2.getLastDonated(),0.01);
		assertEquals(15.0, interactionc1c2.getLastConsumed(),0.01);
		
		interactionc1c2.donate(25);
		interactionc1c2.consume(15);
		
		assertEquals(50.0, interactionc1c2.getDonated(),0.01);
		assertEquals(30.0, interactionc1c2.getConsumed(),0.01);
		assertEquals(25.0, interactionc1c2.getLastDonated(),0.01);
		assertEquals(15.0, interactionc1c2.getLastConsumed(),0.01);
		
		interactionc1c2.saveLastValues();
		assertEquals(50.0, interactionc1c2.getLastDonated(),0.01);
		assertEquals(30.0, interactionc1c2.getLastConsumed(),0.01);
	}

}
