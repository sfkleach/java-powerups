package com.steelypip.powerups.booleans;

import com.steelypip.powerups.basics.CopyableReturnPolicy;
import com.steelypip.powerups.basics.PoweredUp;

public class MutableSetOfBoolean extends ConSetOfBoolean implements PoweredUp {

	public MutableSetOfBoolean( final boolean... flags ) {
		super( flags );
	}

	public MutableSetOfBoolean( int state ) {
		super( state );
	}
	
	public MutableSetOfBoolean deepCopy() {
		return this.makeShallowCopy();
	}

	public MutableSetOfBoolean makeShallowCopy() {
		return new MutableSetOfBoolean( this.getState() );
	}
	
	public MutableSetOfBoolean newShallowCopy( CopyableReturnPolicy policy ) {
		return this.makeShallowCopy();
	}
	
	public MutableSetOfBoolean newShallowCopy() {
		return this.makeShallowCopy();
	}
	
	

}
