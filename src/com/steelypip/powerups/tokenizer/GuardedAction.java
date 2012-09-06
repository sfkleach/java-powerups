/**
 * 
 */
package com.steelypip.powerups.tokenizer;

public abstract class GuardedAction< TokenClass > {

	public abstract Accept accept( char ch, TokenBuilder< TokenClass > b );
	
}