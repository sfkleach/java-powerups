package com.steelypip.powerups.tokenizer;

public class NumberToken extends Token {

	@Override
	public TokenType tokenType() {
		return TokenType.NUMBER_TOKEN;
	}

	@Override
	public boolean isNumberToken() {
		return true;
	}
	
	public <T> T handle( final TokenHandler< T > h ) {
		return h.handleNumberToken( this );
	}


}
