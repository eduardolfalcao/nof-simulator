package tests;

import org.junit.Assert;
import org.junit.Test;

import peer.State;
import simulator.StateGenerator;

public class StateGeneratorTest {
	
	
	@Test
	public void testStateGenerator() {
		
		StateGenerator sg = new StateGenerator(1);
		State result = sg.generateState(State.CONSUMING, 33, State.IDLE, 33, State.PROVIDING, 34);
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
	

}
