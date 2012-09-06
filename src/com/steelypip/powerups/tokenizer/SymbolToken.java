package com.steelypip.powerups.tokenizer;

public class SymbolToken extends Token {

	@Override
	public TokenType tokenType() {
		return TokenType.SYMBOL_TOKEN;
	}

	@Override
	public boolean isSymbolToken() {
		return true;
	}
	public <T> T handle( final TokenHandler< T > h ) {
		return h.handleSymbolToken( this );
	}
	

}
