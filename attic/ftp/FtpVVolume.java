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
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

import com.steelypip.powerups.vfs.AbsVVolume;
import com.steelypip.powerups.vfs.VFolderRef;
import com.steelypip.powerups.vfs.VVolume;

import java.net.*;
import java.io.IOException;

public class FtpVVolume extends AbsVVolume implements VVolume {

	final URL root_url;
	final FTPClient ftp_client = new FTPClient();

	private static final URL toURL( final String s ) {
		try {
			return new URL( s );
		} catch ( MalformedURLException e ) {
			throw new RuntimeException( e );
		}
	}

	public FtpVVolume( final String url ) {
		this( toURL( url ) );
	}

	public FtpVVolume( final URL url ) {
		if ( "ftp".equals( url.getProtocol() ) ) {
			this.root_url = url;
		} else {
			this.root_url = null;
			throw new Alert( "Not an FTP URL" ).culprit(  "tools", url ).mishap();
		}
	}


	public VFolderRef getRootVFolderRef() {
		return new FtpVFolderRef( this, this.root_url.getPath() );
	}

	public FTPClient getFTPClient() {
		return this.ftp_client;
	}

	private void reconnect( final FTPClient ftpc ) throws IOException {
		Print.println( Print.VFS, "Reconnecting ..." );
		final String user_info = this.root_url.getUserInfo();
		final int n = user_info.indexOf( ':' );
		final String user = n >= 0 ? user_info.substring( 0, n ) : user_info;
		final String pw = n >= 0 ? user_info.substring( n + 1 ) : "";
		if ( Print.wouldPrint( Print.VFS ) ) {
			Print.println( "User ID : " + user );
			Print.println( "Password: " + pw );
			Print.println( "Host    : " + root_url.getHost() );
		}
		ftpc.connect( this.root_url.getHost() );

		final boolean ok = ftpc.login( user, pw );
		if ( !ok ) {
			new Alert( "Cannot connect to FTP server" ).culprit( "host", root_url.getHost() ).mishap();
		}
		if ( !ftpc.setFileType( FTP.BINARY_FILE_TYPE ) ) {
			new Alert( "Cannot set file type" ).mishap();
		}
	}

	public FTPClient getConnectedFTPClient() {
		Print.println( Print.VFS, "Trying to connect to FTP server ...." );
		final FTPClient ftpc = this.getFTPClient();
		try {
			if ( !ftpc.isConnected() ) {
				Print.println( Print.VFS, "Not connected - trying to reconnect" );
				this.reconnect( ftpc );
			}
			ftpc.noop();
		} catch ( final FTPConnectionClosedException e ) {
			try {
				ftpc.disconnect();
				this.reconnect( ftpc );
			} catch ( IOException e1 ) {
				throw new RuntimeException( e1 );
			}
		} catch ( final IOException e ) {
			throw new RuntimeException( e );
		}
//		try {
			ftpc.enterLocalPassiveMode();
//			ftpc.setSoTimeout( 30 * 1000 );			//	Set 30 second timeout.
//		} catch ( SocketException e ) {
//			throw new RuntimeException( e );
//		}
		return ftpc;
	}

}
