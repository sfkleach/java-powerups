package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.basics.CopyableReturnPolicy;
import com.steelypip.powerups.exceptions.Alert;

public class EmptyStringCharRepeater extends StringCharRepeater {

	public char nextChar() {
		throw new Alert( "Trying to get next item from empty Repeater" );
	}

	public Object deepCopy() {
		return this;
	}

	public Object newShallowCopy( final CopyableReturnPolicy policy) {
		return new EmptyStringCharRepeater();
	}

	public Object newShallowCopy() {
		return new EmptyStringCharRepeater();
	}

	public Object makeShallowCopy() {
		return this;
	}

	public boolean hasNext() {
		return false;
	}
	

}
