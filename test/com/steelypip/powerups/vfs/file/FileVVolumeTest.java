package com.steelypip.powerups.vfs.file;

import java.io.File;
import java.util.Iterator;

import com.steelypip.powerups.vfs.VFile;
import com.steelypip.powerups.vfs.VFolder;
import com.steelypip.powerups.vfs.file.FileVFolder;
import com.steelypip.powerups.vfs.file.FileVVolume;

import junit.framework.TestCase;

public class FileVVolumeTest extends TestCase {
	
	public void testFileVVolume() {
		final VFolder folder = new FileVVolume( new File( "/usr" ) ).getRootVFolder();
		assertEquals( ((FileVFolder)folder).getName(), "usr" );
		for ( Iterator it = folder.listVFolders().iterator(); it.hasNext(); ) {
			final VFolder vfobj = (VFolder)it.next();
			assertTrue( new File( ((FileVFolder)vfobj).getPath() ).isDirectory() );
		}
		for ( Iterator it = folder.listVFiles().iterator(); it.hasNext(); ) {
			final VFile vf = (VFile)it.next();
			assertTrue( new File( ((FileVFolder)vf).getPath() ).isFile() );
		}
	}

}
