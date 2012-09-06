package com.steelypip.powerups.basics;

import com.steelypip.powerups.exceptions.Alert;

public abstract class PowerUp implements PoweredUp {
	
	static public Alert newShallowCopyAlert( final Object x ) {
		return new Alert( "Cannot make a shallow copy of this object" ).culprit(  "object", x );
	}
	
	static public <T> T deepCopy( T t ) {
		throw Alert.unimplemented();
	}
	
	public String showString() {
		return this.toString();
	}
		
	public Object newShallowCopy() {
		return this.newShallowCopy( AlertCopyableReturnPolicy.ALERT_POLICY ); 
	}
}
