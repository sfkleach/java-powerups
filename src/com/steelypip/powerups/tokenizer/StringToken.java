package com.steelypip.powerups.tokenizer;

public class StringToken extends Token {

	@Override
	public TokenType tokenType() {
		return TokenType.STRING_TOKEN;
	}

	@Override
	public boolean isStringToken() {
		return true;
	}

	public <T> T handle( final TokenHandler< T > h ) {
		return h.handleStringToken( this );
	}
	
	
}
