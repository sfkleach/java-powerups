package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.basics.CopyableReturnPolicy;

public class GeneralStringCharRepeater extends StringCharRepeater {
	
	private final String string;
	private int index = 0;
	private final int slen;
	
	public GeneralStringCharRepeater( final String string ) {
		super();
		this.string = string;
		this.slen = string.length();
	}

	public boolean hasNext() {
		return this.index < slen;
	}

	public char nextChar() {
		try {
			return this.string.charAt( this.index++ );
		} catch ( IndexOutOfBoundsException _ ) {
			return 0;
		}
	}

	public Object deepCopy() {
		return this.newShallowCopy( null );
	}

	public Object newShallowCopy( final CopyableReturnPolicy policy) {
		return this.newShallowCopy();
	}

	public Object newShallowCopy() {
		final String s = this.string.substring( this.index );
		return StringCharRepeater.newStringCharIterator( s );
	}

	public Object makeShallowCopy() {
		return this.newShallowCopy(null);
	}
	
}
