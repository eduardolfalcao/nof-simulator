package simulator;

import java.util.Random;

import peer.State;

public class StateGenerator {
	
	private Random randomGenerator;
	
	public StateGenerator(int seed){
		randomGenerator = new Random(seed);
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
	
	public State generateState(State masterState, double deviation){
		int numberDrawn = randomGenerator.nextInt(100)+1;		
		
		if(numberDrawn<(100-deviation))
			return masterState;
		else{
			if(masterState == State.CONSUMING){
				if(numberDrawn<(100-deviation+deviation/2))
					return State.PROVIDING;
				else
					return State.IDLE;
			}
			else if(masterState == State.PROVIDING){
				if(numberDrawn<(100-deviation+deviation/2))
					return State.CONSUMING;
				else
					return State.IDLE;				
			}
			else{
				if(numberDrawn<(100-deviation+deviation/2))
					return State.CONSUMING;
				else
					return State.PROVIDING;
			}
		}
	}

}