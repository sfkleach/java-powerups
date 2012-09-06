package com.steelypip.powerups.basics;

public abstract class AbsReturnPolicy< T, U > implements ReturnPolicy< T, U >{

	public U returnArg( T t, Object arg ) {
		return this.returnArg( t, new Object[] { arg } );
	}
	
}
