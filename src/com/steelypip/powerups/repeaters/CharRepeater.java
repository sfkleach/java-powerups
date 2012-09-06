package com.steelypip.powerups.repeaters;

public interface CharRepeater extends Repeater< Character > {
	char nextChar();
	char nextChar( char value_if_needed );
}
