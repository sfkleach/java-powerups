package com.steelypip.powerups.repeaters;

public abstract class ProxyPeekableCharRepeater {

	public abstract PeekableCharRepeater cucharin();

	public boolean hasNext() {
		return cucharin().hasNext();
	}

	public Character peek( Character value_if_at_end ) {
		return cucharin().peek( value_if_at_end );
	}

	public void putBack( Character prev ) {
		cucharin().putBack( prev );
	}

	public char peekChar() {
		return cucharin().peekChar();
	}

	public char peekChar( char value_if_at_end ) {
		return cucharin().peekChar( value_if_at_end );
	}

	public void putBackChar( char prev ) {
		cucharin().putBackChar( prev );
	}

	public boolean hasNextChar( char ch ) {
		return cucharin().hasNextChar( ch );
	}

	public Character next() {
		return cucharin().next();
	}

	public char nextChar() {
		return cucharin().nextChar();
	}

	public Character peek() {
		return cucharin().peek();
	}

	public char nextChar( char value_if_needed ) {
		return cucharin().nextChar( value_if_needed );
	}

	public Character next( Character value_if_at_end ) {
		return cucharin().next( value_if_at_end );
	}

	public void remove() {
		cucharin().remove();
	}

	public void skip() {
		cucharin().skip();
	}

	
}
