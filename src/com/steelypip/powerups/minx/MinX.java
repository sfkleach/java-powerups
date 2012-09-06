package com.steelypip.powerups.minx;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.steelypip.powerups.io.StringPrintWriter;
import com.steelypip.powerups.repeaters.PeekableRepeater;

public class MinX implements Iterable< MinX >{
	
	private String element_name;
	private Map< String, String > attributes = new TreeMap< String, String >();
	private List< MinX > children = new ArrayList< MinX >();	//	Not quite right. We could do with a more compact representation.
	
	public MinX( final String name ) {
		assert name != null;
		this.element_name = name.intern();
	}
	
	public List< MinX > allChildren() {
		return new ArrayList< MinX >( this.children );
	}

	public void add( MinX child ) {
		assert child != null;
		this.children.add( child );
	}
		
	public void clear() {
		children.clear();
	}

	public boolean contains( MinX o ) {
		return children.contains( o );
	}

	public MinX getChild( int index ) {
		return children.get( index );
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	public Iterator< MinX > iterator() {
		return children.iterator();
	}

	public MinX setChild( int index, MinX element ) {
		return children.set( index, element );
	}

	public int size() {
		return children.size();
	}
	
	public Set< String > keySet() {
		return this.attributes.keySet();
	}
	
	public String get( final String k ) {
		return this.attributes.get( k );
	}
	
	public String get( final String k, final String def ) {
		final String v = this.attributes.get( k );
		return v == null ? def : v;
	}
	
	public void put( final String k, final String v ) {
		this.attributes.put( k.intern(), v );
	}

	public String getElementName() {
		return element_name;
	}

	public static MinX readMinX() {
		return readMinX( new InputStreamReader( System.in ) );
	}
	
	public static MinX readMinX( final Reader r ) {
		return StdMinXFactory.STD_MIN_X_FACTORY.readMinX( r );
	}
	
	public static MinX readMinX( MinX def ) {
		return StdMinXFactory.STD_MIN_X_FACTORY.readMinX( new InputStreamReader( System.in ), def );
	}
	
	public static MinX readMinX( final Reader r, MinX def ) {
		return StdMinXFactory.STD_MIN_X_FACTORY.readMinX( r, def );
	}
	
	public static PeekableRepeater< MinX > readMinXRepeater( final Reader r ) {
		return StdMinXFactory.STD_MIN_X_FACTORY.readMinXRepeater( r );
	}
	
	public static MinX valueOf( final String s ) {
		try {
			return MinX.readMinX( new StringReader( s ) );
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}
	

	
	private static abstract class Indenter {
		abstract void indent();
		abstract void tab();
		abstract void untab();
		abstract void newline();
	}
	
	private static final class StdIndenter extends Indenter{
		final PrintWriter pw;
		int indent_level = 0;
		int tab_step = 4; 
		
		public StdIndenter( PrintWriter pw ) {
			super();
			this.pw = pw;
		}

		@Override
		void indent() {
			for ( int n = 0; n < this.indent_level; n++ ) {
				pw.write( ' ' );
			}
		}
		
		@Override
		void tab() {
			this.indent_level += this.tab_step;
		}
		
		@Override
		void untab() {
			this.indent_level -= this.tab_step;
		}
		
		@Override
		void newline() {
			pw.println();
		}
	}
	
	private static final class NullIndenter extends Indenter {

		@Override
		void indent() {			
		}

		@Override
		void tab() {
		}

		@Override
		void untab() {
		}

		@Override
		void newline() {
		}
	
	}
	
	private static final class MinXWriter {
		
		final PrintWriter pw;
		final Indenter indenter;
		
		public MinXWriter( PrintWriter pw, Indenter indenter ) {
			this.pw = pw;
			this.indenter = indenter;
		}
		
		public void write( MinX x ) {
			indenter.indent();
			pw.print( "<" );
			pw.print( x.getElementName() );
			for ( String key : x.keySet() ) {
				pw.print( " " );
				pw.print( key );
				pw.print( "=\"" );
				final String v = x.get( key );
				for ( int n = 0; n < v.length(); n++ ) {
					final char ch = v.charAt( n );
					if ( ch == '"' ) {
						pw.print( "&quot;" );
					} else if ( ch == '\'' ) {
						pw.print(  "&apos;" );
					} else if ( ch == '<' ) {
						pw.print( "&lt;" );
					} else if ( ch == '>' ) {
						pw.print( "&gt;" );
					} else if ( ch == '&' ) {
						pw.print( "&amp;" );
					} else if ( ' ' <= ch && ch <= '~' ) {
						pw.print( ch );
					} else {
						pw.print( "&#" );
						pw.print( (int)ch );
						pw.print( ';' );
					}
				}
				pw.print( "\"" );
			}
			if ( x.isEmpty() ) {
				pw.print( "/>" );
				indenter.newline();
			} else {
				pw.print( ">" );
				indenter.newline();
				indenter.tab();
				for ( MinX y : x ) {
					this.write( y );
				}
				indenter.untab();
				indenter.indent();
				pw.format( "</%s>", x.getElementName() );
				indenter.newline();
			}
		}
		

	}
	
	public void write( final PrintWriter pw ) {
		new MinXWriter( pw, new NullIndenter() ).write( this );
		pw.flush();
	}
	
	public void write() {
		PrintWriter pw = new PrintWriter( System.out );
		this.write( pw );
		pw.flush();
	}
	
	public void writeWithIndentation( final PrintWriter pw ) {
		new MinXWriter( pw, new StdIndenter( pw ) ).write( this );
		pw.flush();
	}
	
	public void writeWithIndentation() {
		PrintWriter pw = new PrintWriter( System.out );
		new MinXWriter( pw, new StdIndenter( pw ) ).write( this );
		pw.flush();
	}
	
	@Override
	public String toString() {
		StringPrintWriter pw = new StringPrintWriter();
		this.write( pw );
		return pw.toString();
	}
	

	
}
