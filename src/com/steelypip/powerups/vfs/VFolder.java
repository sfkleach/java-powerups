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
package com.steelypip.powerups.vfs;

import java.util.List;
import java.io.Reader;

public interface VFolder extends VItem {

	List< VItem > listVFolders();

	List< VItem > listVFiles();

	List< VItem > listVItems();

	VFolder newVFolder( String nam, String ext );

	VFile newVFile( String nam, String ext, Reader contents );

	VFolder getVFolder( String nam, String ext );

	VFile getVFile( String nam, String ext );

	VFolderRef getVFolderRef( String nam, String ext );

	VFileRef getVFileRef( String nam, String ext );

	VFolderRef getVFolderRef();

}
