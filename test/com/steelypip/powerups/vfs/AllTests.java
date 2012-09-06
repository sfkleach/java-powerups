package com.steelypip.powerups.vfs;


import com.steelypip.powerups.vfs.file.FileVVolumeTest;
import com.steelypip.powerups.vfs.zip.ZipVVolumeTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		final TestSuite suite = new TestSuite( "Test for org.openspice.vfs" );
		suite.addTestSuite( ZipVVolumeTest.class );
		suite.addTestSuite( FileVVolumeTest.class );
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}
