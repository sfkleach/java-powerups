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

import java.io.Reader;

import com.steelypip.powerups.booleans.ImmutableSetOfBoolean;

public abstract class AbsVFolder extends AbsVItem implements VFolder {

	public VFolder newVFolder( String nam, String ext ) {
		return this.getVFolderRef( nam, ext ).getVFolder( ImmutableSetOfBoolean.ONLY_FALSE, true );
	}

	public VFile newVFile( String nam, String ext, Reader contents ) {
		return this.getVFileRef( nam, ext ).getVFile( ImmutableSetOfBoolean.ONLY_FALSE, true );
	}

	public VFolder getVFolder( final String nam, String ext ) {
		return this.getVFolderRef( nam, ext ).getVFolder( ImmutableSetOfBoolean.EITHER, false );
	}

	public VFile getVFile( String nam, String ext ) {
		return this.getVFileRef( nam, ext ).getVFile( ImmutableSetOfBoolean.EITHER, false );
	}

	public VFolderRef getVFolderRef( String nam, String ext ) {
		return this.getVFolderRef().getVFolderRef( nam, ext );
	}

	public VFileRef getVFileRef( String nam, String ext ) {
		return this.getVFolderRef().getVFileRef( nam, ext );
	}

}
