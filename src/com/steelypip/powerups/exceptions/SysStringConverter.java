package com.steelypip.powerups.exceptions;

public class SysStringConverter implements StringConverter {
	
	static final public SysStringConverter SYS_STRING_CONVERTER = new SysStringConverter();

	public String convertToString( final Object x ) {
		return "" + x;
	}
	
}
