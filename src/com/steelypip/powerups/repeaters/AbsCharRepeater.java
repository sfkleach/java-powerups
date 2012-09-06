package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.exceptions.Alert;

public abstract class AbsCharRepeater implements CharRepeater {
	
	public abstract char nextChar();

	public Character next() {
		if ( this.hasNext() ) {
			return Character.valueOf( this.nextChar() );
		} else {
			throw new Alert( "Reading past the end of stream" );
		}
	}
	
	public Character next( Character value_if_at_end ) {
		return this.nextChar( value_if_at_end );
	}

	public char nextChar( char value_if_at_end ) {
		return this.hasNext() ? this.nextChar() : value_if_at_end;
	}

	public void skip() {
		if ( this.hasNext() ) {
			this.next();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException( "Remove not supported by this iterator" );
	}
	
	public PeekableCharRepeater makePeekable() {
		return MakePeekableCharRepeater.makePeekableCharRepeater( this );
	}
	
	
	
}
