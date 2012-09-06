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
import com.steelypip.powerups.vfs.PathAbsVFile;
import com.steelypip.powerups.vfs.VFile;
import com.steelypip.powerups.vfs.VFileRef;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FileNameCodec;

import java.io.*;
import java.util.zip.ZipEntry;

public class ZipVFile extends PathAbsVFile implements VFile {

	final ZipVVolume zvol;
	final String path;

	private ZipVFile( ZipVVolume zvol, String path ) {
		this.zvol = zvol;
		this.path = path;
	}

	public static final ZipVFile make( final ZipVVolume zvol, final String path ) {
		final ZipEntry ze = zvol.zip_file.getEntry( path );
		if ( ze != null && !ze.isDirectory() ) {
			return new ZipVFile( zvol, path );
		} else {
			throw new Alert( ze == null ? "Entry does not exist" : "Entry is a directory" ).culprit( "path", path ).culprit( "zip file", zvol.zip_file );
		}
	}

	static final ZipVFile uncheckedMake( final ZipVVolume zvol, final String path ) {
		return new ZipVFile( zvol, path );
	}

	protected Codec codec() {
		return FileNameCodec.FILE_NAME_CODEC;
	}

	public String getPath() {
		return this.path;
	}

	public String getName() {
		return ZipTools.getName( this.path );
	}

	protected String getParentPath() {
		return ZipTools.getParentPath( this.path );
	}

	public Reader readContents() {
		return new InputStreamReader( this.inputStreamContents() );
	}

	public Writer writeContents() {
		throw new UnsupportedOperationException();
	}

	public InputStream inputStreamContents() {
		final ZipEntry e = this.zvol.zip_file.getEntry( this.path );
		try {
			return zvol.zip_file.getInputStream( e );
		} catch ( IOException e1 ) {
			throw new RuntimeException( e1 );
		}
	}

	public OutputStream outputStreamContents() {
		throw new UnsupportedOperationException();
	}

	public VFileRef getVFileRef() {
		return new ZipVFileRef( this.zvol, this.path );
	}

}
