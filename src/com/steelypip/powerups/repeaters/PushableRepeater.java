package com.steelypip.powerups.repeaters;

public interface PushableRepeater< T > extends Repeater< T > {
	void push( T item );
}
