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
import com.steelypip.powerups.vfs.AbsVFolderRef;
import com.steelypip.powerups.vfs.VFileRef;
import com.steelypip.powerups.vfs.VFolder;
import com.steelypip.powerups.vfs.VFolderRef;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FileNameCodec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;

import java.util.zip.ZipEntry;

public class ZipVFolderRef extends AbsVFolderRef {

	final String path;
	final ZipVVolume zvol;

	public ZipVFolderRef( final ZipVVolume vvol, final String path ) {
		this.zvol = vvol;
		this.path = path;
	}

	protected Codec fileCodec() {
		return FileNameCodec.FILE_NAME_CODEC;
	}

	protected Codec folderCodec() {
		return FolderNameCodec.FOLDER_NAME_CODEC;
	}

	public VFolder getVFolder( final SetOfBoolean if_exists, final boolean create_if_needed ) {
//		if ( Print.wouldPrint( Print.VFS ) ) Print.println( "Trying to create ZipVFolder: path = " + path );
		if ( this.path.length() == 0 ) {
			return ZipVFolder.uncheckedMake( this.zvol, this.path );
		} else {
			final ZipEntry ze = this.zvol.zip_file.getEntry( this.path );
//			if ( Print.wouldPrint( Print.VFS ) ) Print.println( ze == null ? " ... failed" : " ... ok" );
			final boolean folder_exists = ze != null && ze.isDirectory();
			if ( !if_exists.contains( folder_exists ) ) {
				throw new Alert( folder_exists ? "Folder already exists" : "Folder does not exist (may be non-directory)" ).culprit( "folder", path );
			} else if ( folder_exists ) {
				return ZipVFolder.uncheckedMake( this.zvol, this.path );
			} else {
				if ( create_if_needed ) {
					throw new UnsupportedOperationException();
				} else {
					return null;
				}
			}
		}
	}

	public VFileRef getVFileRef( final String nam, final String ext ) {
		final String name = ZipTools.fileName( this.path, FileNameCodec.FILE_NAME_CODEC.encode( nam, ext ) );
		return new ZipVFileRef( this.zvol, name );		
	}

	public VFolderRef getVFolderRef( final String nam, final String ext ) {
		final String name = ZipTools.folderName( this.path, FolderNameCodec.FOLDER_NAME_CODEC.encode( nam, ext ) );
		return new ZipVFolderRef( this.zvol, name );
	}

	public boolean exists() {
		final ZipEntry e = this.zvol.zip_file.getEntry( this.path );
		return e != null && e.isDirectory();
	}

}

