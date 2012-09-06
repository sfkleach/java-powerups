package com.steelypip.powerups.tokenizer;

public abstract class FiniteStateMachine< TokenClass > {
	
	public abstract State newState( final String title );
	
	public abstract void addRule( State start, GuardedAction< TokenClass > action, State finish );
	
	public abstract TokenBuilder< TokenClass > getBuilder();
	public TokenClass make() {
		return this.getBuilder().make();
	}
	
}
