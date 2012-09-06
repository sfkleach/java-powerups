package com.steelypip.powerups.repeaters;

public interface Repeatable< T > extends Iterable< T > {
	Repeater< T > iterator();
}
