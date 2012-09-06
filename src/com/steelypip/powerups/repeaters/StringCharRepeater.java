package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.basics.PoweredUp;

public abstract class StringCharRepeater extends ShowableAbsCharRepeater implements PoweredUp {

	public static StringCharRepeater makeStringCharIterator( final String s ) {
		return makeStringCharIterator( s, false );
	}
	
	public static StringCharRepeater newStringCharIterator( final String s ) {
		return makeStringCharIterator( s, true );
	}
	
	final static EmptyStringCharRepeater EMPTY_STRING_CHAR_ITERATOR = new EmptyStringCharRepeater();
	
	public static StringCharRepeater makeStringCharIterator( final String s, final boolean newCopy ) {
		return (
			s.length() > 0 ?  new GeneralStringCharRepeater( s ) :
			newCopy ? new EmptyStringCharRepeater() :
			EMPTY_STRING_CHAR_ITERATOR
		);
	}
	
}
