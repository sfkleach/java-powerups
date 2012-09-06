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

import com.steelypip.powerups.vfs.AbsVVolume;
import com.steelypip.powerups.vfs.VFolderRef;
import com.steelypip.powerups.vfs.VItem;

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;

public class ZipVVolume extends AbsVVolume {

	final ZipFile zip_file;
	final List< VItem > zip_entries = new ArrayList< VItem >();

	public ZipVVolume( ZipFile zip_file ) {
		this.zip_file = zip_file;
		for ( Enumeration enm = this.zip_file.entries(); enm.hasMoreElements(); ) {
			final ZipEntry e = (ZipEntry)enm.nextElement();
			zip_entries.add( (VItem)e );
		}
	}

	public VFolderRef getRootVFolderRef() {
		return new ZipVFolderRef( this, "" );
	}

}
