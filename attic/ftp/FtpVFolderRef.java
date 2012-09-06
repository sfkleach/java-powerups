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
import org.openspice.tools.Print;

import com.steelypip.powerups.vfs.AbsVFolderRef;
import com.steelypip.powerups.vfs.VFileRef;
import com.steelypip.powerups.vfs.VFolder;
import com.steelypip.powerups.vfs.VFolderRef;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FileNameCodec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;

public class FtpVFolderRef extends AbsVFolderRef implements VFolderRef {

	protected Codec fileCodec() {
		return FileNameCodec.FILE_NAME_CODEC;
	}

	protected Codec folderCodec() {
		return FolderNameCodec.FOLDER_NAME_CODEC;
	}

	final String path;
	final FtpVVolume fvol;

	public FtpVFolderRef( final FtpVVolume vvol, final String path ) {
		this.path = path;
		this.fvol = vvol;
		if ( Print.wouldPrint( Print.VFS ) ) {
			Print.println( "new FTPVFolderRef: path = " + path );
			if ( path.charAt( path.length() - 1 ) != '/' ) throw new RuntimeException( "bah" );
		}
	}

	public VFileRef getVFileRef( String nam, String ext ) {
		final String name = FileNameCodec.FILE_NAME_CODEC.encode( nam,ext );
		final String full_path = FtpTools.fileName( this.path, name );
		return new FtpVFileRef( this.fvol, full_path );
	}

	public VFolderRef getVFolderRef( String nam, String ext ) {
		final String name = FolderNameCodec.FOLDER_NAME_CODEC.encode( nam,ext );
		return new FtpVFolderRef( this.fvol, FtpTools.folderName( this.path, name ) );
	}

	public boolean exists() {
		return this.getVFolder( ImmutableSetOfBoolean.EITHER, false ) != null;
	}

	public VFolder getVFolder( final SetOfBoolean if_exists, final boolean create_if_needed ) {
		final boolean folder_exists = FtpTools.folderExists( fvol, this.path );
		if ( !if_exists.contains( folder_exists ) ) {
			throw new Alert( folder_exists ? "Folder already exists" : "Folder does not exist (may be non-directory)" ).culprit( "folder", path ).mishap();
		} else if ( folder_exists ) {
			return FtpVFolder.uncheckedMake( fvol, path );
		} else {
			if ( create_if_needed ) {
				throw new RuntimeException( "tbd" ); 	//	todo: to be defined
			} else {
				return null;
			}
		}
	}

}
