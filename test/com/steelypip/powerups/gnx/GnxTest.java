package com.steelypip.powerups.gnx;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import com.steelypip.powerups.minx.MinX;

import junit.framework.TestCase;

public class GnxTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testWrite() {
		String s = "<foo a=\"b\"><bar/></foo>";
		Reader r = new StringReader( s );
		MinX g = MinX.readMinX( r );
		StringWriter w = new StringWriter();
		g.write( new PrintWriter( w ) );
		String t = w.toString();
		assertEquals( s, t );
		g.writeWithIndentation();
	}

}
