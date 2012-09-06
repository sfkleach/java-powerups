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

import com.steelypip.powerups.booleans.ImmutableSetOfBoolean;



public abstract class AbsVVolume implements VVolume {

	public abstract VFolderRef getRootVFolderRef();

	public VFolder getRootVFolder() {
		return this.getRootVFolderRef().getVFolder( ImmutableSetOfBoolean.EITHER, false );
	}

	public VFolderRef getVFolderRefFromPath( final String path ) {
		return this.getRootVFolderRef().getVFolderRefFromPath( path );
	}

	public VFileRef getVFileRefFromPath( String path ) {
		return this.getRootVFolderRef().getVFileRefFromPath( path );
	}

	public VFolder getVFolderFromPath( String path ) {
		return this.getRootVFolderRef().getVFolderFromPath( path );
	}

	public VFile getVFileFromPath( String path ) {
		return this.getRootVFolderRef().getVFileFromPath( path );
	}

	public VItem getVItemFromPath( String path ) {
		return this.getRootVFolderRef().getVItemFromPath( path );
	}

}
