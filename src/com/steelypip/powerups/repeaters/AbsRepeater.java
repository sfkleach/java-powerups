package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.exceptions.Alert;

public abstract class AbsRepeater< T > implements Repeater< T > {

	public void remove() {
		throw new Alert( "Trying to remove an element from a Repeater" ).culprit( "Repeater", this );
	}

	public void skip() {
		if ( this.hasNext() ) {
			this.next();
		}
	}

	public T next( T value_if_at_end ) {
		if ( this.hasNext() ) {
			return value_if_at_end;
		} else {
			return this.next();
		}
	}

	

}
