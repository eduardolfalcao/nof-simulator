package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

import peer.Collaborator;
import peer.FreeRider;
import peer.Peer;
import simulator.Simulator;

public class SimulatorTest {

//	/**
//	 * Test method for {@link simulator.Simulator#setupSimulation()}.
//	 */
//	@Test
//	public void testSetupSimulation() {
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//		
//		/**
//		 * Case 1:
//		 * consumingStateProbability = 20, percentageCollaborators = 0.25
//		 */
//		
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();		
//		assertTrue((s1.getConsumersCollabList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size())==numPeers);
//		for(int peerId : s1.getConsumersCollabList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);
//			
//		}
//		for(int peerId : s1.getDonatorsList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertFalse(Simulator.peers[peerId].isConsuming());
//			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		for(int peerId : s1.getFreeRidersList()){
//			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		
//		
//		
//		/**
//		 * Case 2:
//		 * consumingStateProbability = 20, percentageCollaborators = 0.75
//		 */
//		
//		percentageCollaborators = 0.75;	//75%
//		Simulator s2 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s2.setupSimulation();
//		assertTrue((s2.getConsumersCollabList().size()+s2.getDonatorsList().size()+s2.getFreeRidersList().size())==numPeers);
//		for(int peerId : s2.getConsumersCollabList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);
//			
//		}
//		for(int peerId : s2.getDonatorsList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertFalse(Simulator.peers[peerId].isConsuming());
//			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		for(int peerId : s2.getFreeRidersList()){
//			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		
//		
//		/**
//		 * Case 3:
//		 * consumingStateProbability = 50, percentageCollaborators = 0.75
//		 */
//		
//		consumingStateProbability = 50;
//		Simulator s3 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s3.setupSimulation();
//		assertTrue((s3.getConsumersCollabList().size()+s3.getDonatorsList().size()+s3.getFreeRidersList().size())==numPeers);
//		for(int peerId : s3.getConsumersCollabList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);
//			
//		}
//		for(int peerId : s3.getDonatorsList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertFalse(Simulator.peers[peerId].isConsuming());
//			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		for(int peerId : s3.getFreeRidersList()){
//			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		
//		
//		
//		/**
//		 * Case 4:
//		 * consumingStateProbability = 50, percentageCollaborators = 0.25
//		 */
//		
//		percentageCollaborators = 0.25;	//25%
//		Simulator s4 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s4.setupSimulation();
//		assertTrue((s4.getConsumersCollabList().size()+s4.getDonatorsList().size()+s4.getFreeRidersList().size())==numPeers);
//		for(int peerId : s4.getConsumersCollabList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);
//			
//		}
//		for(int peerId : s4.getDonatorsList()){
//			assertTrue(Simulator.peers[peerId] instanceof Collaborator);
//			assertFalse(Simulator.peers[peerId].isConsuming());
//			assertFalse(Simulator.peers[peerId].getDemand() > 0);			
//		}
//		for(int peerId : s4.getFreeRidersList()){
//			assertTrue(Simulator.peers[peerId] instanceof FreeRider);
//			assertTrue(Simulator.peers[peerId].isConsuming());
//			assertTrue(Simulator.peers[peerId].getDemand() > 0);			
//		}
//
//	}
//	
//	
//	/**
//	 * Test method for {@link simulator.Simulator#testPerformCurrentStepDonations()}.
//	 */
//	@Test
//	public void testPerformCurrentStepDonations() {
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//		
//		/**
//		 * Case 1:
//		 * consumingStateProbability = 20, percentageCollaborators = 0.25
//		 */
//		
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();
//		s1.testPerformCurrentStepDonationsNoNextStep();		
//		assertTrue(s1.getCurrentStep()==1);
//		assertTrue(s1.getDonatorsList().isEmpty() || s1.getConsumersCollabList().isEmpty());				
//		assertTrue((s1.getDonatedPeersList().size()+s1.getConsumedPeersList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size()+s1.getConsumersCollabList().size())==numPeers);
////		System.out.println("s1.getDonatedPeersList().size(): "+s1.getDonatedPeersList().size());
////		System.out.println("s1.getConsumedPeersList(): "+s1.getConsumedPeersList().size());
////		System.out.println("s1.getDonatorsList().size(): "+s1.getDonatorsList().size());
////		System.out.println("s1.getFreeRidersList().size(): "+s1.getFreeRidersList().size());
////		System.out.println("s1.getConsumersCollabList().size(): "+s1.getConsumersCollabList().size());
//				
//		
//		/**
//		 * Case 2:
//		 * consumingStateProbability = 20, percentageCollaborators = 0.75
//		 */
//		
//		percentageCollaborators = 0.75;	//75%
//		Simulator s2 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s2.setupSimulation();
//		s2.testPerformCurrentStepDonationsNoNextStep();		
//		assertTrue(s2.getCurrentStep()==1);
//		assertTrue(s2.getDonatorsList().isEmpty() || s2.getConsumersCollabList().isEmpty());	
//		assertTrue((s2.getDonatedPeersList().size()+s2.getConsumedPeersList().size()+s2.getDonatorsList().size()+s2.getFreeRidersList().size()+s2.getConsumersCollabList().size())==numPeers);		
//		
//		/**
//		 * Case 3:
//		 * consumingStateProbability = 50, percentageCollaborators = 0.75
//		 */
//		
//		consumingStateProbability = 50;
//		Simulator s3 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s3.setupSimulation();
//		s3.testPerformCurrentStepDonationsNoNextStep();		
//		assertTrue(s3.getCurrentStep()==1);
//		assertTrue(s3.getDonatorsList().isEmpty() || s3.getConsumersCollabList().isEmpty());	
//		assertTrue((s3.getDonatedPeersList().size()+s3.getConsumedPeersList().size()+s3.getDonatorsList().size()+s3.getFreeRidersList().size()+s3.getConsumersCollabList().size())==numPeers);
//		
//		
//		
//		/**
//		 * Case 4:
//		 * consumingStateProbability = 50, percentageCollaborators = 0.25
//		 */
//		
//		percentageCollaborators = 0.25;	//25%
//		Simulator s4 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s4.setupSimulation();
//		s4.testPerformCurrentStepDonationsNoNextStep();		
//		assertTrue(s4.getCurrentStep()==1);
//		assertTrue(s4.getDonatorsList().isEmpty() || s4.getConsumersCollabList().isEmpty());	
//		assertTrue((s4.getDonatedPeersList().size()+s4.getConsumedPeersList().size()+s4.getDonatorsList().size()+s4.getFreeRidersList().size()+s4.getConsumersCollabList().size())==numPeers);
//	}
//		
//	
//	/**
//	 * Test method for {@link simulator.Simulator#testPerformCurrentStepDonationsNoNextStep()}.
//	 */
//	@Test
//	public void testPerformCurrentStepDonationsNoNextStep() {
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//		
//		/**
//		 * Case 1:
//		 * consumingStateProbability = 20, percentageCollaborators = 0.25
//		 */
//		
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();
//		s1.testPerformCurrentStepDonationsNoNextStep();		
//		s1.testNextStepNoPerformCurrentStepDonations();
//		assertTrue((s1.getDonatedPeersList().size()+s1.getConsumedPeersList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size()+s1.getConsumersCollabList().size())==numPeers);
//		assertTrue(s1.getDonatedPeersList().isEmpty() && s1.getConsumedPeersList().isEmpty());
//		for(Integer i : s1.getFreeRidersList()){
//			assertTrue(Simulator.peers[i] instanceof FreeRider);
//			assertTrue(Simulator.peers[i].isConsuming());
//			assertTrue(Simulator.peers[i].getDemand()>0);
//		}
//		for(Integer i : s1.getConsumersCollabList()){
//			assertTrue(Simulator.peers[i] instanceof Collaborator);
//			assertTrue(Simulator.peers[i].isConsuming());
//			assertTrue(Simulator.peers[i].getDemand()>0);
//			assertTrue(((Collaborator)Simulator.peers[i]).getCapacitySupplied()==0);
//		}
//		for(Integer i : s1.getDonatorsList()){
//			assertTrue(Simulator.peers[i] instanceof Collaborator);
//			assertFalse(Simulator.peers[i].isConsuming());
//			assertTrue(Simulator.peers[i].getDemand()==0);
//			assertTrue(((Collaborator)Simulator.peers[i]).getCapacitySupplied()>0);
//		}
//		s1.testPerformCurrentStepDonationsNoNextStep();		
//		assertTrue(s1.getCurrentStep()==2);
//		assertTrue(s1.getDonatorsList().isEmpty() || s1.getConsumersCollabList().isEmpty());				
//		assertTrue((s1.getDonatedPeersList().size()+s1.getConsumedPeersList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size()+s1.getConsumersCollabList().size())==numPeers);
//		
//		s1.testNextStepNoPerformCurrentStepDonations();
//		assertTrue((s1.getDonatedPeersList().size()+s1.getConsumedPeersList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size()+s1.getConsumersCollabList().size())==numPeers);
//		assertTrue(s1.getDonatedPeersList().isEmpty() && s1.getConsumedPeersList().isEmpty());
//		for(Integer i : s1.getFreeRidersList()){
//			assertTrue(Simulator.peers[i] instanceof FreeRider);
//			assertTrue(Simulator.peers[i].isConsuming());
//			assertTrue(Simulator.peers[i].getDemand()>0);
//		}
//		for(Integer i : s1.getConsumersCollabList()){
//			assertTrue(Simulator.peers[i] instanceof Collaborator);
//			assertTrue(Simulator.peers[i].isConsuming());
//			assertTrue(Simulator.peers[i].getDemand()>0);
//			assertTrue(((Collaborator)Simulator.peers[i]).getCapacitySupplied()==0);
//		}
//		for(Integer i : s1.getDonatorsList()){
//			assertTrue(Simulator.peers[i] instanceof Collaborator);
//			assertFalse(Simulator.peers[i].isConsuming());
//			assertTrue(Simulator.peers[i].getDemand()==0);
//			assertTrue(((Collaborator)Simulator.peers[i]).getCapacitySupplied()>0);
//		}
//		s1.testPerformCurrentStepDonationsNoNextStep();		
//		assertTrue(s1.getCurrentStep()==3);
//		assertTrue(s1.getDonatorsList().isEmpty() || s1.getConsumersCollabList().isEmpty());				
//		assertTrue((s1.getDonatedPeersList().size()+s1.getConsumedPeersList().size()+s1.getDonatorsList().size()+s1.getFreeRidersList().size()+s1.getConsumersCollabList().size())==numPeers);
//	
//	}
//	
//	/**
//	 * Test method for choosesCollaboratorToDonate.
//	 */
//	@Test
//	public void testChoosesCollaboratorToDonate() {
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//		
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();
//		
//		for(int i = 0; i < 10000; i++)
//			assertTrue(s1.testChoosesCollaboratorToDonate() instanceof Collaborator);
//		
//	}
//	
//	
//	/**
//	 * Test method for anyPeer.
//	 */
//	@Test
//	public void testAnyPeer() {
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//		
//		/**
//		 * Case 1:
//		 * consumingStateProbability = 20, percentageCollaborators = 0.25
//		 */
//		
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();
//		
//		for(int i = 0; i < 10000; i++){
//			assertTrue(s1.testAnyPeer(s1.getConsumersCollabList()) >= 0 && s1.testAnyPeer(s1.getConsumersCollabList()) < 1000);
//			assertTrue(s1.testAnyPeer(s1.getDonatorsList()) >= 0 && s1.testAnyPeer(s1.getDonatorsList()) < 1000);
//			assertTrue(s1.testAnyPeer(s1.getFreeRidersList()) >= 0 && s1.testAnyPeer(s1.getFreeRidersList()) < 1000);		
//		}
//		
//	}
//
//	/**
//	 * Test method for choosesConsumer().
//	 */
//	@Test
//	public void testChoosesConsumer(){
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//				
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();
//		
//		Collaborator collab = s1.testChoosesCollaboratorToDonate();
//		
//		for(int i = 0; i < 10000; i++){
//			Peer p = s1.testChoosesConsumer(collab);
//			assertTrue(p.isConsuming());
//		}
//		
//	}
//	
//	/**
//	 * Test method for performDonation.
//	 */
//	@Test
//	public void testPerformDonation(){
//		
//		int numPeers = 1000;
//		int numSteps = 5000;
//		double consumingStateProbability = 0.2;
//		double percentageCollaborators = 0.25;	//25%
//		double peersDemand = 2;					//2C
//		double capacitySupplied = 1;
//		int returnLevelVerificationTime = 10;
//		double changingValue = 0.05;
//		boolean dynamic = false;
//		boolean withLog = false;
//				
//		Simulator s1 = new Simulator(numPeers, numSteps, consumingStateProbability, percentageCollaborators, dynamic, withLog, peersDemand, capacitySupplied, returnLevelVerificationTime, changingValue, Level.INFO);
//		s1.setupSimulation();
//		
//		Collaborator collab = s1.testChoosesCollaboratorToDonate();
//		Peer consumer = s1.testChoosesConsumer(collab);
//		
//		
//		
//		double initialConsumerDemand = consumer.getDemand();
//		double donation = Math.min(collab.getCapacitySupplied(), consumer.getDemand());	
////		System.out.println("Donator - initialCapacity(1) - donation("+donation+")");
////		System.out.println("Consumer - initialDemand("+initialConsumerDemand+")");
//		s1.testPerformDonation(collab, consumer);
//		assertTrue((1-donation)==collab.getCapacitySupplied());				
//		assertTrue((initialConsumerDemand-donation)==consumer.getDemand());
////		System.out.println("Donator - currentCapacity("+collab.getCapacitySupplied()+") - should be 1-donation("+(1-donation)+")");
////		System.out.println("Consumer - currentDemand("+consumer.getDemand()+") - should be initialConsumerDemand-donation("+(initialConsumerDemand-donation)+")");
//		
//		assertTrue(consumer.getInteractions().size()==1);
//		assertTrue(collab.getInteractions().size()==1);
//		assertEquals(consumer.getInteractions().get(0), collab.getInteractions().get(0));
//		
//	}
}
