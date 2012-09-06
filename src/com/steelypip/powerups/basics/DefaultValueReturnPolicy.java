package com.steelypip.powerups.basics;

public class DefaultValueReturnPolicy< T, U > extends AbsReturnPolicy< T, U > {
	
	private U defaultValue;
	
	DefaultValueReturnPolicy( U defaultValue ) {
		super();
		this.defaultValue = defaultValue;
	}

	public U returnForArgs( T t, Object... arg ) {
		return this.defaultValue;
	}

	@Override
	public U returnArg( T t, Object arg ) {
		return this.defaultValue;
	}
	
}
