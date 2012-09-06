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

import com.steelypip.powerups.exceptions.Alert;

import com.steelypip.powerups.vfs.PathAbsVFile;
import com.steelypip.powerups.vfs.VFile;
import com.steelypip.powerups.vfs.VFileRef;
import com.steelypip.powerups.vfs.codec.Codec;
import com.steelypip.powerups.vfs.codec.FileNameCodec;

import java.io.*;

public class FileVFile extends PathAbsVFile implements VFile {

	protected Codec codec() {
		return FileNameCodec.FILE_NAME_CODEC;
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

	private FileVFile( final File file ) {
		this.file = file;
	}

	public static final FileVFile make( final File file ) {
		if ( file.exists() ) {
			return new FileVFile( file );
		} else {
			throw new Alert( "File does not exist" ).culprit(  "file", file );
		}
	}

	public static final FileVFile uncheckedMake( final File file ) {
		return new FileVFile( file );
	}


	public Reader readContents() {
		try {
			return new FileReader( this.file );
		} catch ( final FileNotFoundException e ) {
			throw new Alert( "File not found" ).culprit( "file", this.file );
		}
	}

	public Writer writeContents() {
		try {
			return new FileWriter( this.file );
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public InputStream inputStreamContents() {
		try {
			return new FileInputStream( this.file );
		} catch ( final FileNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}

	public OutputStream outputStreamContents() {
		try {
			return new FileOutputStream( this.file );
		} catch ( FileNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}

	public VFileRef getVFileRef() {
		return new FileVFileRef( this.file );
	}

}
