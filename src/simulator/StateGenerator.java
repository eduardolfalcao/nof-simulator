package simulator;

import java.util.Random;

import peer.State;

public class StateGenerator {
	
	private Random randomGenerator;
	
	public StateGenerator(int seed){
		this.randomGenerator = new Random(seed);
	}
	
	
	public State generateState(State consumingState, double consumingProbability, State idleState, double idleProbability, State providingState, double providingProbability){
		int numberDrawn = randomGenerator.nextInt(100)+1;
		
		if(numberDrawn<=consumingProbability)
			return consumingState;
		else if(numberDrawn<=(consumingProbability+idleProbability))
			return idleState;
		else
			return providingState;		
	}

}
