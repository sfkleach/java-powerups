package com.steelypip.powerups.symbols;


import com.steelypip.powerups.basics.CopyableReturnPolicy;
import com.steelypip.powerups.basics.PowerUp;
import com.steelypip.powerups.exceptions.Alert;

public class Symbol extends PowerUp {
	
	final SymbolFactory factory;
	final Object key;
	
	public Symbol( SymbolFactory factory, Object key ) {
		super();
		this.factory = factory;
		this.key = key;
	}
	
	public boolean isInterned() {
		return this.factory.isInterned( this );
	}

	public SymbolFactory getSymbolFactory() {
		return this.factory;
	}
	
	public Object getKey() {
		return this.key;
	}

	public Symbol deepCopy() {
		return this;
	}

	public Symbol makeShallowCopy() {
		return this;
	}

	public Symbol newShallowCopy( final CopyableReturnPolicy policy ) {
		return (Symbol)policy.returnArg( this, null );
	}
	
	
}
