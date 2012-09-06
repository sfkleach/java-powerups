package com.steelypip.powerups.basics;

import com.steelypip.powerups.exceptions.Alert;

public class AlertReturnPolicy< T, U > extends AbsReturnPolicy< T, U > {

	public U returnForArgs( T t, Object... args ) {
		final Alert alert = new Alert( "Undefined return value" );
		int n = 0;
		for ( Object x : args ) {
			n += 1;
			alert.culprit( "arg" + n, x );
		}
		throw alert;
	}

}
