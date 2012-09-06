package com.steelypip.powerups.exceptions;

import java.io.PrintWriter;

import com.steelypip.powerups.io.StringPrintWriter;

/**
 * This class has to be final so that other classes can specialise the culprit method.
 *
 */
public final class Culprit {
	final String desc;
	final Object arg;
	final boolean print;

	Culprit( final String desc1, final Object arg1, final boolean _print ) {
		this.desc = desc1;
		this.arg = arg1;
		this.print = _print;
	}

	Culprit( final String desc1, final Object arg1 ) {
		this( desc1, arg1, false );
	}

	private String keepShort( final StringConverter p, final Object x, final int mx_len, final int filemxlen ) {
		final int mxlen = mx_len > 5 ? mx_len : 5;
		final String s = p.convertToString( x );
		if ( s.length() > mxlen ) {
			if ( s.charAt( 0 ) == '/' && s.length() < filemxlen ) {
				return s;
			} else {
				return s.substring( 0, mxlen - 4 ) + " ...";
			}
		} else {
			return s;
		}
	}

	static final int maxlen = 256;
	static final int filemaxlen = 256;

	private String keepShort( final StringConverter p, Object x ) {
		return keepShort( ( this.print ? SysStringConverter.SYS_STRING_CONVERTER : p ), x, maxlen, filemaxlen );
	}

	static final int min_pad_width = 8;
	
	void output( final PrintWriter pw, final StringConverter p ) {
		final String d = this.desc.toUpperCase();
		pw.print( d );
		for ( int i = d.length(); i < min_pad_width; i++ ) {
			pw.print( " " );
		}
		pw.print( " : " );
		pw.println( this.keepShort( p, this.arg ) );
	}
	
	String asString( final StringConverter p ) {
		final String d = this.desc.toUpperCase();
		final StringPrintWriter pw = new StringPrintWriter();
		this.output( pw, p );
		return pw.toString();
		
	}

}
