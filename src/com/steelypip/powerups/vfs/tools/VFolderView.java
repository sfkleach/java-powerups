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
package com.steelypip.powerups.vfs.tools;

import com.steelypip.powerups.vfs.FixedConf;
import com.steelypip.powerups.vfs.VFileRef;
import com.steelypip.powerups.vfs.VFolderRef;
import com.steelypip.powerups.vfs.codec.FileNameCodec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;
import com.steelypip.powerups.vfs.zip.ZipVVolume;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class VFolderView {

	public static final VFolderRef make( final String ext, final File file ) {
		if ( FixedConf.ZIP_EXT.equals( ext ) ) {
			try {
				return new ZipVVolume( new ZipFile( file ) ).getRootVFolderRef();
			} catch ( IOException e ) {
				throw new RuntimeException( e );
			}
		} else {
			return null;
		}
	}

	public static final boolean isArchiveExt( final String ext ) {
		return FixedConf.ZIP_EXT.equals( ext );
	}

	public static final VFolderRef make( final File file ) {
		return make( FileNameCodec.FILE_NAME_CODEC.decodeExt( file.getName() ), file );
	}

	public static final VFileRef makeVFileRefWithTrackBack( final File file ) {
		final LinkedList track_back = new LinkedList();
		final File found = find( file, track_back );
		if ( found == null ) return null;
		return makeArchiveVFileRef( found, track_back );
	}

	public static final VFolderRef makeVFolderRefWithTrackBack( final File file ) {
		final LinkedList track_back = new LinkedList();
		final File found = find( file, track_back );
		if ( found == null ) return null;
		return makeArchiveVFolderRef( found, track_back );
	}

	private static File find( File file, final LinkedList track_back ) {
		for(;;) {
			if ( file.exists() ) {
				return file;
			} else {
				final File parent = file.getParentFile();
				if ( parent == null ) return null;
				final String name = file.getName();
				track_back.addFirst( name );
				file = parent;
			}
		}
	}

	private static final VFolderRef makeArchiveVFolderRef( final File found, final LinkedList track_back ) {
		VFolderRef ref = VFolderView.make( found );
		for ( Iterator it = track_back.iterator(); ref != null && it.hasNext(); ) {
			final String name = (String)it.next();
			final String[] namext = FolderNameCodec.FOLDER_NAME_CODEC.decode( name );
			ref = ref.getVFolderRef( namext[0], namext[1] );
		}
		return ref;
	}

	private static final VFileRef makeArchiveVFileRef( final File found, final LinkedList track_back ) {
		VFolderRef ref = VFolderView.make( found );
		for ( Iterator it = track_back.iterator(); ref != null && it.hasNext(); ) {
			final String name = (String)it.next();
			if ( it.hasNext() ) {
				final String[] namext = FolderNameCodec.FOLDER_NAME_CODEC.decode( name );
				ref = ref.getVFolderRef( namext[0], namext[1] );
			} else {
				final String[] namext = FileNameCodec.FILE_NAME_CODEC.decode( name );
				return ref.getVFileRef( namext[0], namext[1] );
			}
		}
		return null;
	}

}
