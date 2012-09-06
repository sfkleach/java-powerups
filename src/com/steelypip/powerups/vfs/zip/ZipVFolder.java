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

import com.steelypip.powerups.exceptions.Alert;
import com.steelypip.powerups.vfs.*;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.zip.ZipEntry;

public class ZipVFolder extends PathAbsVFolder implements VFolder {

	protected Codec codec() {
		return FolderNameCodec.FOLDER_NAME_CODEC;
	}

	protected String getPath() {
		return this.path;
	}

	protected String getName() {
		return ZipTools.getName( this.path );
	}

	protected String getParentPath() {
		return ZipTools.getParentPath( this.path );
	}

	final ZipVVolume zvol;
	final String path;

	private ZipVFolder( ZipVVolume zvol, String path ) {
		this.zvol = zvol;
		this.path = path;
	}

	public static final ZipVFolder make( final ZipVVolume zvol, final String path ) {
		final ZipEntry ze = zvol.zip_file.getEntry( path );
		if ( ze != null && ze.isDirectory() ) {
			return new ZipVFolder( zvol, path );
		} else {
			throw new Alert( ze == null ? "Entry does not exist" : "Entry is not a directory" ).culprit( "path", path ).culprit( "zip file", zvol.zip_file );
		}
	}

	static final ZipVFolder uncheckedMake( final ZipVVolume zvol, final String path ) {
		return new ZipVFolder( zvol, path );
	}

	private List< VItem > listAll( final boolean vfiles, final boolean vfolders ) {
		final List< VItem > list = new ArrayList< VItem >();
		for ( Iterator< VItem > it = this.zvol.zip_entries.iterator(); it.hasNext(); ) {
			final ZipEntry ze = (ZipEntry)it.next();
			final String ze_path = ze.getName();
//			if ( Print.wouldPrint( Print.VFS ) ) Print.println( "Name of zip entry = " + ze_path );
			if ( ze_path.length() > this.path.length() && ze_path.startsWith( this.path ) ) {
				final String p = ze_path.substring( this.path.length() );
				final int n = p.indexOf( FixedConf.VFOLDER_TERMINATOR );
//				System.err.println( "p = " + p );
//				System.err.println( "path = " + this.path );
//				System.err.println( "n = " + n );
//				System.err.println( "is_folder " + ze.isDirectory() );
				if ( n == -1 || n == p.length() - 1 ) {
					//	Got a member of the directory.
					if ( ze.isDirectory() ) {
//						if ( Print.wouldPrint( Print.VFS ) ) Print.println( "folder: " + ze_path );
						if ( vfolders ) list.add( new ZipVFolder( this.zvol, ze_path ) );
					} else {
//						if ( Print.wouldPrint( Print.VFS ) ) Print.println( "ordinary file: " + ze_path );
						if ( vfiles ) list.add( ZipVFile.make( this.zvol, ze_path ) );
					}
				} else {
//					if ( Print.wouldPrint( Print.VFS ) ) Print.println( "other: " + ze_path );
				}
			}
		}
		return list;
	}

	public List< VItem > listVFolders() {
		return this.listAll( false, true );
	}

	public List< VItem > listVFiles() {
		return this.listAll( true, false );
	}

	public List< VItem > listVItems() {
		return this.listAll( true, true );
	}

	public VFolderRef getVFolderRef() {
		return new ZipVFolderRef( this.zvol, this.path );
	}

}
