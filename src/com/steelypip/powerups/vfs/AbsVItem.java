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

public abstract class AbsVItem implements VItem {

	public boolean hasExt( final String ext1 ) {
		final String ext2 = this.getExt();
		return ext1 == null ? ext2 == null : ext1.equals( ext2 );
	}

	public void delete() {
		throw new UnsupportedOperationException();
	}

	public void setNamExt( String nam, String ext ) {
		throw new UnsupportedOperationException();
	}

}
