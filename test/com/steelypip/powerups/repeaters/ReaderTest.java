package com.steelypip.powerups.repeaters;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.steelypip.powerups.exceptions.Alert;

import junit.framework.TestCase;

public class ReaderTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	
	@Test
	public void testReader() {
		PeekableCharRepeater r = new ReaderCharRepeater( new StringReader( "abcde" ) );
		Assert.assertTrue( r.hasNext() );
		Assert.assertEquals( 'a', r.peekChar() );
		Assert.assertEquals( 'a', r.peekChar() );
		Assert.assertEquals( 'a', r.nextChar() );
		Assert.assertTrue( r.hasNext() );
		Assert.assertEquals( 'b', r.nextChar() );
		Assert.assertEquals( 'c', r.nextChar() );
		Assert.assertEquals( 'd', r.nextChar() );
		Assert.assertEquals( 'e', r.nextChar() );
		Assert.assertFalse( r.hasNext() );
	}
	
	@Test(expected = com.steelypip.powerups.exceptions.Alert.class)
	public void testEndOfStream() {
		try {
			PeekableCharRepeater r = new ReaderCharRepeater( new StringReader( "" ) );
			r.next();
			Assert.fail();
		} catch ( Alert _ ) {
		}
	}
	
	@Test
	public void testPushBack() {
		ReaderCharRepeater r = new ReaderCharRepeater( new StringReader( "x" ) );
		char x = r.nextChar();
		Assert.assertEquals( 'x', x );
		r.putBackChar( 'y' );
		Assert.assertEquals( 'y', r.nextChar() );
	}
}
