package com.steelypip.powerups.basics;

public interface Copyable {
	Object makeShallowCopy();
	Object deepCopy();
	Object newShallowCopy( CopyableReturnPolicy policy) ;
	Object newShallowCopy() ;
}
