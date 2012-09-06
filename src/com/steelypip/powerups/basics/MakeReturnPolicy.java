package com.steelypip.powerups.basics;

public class MakeReturnPolicy {
	
	public static <T,U> AlertReturnPolicy< T, U > makeAlertReturnPolicy() {
		return new AlertReturnPolicy< T, U >();
	}
	
	public static <T,U> DefaultValueReturnPolicy< T, U > makeDefaultValueReturnPolicy( U u ) {
		return new DefaultValueReturnPolicy< T, U >( u );
	}
	
}
