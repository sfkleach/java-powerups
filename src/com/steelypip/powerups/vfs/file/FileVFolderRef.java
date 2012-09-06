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
package com.steelypip.powerups.vfs.file;


import com.steelypip.powerups.booleans.SetOfBoolean;
import com.steelypip.powerups.vfs.*;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FileNameCodec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;
import com.steelypip.powerups.vfs.tools.VFolderView;

import java.io.File;

public class FileVFolderRef extends AbsVFolderRef implements VFolderRef {

	protected Codec fileCodec() {
		return FileNameCodec.FILE_NAME_CODEC;
	}

	protected Codec folderCodec() {
		return FolderNameCodec.FOLDER_NAME_CODEC;
	}

	private final File file;

	FileVFolderRef( File file ) {
		this.file = file;
	}

	public VFolder getVFolder( final SetOfBoolean if_exists, final boolean create_if_needed ) {
		if ( this.file.isDirectory() ) {
			return FileVFolder.make( this.file );
		} else if ( FixedConf.TRACK_BACK_ENABLED ) {
			return VFolderView.makeVFolderRefWithTrackBack( this.file ).getVFolder( if_exists, create_if_needed );
		} else {
			final VFolderRef vfr = VFolderView.make( this.file );
			if ( vfr == null ) return null;
			return vfr.getVFolder( if_exists, create_if_needed );
		}
	}


	public VFileRef getVFileRef( final String nam, final String ext ) {
		final String name = FileNameCodec.FILE_NAME_CODEC.encode( nam, ext );
		return new FileVFileRef( new File( this.file, name ) );
	}

	public VFolderRef getVFolderRef( String nam, String ext ) {
		if ( VFolderView.isArchiveExt( ext ) ) {
//			if ( Print.wouldPrint( Print.VFS ) ) Print.println( "Constructing archive folderref: ext = " + ext );
			final String name = FileNameCodec.FILE_NAME_CODEC.encode( nam, ext );
			final File file = new File( this.file, name );
			return VFolderView.make( ext, file );
		} else {
//			if ( Print.wouldPrint( Print.VFS ) ) Print.println( "Constructing normal folderref; ext = " + ext );
			final String name = FolderNameCodec.FOLDER_NAME_CODEC.encode( nam, ext );
			return new FileVFolderRef( new File( this.file, name ) );
		}
	}

	public boolean exists() {
		return this.file.exists();
	}

}
