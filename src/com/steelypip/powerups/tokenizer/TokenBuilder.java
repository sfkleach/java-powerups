/**
 * 
 */
package com.steelypip.powerups.tokenizer;

public abstract class TokenBuilder< TokenClass > {
	public abstract TokenClass make();
	public abstract void add( char ch );
}