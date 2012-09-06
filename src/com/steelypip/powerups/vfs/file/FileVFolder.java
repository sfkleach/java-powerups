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

import com.steelypip.powerups.vfs.PathAbsVFolder;
import com.steelypip.powerups.vfs.VFolder;
import com.steelypip.powerups.vfs.VFolderRef;
import com.steelypip.powerups.vfs.VItem;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileVFolder extends PathAbsVFolder implements VFolder {

	protected Codec codec() {
		return FolderNameCodec.FOLDER_NAME_CODEC;
	}

	public String getPath() {
		return this.file.getPath();
	}

	public String getName() {
		return this.file.getName();
	}

	protected String getParentPath() {
		return this.file.getParent();
	}

	private File file;

	private FileVFolder( final File file ) {
		this.file = file;
	}

	public static final FileVFolder make( final File file ) {
		if ( !file.isDirectory() ) {
			throw new RuntimeException( "directory needed: " + file );
		}
		return new FileVFolder( file );
	}

	public static final FileVFolder uncheckedMake( final File file ) {
		return new FileVFolder( file );
	}

	public List< VItem > listVFolders() {
		final File[] files = (
			this.file.listFiles(
				new FileFilter() {
					public boolean accept( File pathname ) {
						return pathname.isDirectory();
					}
				}
			)
		);
		final List< VItem > list = new ArrayList< VItem >();
		for ( int i = 0; i < files.length; i++ ) {
			list.add( new FileVFolder( files[ i ] ) );
		}
		return list;
	}

	public List< VItem > listVFiles() {
		final File[] files = (
			this.file.listFiles(
				new FileFilter() {
					public boolean accept( File pathname ) {
						return pathname.isFile();
					}
				}
			)
		);
		final List< VItem > list = new ArrayList< VItem >();
		for ( int i = 0; i < files.length; i++ ) {
			list.add( FileVFile.uncheckedMake( files[ i ] ) );
		}
		return list;
	}

	public List< VItem > listVItems() {
		final File[] files = (
			this.file.listFiles(
				new FileFilter() {
					public boolean accept( File pathname ) {
						return pathname.isFile() || pathname.isDirectory();
					}
				}
			)
		);
		final List< VItem > list = new ArrayList< VItem >();
		for ( int i = 0; i < files.length; i++ ) {
			final File file = files[ i ];
			list.add( file.isFile() ? (VItem)FileVFile.uncheckedMake( file ) : (VItem)FileVFolder.uncheckedMake( file ) );
		}
		return list;
	}

	public VFolderRef getVFolderRef() {
		return new FileVFolderRef( this.file );
	}

}
