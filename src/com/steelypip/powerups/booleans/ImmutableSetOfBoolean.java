package com.steelypip.powerups.booleans;

import com.steelypip.powerups.basics.AlertCopyableReturnPolicy;
import com.steelypip.powerups.basics.CopyableReturnPolicy;
import com.steelypip.powerups.basics.PoweredUp;

public class ImmutableSetOfBoolean extends ConSetOfBoolean implements PoweredUp {

	public ImmutableSetOfBoolean( final boolean... flags ) {
		super( flags );
	}

	public ImmutableSetOfBoolean( int state ) {
		super( state );
	}
	
	public Object deepCopy() {
		return this;
	}

	public Object makeShallowCopy() {
		return this;
	}
	
	public ImmutableSetOfBoolean newShallowCopy( CopyableReturnPolicy policy) {
		return (ImmutableSetOfBoolean)policy.returnArg( this, null );
	}
	
	public ImmutableSetOfBoolean newShallowCopy() {
		return this.newShallowCopy( AlertCopyableReturnPolicy.ALERT_POLICY );
	}
	
	public static final ImmutableSetOfBoolean NEITHER = new ImmutableSetOfBoolean( 0 );
	public static final ImmutableSetOfBoolean ONLY_FALSE = new ImmutableSetOfBoolean( 1 );
	public static final ImmutableSetOfBoolean ONLY_TRUE = new ImmutableSetOfBoolean( 2 );
	public static final ImmutableSetOfBoolean EITHER = new ImmutableSetOfBoolean( 3 );
	
	private static ImmutableSetOfBoolean[] values = (
		new ImmutableSetOfBoolean[] {
			NEITHER,
			ONLY_FALSE,
			ONLY_TRUE,
			EITHER
		}
	);

	public static ImmutableSetOfBoolean valueOf( final int state ) {
		return values[ state ];
	}
	
	public static ImmutableSetOfBoolean valueOf( final boolean... flags ) {
		return values[ flagsToState( flags ) ];
	}

}

