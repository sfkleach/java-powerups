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
package com.steelypip.powerups.vfs.codec;

public interface Codec {

//	/**
//	 * Composes path + NAM + EXT components into a path
//	 * @param previous
//	 * @param nam
//	 * @param ext
//	 * @return
//	 */
//	String encodePath( String previous, String nam, String ext );

	/**
	 * Composes NAM and EXT components into an item.
	 * @param nam
	 * @param ext
	 * @return
	 */
	String encode( String nam, String ext );

	/**
	 * Decomposes an item into its NAM + EXT components.
	 * @param name
	 * @return
	 */
	String[] decode( String name );
	String decodeNam( String name );
	String decodeExt( String name );

//	/**
//	 * Decomposes a path into a NAM + EXT + path
//	 */
//	String[] decodePath( String path );
//	String decodePathNam( String path );
//	String decodePathExt( String path );
//	String decodePathPath( String path );

}
