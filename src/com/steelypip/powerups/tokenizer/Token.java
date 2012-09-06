package com.steelypip.powerups.tokenizer;

import com.steelypip.powerups.symbols.Symbol;

public abstract class Token {

	public boolean isNumberToken() { return false; }
	public Number makeNumber() { return null; }
	
	public boolean isSymbolToken() { return false; }
	public Symbol makeSymbol() { return null; }
	
	public boolean isStringToken() { return false; }
	public String makeString() { return null; }
	
	public abstract TokenType tokenType();
	
	
	public <T> T handle( final TokenHandler< T > h ) {
		return h.handleToken( this );
	}
	
	
}
