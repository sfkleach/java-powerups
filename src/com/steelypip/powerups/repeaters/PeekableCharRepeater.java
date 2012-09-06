package com.steelypip.powerups.repeaters;

public interface PeekableCharRepeater extends CharRepeater {
	
	Character peek();
	Character peek( Character value_if_at_end );
	void putBack( Character prev );
	
	char peekChar();
	char peekChar( char value_if_at_end );
	void putBackChar( char prev );
	
	boolean hasNextChar( char ch );

}
