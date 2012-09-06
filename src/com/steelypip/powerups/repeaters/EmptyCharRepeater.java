package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.basics.CopyableReturnPolicy;
import com.steelypip.powerups.basics.PoweredUp;
import com.steelypip.powerups.exceptions.Alert;

public class EmptyCharRepeater extends ShowableAbsCharRepeater implements PoweredUp {

	public boolean hasNext() {
		return false;
	}

	public char nextChar() {
		throw new Alert( "Trying to find next item on exhausted Repeater" );
	}

	public EmptyCharRepeater deepCopy() {
		return this;
	}

	public EmptyCharRepeater newShallowCopy( final CopyableReturnPolicy policy) {
		return new EmptyCharRepeater();
	}

	public EmptyCharRepeater newShallowCopy() {
		return new EmptyCharRepeater();
	}

	public EmptyCharRepeater makeShallowCopy() {
		return this;
	}

	public static EmptyCharRepeater newEmptyCharIterator() {
		return new EmptyCharRepeater();
	}
	
	static EmptyCharRepeater EMPTY_CHAR_ITERATOR = new EmptyCharRepeater();
	
	public static EmptyCharRepeater makeEmptyCharIterator() {
		return EMPTY_CHAR_ITERATOR;
	}
	
	public static EmptyCharRepeater makeEmptyCharIterator( final boolean newFlag ) {
		return newFlag ? new EmptyCharRepeater() : EMPTY_CHAR_ITERATOR;
	}
	
}
