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



import com.steelypip.powerups.chararrays.CharArrayTools;
import com.steelypip.powerups.vfs.FixedConf;


/**
 * Implements a Codec appropriate for a filing system.
 */
public abstract class AbsCodec implements Codec {

 	private final char esc_char;
	protected final char forbidden_char;
	private final char separator;

	public AbsCodec( char separator ) {
		this.esc_char = FixedConf.VITEM_ESCAPE;					//	The character reserved for encoding.
		this.forbidden_char = FixedConf.VFOLDER_TERMINATOR;		//	The folder terminator.
		this.separator = separator;								//	The character reserved for separating nam from ext.
	}


	//	%UUUU -> hex

	private final String basic_decode( final String s ) {
		if ( s.indexOf( esc_char ) == -1 ) {
			return s;
		}
		final StringBuffer buff = new StringBuffer();
		for ( int i = 0; i < s.length(); i++ ) {
			final char ch = s.charAt( i );
			if ( ch != esc_char ) {
				final char[] h = new char[ 4 ];
				for ( int j = 0; j < 4; j++ ) {
					h[ j ] = s.charAt( ++i );
				}
				buff.append( CharArrayTools.fromHex( h ) );
			}
		}
		return buff.toString();
	}

	/**
	 * Slightly inefficient.
	 * @param s the string to be encoded
	 * @param separator the separator character to be protected
	 * @return the encoded (partial) file name
	 */
	private final String basic_encode( final String s, final char separator ) {
		if ( s.indexOf( separator ) == -1 && s.indexOf( esc_char ) == -1 && s.indexOf( forbidden_char ) == -1 ) {
			return s;
		}
		final StringBuffer buff = new StringBuffer();
		for ( int i = 0; i < s.length(); i++ ) {
			final char ch = s.charAt( i );
			if ( ch == esc_char || ch == separator || ch == forbidden_char ) {
				final char[] hex = CharArrayTools.toHex( ch );
				buff.append( esc_char );
				buff.append( hex, 0, 4 );
			} else {
				buff.append( ch );
			}
		}
		return buff.toString();
	}

	public String encode( final String nam, final String ext ) {
		if ( ext == null ) {
			return this.basic_encode( nam, separator );
		} else {
			return this.basic_encode( nam, separator ) + separator + this.basic_encode( ext, separator );
		}
	}

	public String[] decode( final String name, final boolean nam_wanted, final boolean ext_wanted, final String[] result ) {
		if ( name.indexOf( '/' ) != -1 ) throw new RuntimeException( "This never happens: " + name );	//	todo: remove once analysis is done properly. 
		final int n = name.lastIndexOf( separator );
		if ( n < 0 ) {
			if ( nam_wanted ) result[0] = this.basic_decode( name );
			if ( ext_wanted ) result[1] = null;
		} else {
			if ( nam_wanted ) result[0] = this.basic_decode( name.substring( 0, n ) );
			if ( ext_wanted ) result[1] = this.basic_decode( name.substring( n + 1 ) );
		}
		return result;
	}

	public String[] decode( String name ) {
		return decode( name, true, true, new String[ 2 ] );
	}

	public String decodeNam( final String name ) {
		return decode( name, true, false, new String[ 1 ] )[ 0 ];
	}

	public String decodeExt( final String name ) {
		return decode( name, false, true, new String[ 2 ] )[ 1 ];
	}

}
