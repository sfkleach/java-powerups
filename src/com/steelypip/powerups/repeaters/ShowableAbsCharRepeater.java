package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.basics.PoweredUp;

public abstract class ShowableAbsCharRepeater extends AbsCharRepeater implements PoweredUp {

	public String showString() {
		return this.toString();
	}


}
