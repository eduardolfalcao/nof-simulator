package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.Simulator;

public class SimulatorTest {

	/**
	 * Test method for {@link simulator.Simulator#setupSimulation()}.
	 */
	@Test
	public void testSetupSimulation() {
		
		int numPeers = 1000;
		int numSteps = 5000;
		int consumingStateProbability = 20;
		double percentageCollaborators = 0.25;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 10;
		double changingValue = 0.05;
		
		/**
		 * Case 1:
		 * consumingStateProbability = 20, percentageCollaborators = 0.25
		 */
		
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s1.setupSimulation();		
		assertTrue((s1.getConsumersCollabList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size())==numPeers);
		for(int peerId : s1.getConsumersCollabList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);
			
		}
		for(int peerId : s1.getDonatorsList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertFalse(Simulator.peers[peerId].isConsuming());
			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
		}
		for(int peerId : s1.getFreeRidersList()){
			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
		}
		
		
		
		/**
		 * Case 2:
		 * consumingStateProbability = 20, percentageCollaborators = 0.75
		 */
		
		percentageCollaborators = 0.75;	//75%
		Simulator s2 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s2.setupSimulation();
		assertTrue((s2.getConsumersCollabList().size()+s2.getDonatorsList().size()+s2.getFreeRidersList().size())==numPeers);
		for(int peerId : s2.getConsumersCollabList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);
			
		}
		for(int peerId : s2.getDonatorsList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertFalse(Simulator.peers[peerId].isConsuming());
			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
		}
		for(int peerId : s2.getFreeRidersList()){
			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
		}
		
		
		/**
		 * Case 3:
		 * consumingStateProbability = 50, percentageCollaborators = 0.75
		 */
		
		consumingStateProbability = 50;
		Simulator s3 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s3.setupSimulation();
		assertTrue((s3.getConsumersCollabList().size()+s3.getDonatorsList().size()+s3.getFreeRidersList().size())==numPeers);
		for(int peerId : s3.getConsumersCollabList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);
			
		}
		for(int peerId : s3.getDonatorsList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertFalse(Simulator.peers[peerId].isConsuming());
			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
		}
		for(int peerId : s3.getFreeRidersList()){
			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
		}
		
		
		
		/**
		 * Case 4:
		 * consumingStateProbability = 50, percentageCollaborators = 0.25
		 */
		
		percentageCollaborators = 0.25;	//25%
		Simulator s4 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s4.setupSimulation();
		assertTrue((s4.getConsumersCollabList().size()+s4.getDonatorsList().size()+s4.getFreeRidersList().size())==numPeers);
		for(int peerId : s4.getConsumersCollabList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);
			
		}
		for(int peerId : s4.getDonatorsList()){
			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
			assertFalse(Simulator.peers[peerId].isConsuming());
			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
		}
		for(int peerId : s4.getFreeRidersList()){
			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
			assertTrue(Simulator.peers[peerId].isConsuming());
			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
		}

	}
	
	
	
	
	/**
	 * Test method for choosesCollaboratorToDonate.
	 */
	@Test
	public void testChoosesCollaboratorToDonate() {
		
		int numPeers = 1000;
		int numSteps = 5000;
		int consumingStateProbability = 20;
		double percentageCollaborators = 0.25;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 10;
		double changingValue = 0.05;
		
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s1.setupSimulation();
		
		for(int i = 0; i < 10000; i++)
			assertTrue(s1.testChoosesCollaboratorToDonate() instanceof Collaborator);
		
	}
	
	
	/**
	 * Test method for anyPeer.
	 */
	@Test
	public void testAnyPeer() {
		
		int numPeers = 1000;
		int numSteps = 5000;
		int consumingStateProbability = 20;
		double percentageCollaborators = 0.25;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 10;
		double changingValue = 0.05;
		
		/**
		 * Case 1:
		 * consumingStateProbability = 20, percentageCollaborators = 0.25
		 */
		
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s1.setupSimulation();
		
		for(int i = 0; i < 10000; i++){
			assertTrue(s1.testAnyPeer(s1.getConsumersCollabList()) >= 0 && s1.testAnyPeer(s1.getConsumersCollabList()) < 1000);
			assertTrue(s1.testAnyPeer(s1.getDonatorsList()) >= 0 && s1.testAnyPeer(s1.getDonatorsList()) < 1000);
			assertTrue(s1.testAnyPeer(s1.getFreeRidersList()) >= 0 && s1.testAnyPeer(s1.getFreeRidersList()) < 1000);		
		}
		
	}

	/**
	 * Test method for choosesConsumer().
	 */
	@Test
	public void testChoosesConsumer(){
		
		int numPeers = 1000;
		int numSteps = 5000;
		int consumingStateProbability = 20;
		double percentageCollaborators = 0.25;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 10;
		double changingValue = 0.05;
				
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s1.setupSimulation();
		
		Collaborator collab = s1.testChoosesCollaboratorToDonate();
		
		for(int i = 0; i < 10000; i++){
			Peer p = s1.testChoosesConsumer(collab);
			assertTrue(p.isConsuming());
		}
		
	}
	
	/**
	 * Test method for performDonation.
	 */
	@Test
	public void testPerformDonation(){
		
		int numPeers = 1000;
		int numSteps = 5000;
		int consumingStateProbability = 20;
		double percentageCollaborators = 0.25;	//25%
		double peersDemand = 2;					//2C
		double capacitySupplied = 1;
		int returnLevelVerificationTime = 10;
		double changingValue = 0.05;
				
		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue);
		s1.setupSimulation();
		
		Collaborator collab = s1.testChoosesCollaboratorToDonate();
		Peer consumer = s1.testChoosesConsumer(collab);
		
		
		
		double initialConsumerDemand = consumer.getDemand();
		double donation = Math.min(collab.getCapacitySupplied(), consumer.getDemand());	
		System.out.println("Donator - initialCapacity(1) - donation("+donation+")");
		System.out.println("Consumer - initialDemand("+initialConsumerDemand+")");
		s1.testPerformDonation(collab, consumer);
		assertTrue((1-donation)==collab.getCapacitySupplied());				
		assertTrue((initialConsumerDemand-donation)==consumer.getDemand());
		System.out.println("Donator - currentCapacity("+collab.getCapacitySupplied()+") - should be 1-donation("+(1-donation)+")");
		System.out.println("Consumer - currentDemand("+consumer.getDemand()+") - should be initialConsumerDemand-donation("+(initialConsumerDemand-donation)+")");
		
		assertTrue(consumer.getInteractions().size()==1);
		assertTrue(collab.getInteractions().size()==1);
		assertEquals(consumer.getInteractions().get(0), collab.getInteractions().get(0));
		
	}
}
