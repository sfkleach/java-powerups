package com.steelypip.powerups.repeaters;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.steelypip.powerups.basics.CopyableReturnPolicy;
import com.steelypip.powerups.basics.PowerUp;
import com.steelypip.powerups.basics.PoweredUp;

public class PoweredUpCharRepeater extends ShowableAbsCharRepeater implements PoweredUp, Observer {
	
	private final ObservableCharRepeater ochit;
	private final List< Character > charList;
	
	private PoweredUpCharRepeater( final PoweredUpCharRepeater that, final boolean deepCopy ) {
		this.ochit = deepCopy ? PowerUp.deepCopy( that.ochit ) : that.ochit;
		this.charList = new LinkedList< Character >( that.charList );
		this.ochit.addObserver( this );
	}
	
	private PoweredUpCharRepeater( final CharRepeater chit ) {
		super();
		this.ochit = new ObservableCharRepeater( chit );
		this.charList = new LinkedList< Character >();
		this.ochit.addObserver( this );
	}

	public char nextChar() {
		if ( this.charList.isEmpty() ) {
			this.ochit.nextChar();
		}
		return this.charList.remove( 0 );
	}

	public boolean hasNext() {
		return !this.charList.isEmpty() || this.ochit.hasNext();
	}


	public PoweredUpCharRepeater deepCopy() {
		return new PoweredUpCharRepeater( this, true );
	}

	public PoweredUpCharRepeater newShallowCopy() {
		return new PoweredUpCharRepeater( this, false );
	}

	public PoweredUpCharRepeater newShallowCopy( final CopyableReturnPolicy policy) {
		return this.newShallowCopy();
	}

	public Object makeShallowCopy() {
		return this.hasNext() ? this.newShallowCopy(null) : new EmptyCharRepeater();
	}

	public void update( final Observable o, final Object arg ) {
		this.charList.add( (Character)arg );
	}
	
}
