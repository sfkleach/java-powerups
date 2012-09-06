package com.steelypip.powerups.minx;

public class StdMinXFactory extends MinXFactory< MinX > {
	
	@Override
	public MinX newMinX( final String name ) {
		return new MinX( name );
	}
	
	public static final StdMinXFactory STD_MIN_X_FACTORY = new StdMinXFactory();
}

