package com.steelypip.powerups.booleans;

import java.util.AbstractSet;
import java.util.Iterator;

import com.steelypip.powerups.repeaters.MakeRepeater;

abstract class AbsSetOfBoolean extends AbstractSet< Boolean > implements SetOfBoolean {

	protected abstract int getState();

	public final boolean isEmpty() {
		return this.getState() == 0;
	}

	public final boolean contains( Object o ) {
		if ( ! ( o instanceof Boolean ) ) return false;
		final boolean b = ((Boolean)o).booleanValue();
		return ( this.getState() & ( b ? 2 : 1 ) ) != 0;
	}

	public final Iterator< Boolean > iterator() {
		switch ( this.getState() ) {
			case 0: return MakeRepeater.make0();
			case 1: return MakeRepeater.make1( Boolean.FALSE );
			case 2: return MakeRepeater.make1( Boolean.TRUE );
			default: return MakeRepeater.make2( Boolean.FALSE, Boolean.TRUE );
		}
	}

	public final int size() {
		switch ( this.getState() ) {
			case 0: return 0;
			case 3: return 2;
			default: return 1;
		}
	}

	public final boolean contains( boolean flag ) {
		return flag ? ( this.getState() & 2 ) != 0 : ( this.getState() & 1 ) != 0;
	}

	public boolean isFull() {
		return this.getState() == 3;
	}

}

