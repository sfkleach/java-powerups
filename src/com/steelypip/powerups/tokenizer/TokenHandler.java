package com.steelypip.powerups.tokenizer;

import com.steelypip.powerups.exceptions.Alert;

public class TokenHandler< T > {

	final T handle( final Token t ) {
		return t.handle( this );
	}
	
	T handleToken( final Token t ) {
		throw new Alert( "No handling defined for token" ).culprit( "Token", t );
	}
	
	T handleStringToken( final StringToken t ) {
		return this.handleToken( t );
	}
	
	T handleNumberToken( final NumberToken t ) {
		return this.handleToken( t );
	}
	
	T handleSymbolToken( final SymbolToken t ) {
		return this.handleToken( t );
	}
	
}
