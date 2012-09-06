package com.steelypip.powerups.chararrays;

public class CharArrayTools {

	public static final char[] hex_digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static final char[] toHex( final char ch ) {
		final char[] h = new char[ 4 ];
		h[0] = hex_digits[ ch & 0xF ];
		h[1] = hex_digits[ ( ch >> 8 ) & 0xF ];
		h[2] = hex_digits[ ( ch >> 16 ) & 0xF ];
		h[3] = hex_digits[ ( ch >> 24 ) & 0xF ];
		return h;
	}

	public static final char fromHex( final char[] h ) {
		return (char)(
			( h[ 0 ] - '0' ) +
			( ( h[ 1 ] - '0' ) << 8 ) +
			( ( h[ 1 ] - '0' ) << 16 ) +
			( ( h[ 1 ] - '0' ) << 24 )
		);
	}

	private static char[] CBUF = null;

	public static final char[] tmpBuffer() {
		if ( CBUF == null ) {
			CBUF = new char[ 1 << 13 ];
		}
		return CBUF;
	}

}
