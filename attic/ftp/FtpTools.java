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

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.steelypip.powerups.exceptions.Alert;
import com.steelypip.powerups.vfs.FixedConf;

import java.io.IOException;

public abstract class FtpTools {

	public static final String folderName( final String path, final String name ) {
		return path + name + FixedConf.VFOLDER_TERMINATOR;
	}

	public static final String fileName( final String path, final String name ) {
		return path + name;
	}

	public static final String getName( final String path ) {
		final int n = path.lastIndexOf( FixedConf.VFOLDER_TERMINATOR );
		if ( n < 0 ) {
			return path;
		} else if ( n == path.length() - 1 ) {
			final int k = path.lastIndexOf( FixedConf.VFOLDER_TERMINATOR, n - 1 );
			if ( k < 0 ) {
				return path.substring( 0, n );
			} else {
				return path.substring( k + 1, n );
			}
		} else {
			return path.substring( n + 1 );
		}
	}

	public static final String getParentPath( final String path ) {
		final int n = path.indexOf( FixedConf.VFOLDER_TERMINATOR );
		if ( n < 0 ) {
			return "";
		} else {
			return path.substring( 0, n );
		}
	}

	public static final boolean folderExists( final FTPClient ftpc, final String path ) {
		try {
			return ftpc.changeWorkingDirectory( path );
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}

	public static final boolean folderExists( final FtpVVolume fvol, final String path ) {
		return folderExists( fvol.getConnectedFTPClient(), path );
	}

	public static final boolean fileExists( final FtpVVolume fvol, final String path ) {
		return fileExists( fvol.getConnectedFTPClient(), path );
	}
	
	public static final boolean fileExists( final FTPClient ftpc, final String path ) {
		boolean exists = false;
		try {
			final FTPFile[] files = ftpc.listFiles( path );
			if ( files.length == 1 ) {
				exists = true;
			} else if ( files.length > 1 ) {
				throw new Alert( "Cannot determine this path is a file" ).culprit(  "path", path );
			}
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
		return exists;
	}

}
