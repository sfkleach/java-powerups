package com.steelypip.powerups.repeaters;

import java.util.Observable;


class ObservableCharRepeater extends Observable implements CharRepeater {

	final CharRepeater chit;
	char lastChar;

	ObservableCharRepeater( final CharRepeater chit ) {
		super();
		this.chit = chit;
	}

	public boolean hasNext() {
		return chit.hasNext();
	}

	public Character next() {
		return this.nextChar();
	}

	public char nextChar() {
		this.lastChar = chit.nextChar();
		this.notifyObservers( this.lastChar );
		return this.lastChar;
	}

	public void remove() {
		chit.remove();
	}

	public void skip() {
		if ( this.hasNext() ) this.nextChar();  
	}

	public Character next( Character value_if_at_end ) {
		return this.nextChar( value_if_at_end );
	}

	public char nextChar( char value_if_at_end ) {
		return this.hasNext() ? this.nextChar() : value_if_at_end;
	}

}
