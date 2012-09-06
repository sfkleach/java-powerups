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
package com.steelypip.powerups.vfs.zip;

import com.steelypip.powerups.booleans.SetOfBoolean;
import com.steelypip.powerups.exceptions.Alert;
import com.steelypip.powerups.vfs.AbsVFileRef;
import com.steelypip.powerups.vfs.VFile;

import java.util.zip.ZipEntry;

public class ZipVFileRef extends AbsVFileRef {

	final String path;
	final ZipVVolume zvol;

	public ZipVFileRef( ZipVVolume zvol, String path ) {
		this.path = path;
		this.zvol = zvol;
	}

	public final VFile getVFile( final SetOfBoolean if_exists, final boolean create_if_needed ) {
		final ZipEntry ze = this.zvol.zip_file.getEntry( this.path );
		final boolean folder_exists = ze != null && !ze.isDirectory();
		if ( !if_exists.contains( folder_exists ) ) {
			throw new Alert( folder_exists ? "File already exists" : "File does not exist (may be directory)" ).culprit( "folder", path );
		} else if ( folder_exists ) {
			return ZipVFile.uncheckedMake( this.zvol, this.path );
		} else {
			if ( create_if_needed ) {
				throw new UnsupportedOperationException();
			} else {
				return null;
			}
		}
	}

	public final boolean exists() {
		final ZipEntry e = this.zvol.zip_file.getEntry( this.path );
		return e != null && !e.isDirectory();
	}

}
