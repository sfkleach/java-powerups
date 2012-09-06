/**
 *	JSpice, an Open Spice interpreter and library.
 *	Copyright (C) 2003, Stephen F. K. Leach
 *
 * 	This program is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation; either version 2 of the License, or
 * 	(at your option) any later version.
 *
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 * 	along with this program; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.steelypip.powerups.vfs.ftp;

import org.openspice.tools.SetOfBoolean;
import org.openspice.tools.ImmutableSetOfBoolean;
import org.openspice.alert.Alert;

import com.steelypip.powerups.vfs.AbsVFileRef;
import com.steelypip.powerups.vfs.VFile;
import com.steelypip.powerups.vfs.VFileRef;

public class FtpVFileRef extends AbsVFileRef implements VFileRef {

	final FtpVVolume fvol;
	final String path;

	public FtpVFileRef( FtpVVolume fvol, String path ) {
		this.fvol = fvol;
		this.path = path;
	}


	public boolean exists() {
		return this.getVFile( ImmutableSetOfBoolean.EITHER, false ) != null;
	}

	public VFile getVFile( final SetOfBoolean if_exists, final boolean create_if_needed ) {
		final boolean file_exists = FtpTools.fileExists( this.fvol, this.path );
		if ( !if_exists.contains( file_exists ) ) {
			throw new Alert( file_exists ? "File already exists" : "File does not exist (may be directory)" ).culprit( "folder", path ).mishap();
		} else if ( file_exists ) {
			return FtpVFile.uncheckedMake( fvol, path );
		} else {
			if ( create_if_needed ) {
				throw new RuntimeException( "tbd" ); 	//	todo: to be defined
			} else {
				return null;
			}
		}
	}

}
