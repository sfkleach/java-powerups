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
import com.steelypip.powerups.booleans.SetOfBoolean;
import com.steelypip.powerups.repeaters.CharRepeater;
import com.steelypip.powerups.repeaters.ReaderCharRepeater;

public abstract class AbsVFileRef implements VFileRef {

	public Reader readContents() {
		final VFile vfile = this.getVFile( ImmutableSetOfBoolean.EITHER, false );
		if ( vfile == null ) return null;
		return vfile.readContents();
	}
	
	public CharRepeater iterator() {
		return new ReaderCharRepeater( this.readContents() );
	}

	public final VItem getVItem( final SetOfBoolean if_exists, final boolean create_if_needed ) {
		return this.getVFile( if_exists, create_if_needed );
	}


	public final boolean isVFileRef() {
		return true;
	}

	public final boolean isVFolderRef() {
		return false;
	}
	
}
