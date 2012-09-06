package com.steelypip.powerups.booleans;

import com.steelypip.powerups.basics.PoweredUp;

abstract class ConSetOfBoolean extends AbsSetOfBoolean implements SetOfBoolean, PoweredUp {
	
	private int state;

	public ConSetOfBoolean( final int state ) {
		this.state = state;
	}
	
	protected static int flagsToState ( final boolean... flags ) {
		int state = 0;
		int place = 1;
		for ( boolean flag : flags ) {
			state |= flag ? place : 0;
			place <<= 1;
		}
		return state;
	}

	public ConSetOfBoolean( final boolean... flags ) {
		this.state = flagsToState( flags );
	}

	protected int getState() {
		return this.state;
	}

	public String showString() {
		return this.toString();
	}

}
