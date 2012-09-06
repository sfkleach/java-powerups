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

import org.openspice.alert.Alert;
import org.openspice.tools.Print;
import org.openspice.tools.ImmutableSetOfBoolean;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.steelypip.powerups.vfs.*;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FolderNameCodec;


import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.IOException;

public class FtpVFolder extends PathAbsVFolder implements VFolder {

	protected Codec codec() {
		return FolderNameCodec.FOLDER_NAME_CODEC;
	}

	protected String getPath() {
		return this.path;
	}

	protected String getName() {
		return FtpTools.getName( this.path );
	}

	protected String getParentPath() {
		return FtpTools.getParentPath( this.path );
	}

	public static final FtpVFolder make( final FtpVVolume fvol, final String path ) {
		if ( FtpTools.folderExists( fvol, path ) ) {
			return new FtpVFolder( fvol, path );
		} else {
			throw new Alert( "Cannot verify folder exists" ).culprit( "path", path ).mishap();
		}
	}

	public static final FtpVFolder uncheckedMake( final FtpVVolume fvol, final String path ) {
		return new FtpVFolder( fvol, path );
	}

	private FtpVVolume fvol;
	private String path;

	private FtpVFolder( final FtpVVolume vvol, final String path ) {
		this.fvol = vvol;
		this.path = path;
		if ( Print.wouldPrint( Print.VFS ) ) {
			Print.println( "new FTPVFolder: path = " + path );
			if ( path.charAt( path.length() - 1) != '/' ) throw new RuntimeException( "bah" );
		}
	}

	public VFolder newVFolder( String nam, String ext ) {
		return this.getVFolderRef().getVFolderRef( nam, ext ).getVFolder( ImmutableSetOfBoolean.ONLY_FALSE, true );
	}

	public VFile newVFile( final String nam, final String ext, Reader contents ) {
		return this.getVFolderRef().getVFileRef( nam, ext ).getVFile( ImmutableSetOfBoolean.ONLY_FALSE, true );
	}

	public void delete() {
		throw new RuntimeException( "tbd" ); 	//	todo: to be defined
	}

	private List list( final boolean want_folders, final boolean want_files ) {
//		System.err.println( "folders = " + want_folders );
//		System.err.println( "files = " + want_files );
		try {
			final FTPClient ftpc = this.fvol.getConnectedFTPClient();
			final FTPFile[] files = ftpc.listFiles( this.path );
//			{
//				System.err.println( "List of files is " + files.length + " long" );
//				for ( int n = 0; n < files.length; n++ ) {
//					System.err.println( "["+n+"]. " + files[n] + " " + ( files[n].isDirectory() ? "d" : "-" ) + ( files[n].isFile() ? "f" : "-" ) );
//				}
//			}
			final List answer = new ArrayList();
			for ( int i = 0; i < files.length; i++ ) {
				final FTPFile file = files[ i ];
				if ( want_folders && file.isDirectory() ) {
//					System.err.println( "adding FOLDER " + file );
					answer.add( new FtpVFolder( this.fvol, FtpTools.folderName( this.path, file.getName() ) ) );
				} else if ( want_files && ( file.isFile() || file.isSymbolicLink() ) ) {
//					System.err.println( "adding FILE " + file );
					answer.add( FtpVFile.uncheckedMake( this.fvol, FtpTools.fileName( this.path, file.getName() ) ) );
				}
			}
			return answer;
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public List listVFolders() {
		return this.list( true, false );
	}

	public List listVItems() {
		return this.list( true, true );
	}

	public List listVFiles() {
		return this.list( false, true );
	}

	public VFolderRef getVFolderRef() {
		return new FtpVFolderRef( this.fvol, this.path );
	}

}
