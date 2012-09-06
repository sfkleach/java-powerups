package com.steelypip.powerups.repeaters;

import java.io.Reader;

public class MakeCharRepeater {

	public static StringCharRepeater newCharIterator( final String s ) {
		return StringCharRepeater.newStringCharIterator( s );
	}
	
	public static ReaderCharRepeater newCharIterator( final Reader r ) {
		return ReaderCharRepeater.newReaderCharIterator( r );
	}

	public static EmptyCharRepeater newCharIterator() {
		return EmptyCharRepeater.newEmptyCharIterator();
	}
	
	public static StringCharRepeater makeCharIterator( final String s ) {
		return StringCharRepeater.makeStringCharIterator( s );
	}
	
	public static ReaderCharRepeater makeCharIterator( final Reader r ) {
		return ReaderCharRepeater.makeReaderCharIterator( r );
	}

	public static EmptyCharRepeater makeCharIterator() {
		return EmptyCharRepeater.makeEmptyCharIterator();
	}
	

	public static StringCharRepeater makeCharIterator( final String s, final boolean newCopy ) {
		return StringCharRepeater.makeStringCharIterator( s, newCopy );
	}
	
	public static ReaderCharRepeater makeCharIterator( final Reader r, final boolean newCopy ) {
		return ReaderCharRepeater.makeReaderCharIterator( r, newCopy );
	}

	public static EmptyCharRepeater makeCharIterator( final boolean newCopy ) {
		return EmptyCharRepeater.makeEmptyCharIterator( newCopy );
	}	
	
}
