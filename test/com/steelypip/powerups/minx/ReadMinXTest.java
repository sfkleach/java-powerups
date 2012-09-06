package com.steelypip.powerups.minx;


import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.repeaters.PeekableRepeater;

public class ReadMinXTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadMinX() {
		final MinX m = MinX.readMinX( new StringReader( "<alpha/>" ) );
		Assert.assertEquals( "alpha", m.getElementName() );
		Assert.assertEquals( 0, m.size() );
	}
	
	
	@Test
	public void testMultiRead() {
		Reader r = new StringReader( "<alpha/><beta/>" );
		{
			final MinX m = MinX.readMinX( r );
			Assert.assertEquals( "alpha", m.getElementName() );
			Assert.assertEquals( 0, m.size() );
		}
		{
			final MinX n = MinX.readMinX( r );
			Assert.assertEquals( "beta", n.getElementName() );
			Assert.assertEquals( 0, n.size() );
		}
	}
	
	@Test
	public void testNestedRead() {
		Reader r = new StringReader( "<alpha><beta/></alpha>" );
		final MinX m = MinX.readMinX( r );
		Assert.assertEquals( "alpha", m.getElementName() );
		Assert.assertEquals( 1, m.size() );
		final MinX b = m.getChild( 0 );
		Assert.assertEquals( "beta", b.getElementName() );
		Assert.assertEquals( 0, b.size() );
	}
	
	@Test
	public void testAttributes() {
		Reader r = new StringReader( "<alpha left=\"right\" \t up=\'down\'><beta/></alpha>" );
		final MinX m = MinX.readMinX( r );
		Assert.assertEquals( "alpha", m.getElementName() );
		Assert.assertEquals( 1, m.size() );
		Assert.assertEquals( "right", m.get( "left" ) );
		Assert.assertEquals( "down", m.get( "up" ) );
		Assert.assertNull( m.get( "notattribute" ) );
		final MinX b = m.getChild( 0 );
		Assert.assertEquals( "beta", b.getElementName() );
		Assert.assertEquals( 0, b.size() );
	}
	
	@Test
	public void testEmptyStream() {
		Reader r = new StringReader( "    " );
		final MinX m = MinX.readMinX( r, null );
		Assert.assertNull( m );
		MinX.readMinX( r, null );	//	It should be OK to keep trying.
	}
	
	@Test
	public void testRepeater() {
		Reader r = new StringReader( "<alpha/><beta/><gamma/>" );
		PeekableRepeater< MinX > mxs = MinX.readMinXRepeater( r );
		MinX alpha = mxs.next();
		Assert.assertEquals( "alpha", alpha.getElementName() );
		MinX beta = mxs.next();
		Assert.assertEquals( "beta", beta.getElementName() );
		MinX gamma = mxs.next();
		Assert.assertEquals( "gamma", gamma.getElementName() );
		Assert.assertFalse( mxs.hasNext() );
	}
	
}
