package tests;

import org.junit.Assert;
import org.junit.Test;

import peer.State;
import simulator.StateGenerator;

public class StateGeneratorTest {
	
	
	@Test
	public void testStateGenerator1() {
		
		StateGenerator sg = new StateGenerator(1);
		State result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.PROVIDING, result);		
		
		//when a peer alternates between consuming and providing
		result = sg.generateState(State.CONSUMING, 50, State.IDLE, 0, State.PROVIDING, 50);
		Assert.assertEquals(State.PROVIDING, result);
		
		//when a peer is a sheer free rider, i.e., only consumes
		result = sg.generateState(State.CONSUMING, 100, State.IDLE, 0, State.PROVIDING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		//when a peer is completely altruistic
		result = sg.generateState(State.CONSUMING, 0, State.IDLE, 0, State.PROVIDING, 100);
		Assert.assertEquals(State.PROVIDING, result);
		
		sg = new StateGenerator(2);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.CONSUMING, result);
		
		sg = new StateGenerator(3);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.IDLE, result);
		
		sg = new StateGenerator(4);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.IDLE, result);
		
		sg = new StateGenerator(5);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.PROVIDING, result);
		
		sg = new StateGenerator(6);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.CONSUMING, result);
		
		sg = new StateGenerator(7);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.IDLE, result);
		
		sg = new StateGenerator(8);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.IDLE, result);
		
		sg = new StateGenerator(9);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.PROVIDING, result);
		
		sg = new StateGenerator(10);
		result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
		Assert.assertEquals(State.CONSUMING, result);		
		
	}
	
	@Test
	public void testStateGenerator2() {
		StateGenerator sg = new StateGenerator(1);
		State result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);	
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 0);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.CONSUMING, 30);
		Assert.assertEquals(State.CONSUMING, result);
		
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.PROVIDING, 0);
		Assert.assertEquals(State.PROVIDING, result);
				
		result = sg.generateState(State.PROVIDING, 50);
		Assert.assertEquals(State.PROVIDING, result);
				
		result = sg.generateState(State.PROVIDING, 50);
		Assert.assertEquals(State.IDLE, result);
				
		result = sg.generateState(State.PROVIDING, 50);
		Assert.assertEquals(State.IDLE, result);
		
		result = sg.generateState(State.PROVIDING, 50);
		Assert.assertEquals(State.CONSUMING, result);
				
		result = sg.generateState(State.PROVIDING, 50);
		Assert.assertEquals(State.IDLE, result);
		
		
		
		
		
		result = sg.generateState(State.IDLE, 100);
		Assert.assertEquals(State.PROVIDING, result);
		
		result = sg.generateState(State.IDLE, 100);
		Assert.assertEquals(State.CONSUMING, result);
		
		result = sg.generateState(State.IDLE, 0);
		Assert.assertEquals(State.IDLE, result);
	}
	

}
