package com.steelypip.powerups.shell;

import java.io.File;


import com.steelypip.powerups.exceptions.Alert;


public abstract class StdCmdLineProcessor extends CmdLineProcessor {

	@Override
	public void processRest( CmdArgs rest ) {
		while ( rest.hasNext() ) {
			final File f = new File( rest.next() );
			if ( f.exists() ) {
				this.processFileArg( f );
			} else {
				throw new Alert( "File does not exist" ).culprit( "File", f );
			}
		}
	}

	public abstract void processFileArg( File f );
	
}
