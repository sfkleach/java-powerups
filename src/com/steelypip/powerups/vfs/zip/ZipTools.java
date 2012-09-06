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

import com.steelypip.powerups.vfs.FixedConf;

public abstract class ZipTools {

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
		} else if ( n == path.length() -1 ) {
			return getName( path.substring( 0, n ) );
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

}
