package com.steelypip.powerups.basics;

public interface ReturnPolicy< This, ReturnThat > {
	ReturnThat returnArg( This t, Object arg );
	ReturnThat returnForArgs( This t, Object... arg );
}
