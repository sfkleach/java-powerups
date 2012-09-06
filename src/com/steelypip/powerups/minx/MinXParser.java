package com.steelypip.powerups.minx;

import com.steelypip.powerups.exceptions.Alert;
import com.steelypip.powerups.repeaters.PeekableCharRepeater;

public class MinXParser< T extends MinX > {
	
	private int level = 0;
	final PeekableCharRepeater cucharin;
	
	private boolean pending_end_tag = false;
	private MinXBuilder< T > parent = null;
	private String tag_name = null;	
	
	public MinXParser( PeekableCharRepeater rep, MinXBuilder< T > parent ) {
		this.parent = parent;
		this.cucharin = rep;
	}

	private char nextChar() {
		return this.cucharin.nextChar();
	}
		
	private char peekChar() {
		return this.cucharin.peekChar();
	}
	
	private void mustReadChar( final char ch_want ) {
		if ( this.cucharin.hasNextChar( ch_want ) ) {
			this.cucharin.skip();
		} else {
			if ( this.cucharin.hasNext() ) {
				throw new Alert( "Unexpected character" ).culprit( "Wanted", "" + ch_want ).culprit( "Received", "" + this.cucharin.peekChar() );
			} else {
				throw new Alert( "Unexpected end of stream" );
			}			
		}
	}
	
	private void eatWhiteSpace() {
		while ( this.cucharin.hasNext() ) {
			final char ch = this.cucharin.nextChar();
			if ( ch == '#' && this.level == 0 ) {
				//	EOL comment.
				while ( this.cucharin.hasNext() && this.cucharin.nextChar() != '\n' ) {
				}
			} else if ( ! Character.isWhitespace( ch ) ) {
				this.cucharin.putBackChar( ch );
				return;
			}
		}
	}
	
	private static boolean is_name_char( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	private String readName() {
		final StringBuilder name = new StringBuilder();
		while ( this.cucharin.hasNext() ) {
			final char ch = this.cucharin.nextChar();
			if ( is_name_char( ch ) ) {
				name.append( ch );
			} else {
				this.cucharin.putBackChar( ch );
				break;
			}
		}
		return name.toString();
	}
	
	private String readAttributeValue() {
		final StringBuilder attr = new StringBuilder();
		final char q = this.nextChar();
		if ( q != '"' && q != '\'' ) throw new Alert( "Attribute value not quoted" ).culprit( "Character", q );
		for (;;) {
			char ch = this.nextChar();
			if ( ch == q ) break;
			if ( ch == '&' ) {
				final StringBuilder esc = new StringBuilder();
				for (;;) {
					ch = this.nextChar();
					if ( ch == ';' ) break;
					//std::cout << "char " << ch << endl;
					esc.append( ch );
					if ( esc.length() > 4 ) {
						throw new Alert( "Malformed escape" ).culprit( "Sequence", esc );
					}
				}
				if ( esc.length() >= 2 && esc.charAt( 0 ) == '#' ) {
					try {
						final int n = Integer.parseInt( esc.toString().substring( 1 ) );
						attr.append( (char)n );
					} catch ( NumberFormatException e ) {
						throw new Alert( e, "Unexpected numeric sequence after &#" ).culprit( "Sequence", esc );
					}
				} else {
					final String e = esc.toString();
					if ( "lt".equals( e ) ) {
						attr.append( '<' );
					} else if ( "gt".equals( e ) ) {
						attr.append( '>' );
					} else if ( "amp".equals(  e  ) ) {
						attr.append( '&' );
					} else if ( "quot".equals( e ) ) {
						attr.append( '"' );
					} else if ( "apos".equals(  e ) ) {
						attr.append( '\'' );
					} else {
						throw new Alert( "Unexpected escape sequence after &" ).culprit( "Sequence", esc );
					}
				}
			} else {
				attr.append( ch );
			}
		}
		return attr.toString();
	}	
	
	
	private void processAttributes() {
		//	Process attributes
		for (;;) {
			this.eatWhiteSpace();
			char c = peekChar();
			if ( c == '/' || c == '>' ) break;
			final String key = this.readName();
			
			this.eatWhiteSpace();
			this.mustReadChar( '=' );
			this.eatWhiteSpace();
			final String value = this.readAttributeValue();
			this.parent.put( key, value );
		}
	}
	
	
	private void read() {
		
		if ( this.pending_end_tag ) {
			this.parent.endTag( this.tag_name );
			this.pending_end_tag = false;
			this.level -= 1;
			return;
		}
			
		this.eatWhiteSpace();
		
		if ( !this.cucharin.hasNext() ) {
			return;
		}
		
		this.mustReadChar( '<' );
			
		char ch = this.nextChar();
		
		if ( ch == '/' ) {
			final String end_tag = this.readName();
			this.eatWhiteSpace();
			this.mustReadChar( '>' );
			this.parent.endTag( end_tag );
			this.level -= 1;
			return;
		} else if ( ch == '!' ) {
			if ( '-' != nextChar() || '-' != nextChar() ) throw new Alert( "Invalid XML comment syntax" );
			ch = nextChar();
			for (;;) {
				char prev_ch = ch;
				ch = nextChar();
				if ( prev_ch == '-' && ch == '-' ) break;
			}
			if ( '>' != this.nextChar() ) throw new Alert( "Invalid XML comment syntax" );
			this.read();
			return;
		} else if ( ch == '?' ) {
			for (;;) {
				char prev = ch;
				ch = this.nextChar();
				if ( prev == '?' && ch == '>' ) break;
			}
			this.read();
			return;
		} else {
			//cout << "Ungetting '" << ch << "'" << endl;
			this.cucharin.putBackChar( ch );
		}
		
		this.tag_name = this.readName();
		
		this.parent.startTag( this.tag_name );
		this.processAttributes();
		
		//	Commenting out the original C++ callback.
		//	this.parent.startTagClose( this.tag_name );
		
		this.eatWhiteSpace();
		
		
		ch = nextChar();
		if ( ch == '/' ) {
			this.mustReadChar( '>' );
			this.pending_end_tag = true;
			this.level += 1;
			//this->parent.endTag( name ); <- this code replaced
			return;
		} else if ( ch == '>' ) {
			this.level += 1;
			return;
		} else {
			throw new Alert( "Invalid continuation" );
		}
				
	}
	
	public void readElement() { 
		for (;;) {
			this.read();
			if ( this.level == 0 ) break;
		}
	}

} // namespace
