package com.steelypip.powerups.repeaters;

import com.steelypip.powerups.exceptions.Alert;

public class MakePeekableCharRepeater {
	
	static class StdPeekableCharRepeater extends AbsCharRepeater implements PeekableCharRepeater {
		private final CharRepeater rep;
		private int previous = -1;

		private StdPeekableCharRepeater( final CharRepeater rep ) {
			super();
			this.rep = rep;
		}

		public char peekChar() {
			if ( this.previous < 0 ) {
				final char ch = this.rep.nextChar();
				this.previous = ch;
				return ch;
			} else {
				return (char)this.previous;
			}
		}
		
		public char peekChar( final char value_if_at_end ) {
			if ( this.previous < 0 ) {
				if ( this.rep.hasNext() ) {
					final char ch = this.rep.nextChar();
					this.previous = ch;
					return ch;
				} else {
					return value_if_at_end;
				}
			} else {
				return (char)this.previous;
			}
		}

		public Character peek() {
			return this.peekChar();
		}
		
		public Character peek( final Character value_if_at_end ) {
			return this.peekChar( value_if_at_end );
		}

		public char nextChar() {
			if ( this.previous < 0 ) {
				return this.rep.nextChar();
			} else {
				final char ch = (char)this.previous;
				this.previous = -1;
				return ch;
			}
		}

		public boolean hasNext() {
			return this.previous >= 0 || this.rep.hasNext();
		}
		
		public boolean hasNextChar( char ch ) {
			return this.hasNext() && this.peekChar() == ch;
		}


		public Character next() {
			if ( this.previous < 0 ) {
				return this.rep.next();
			} else {
				final char ch = (char)this.previous;
				this.previous = -1;
				return ch;
			}
		}

		@Override
		public PeekableCharRepeater makePeekable() {
			return this;
		}

		public void putBack( Character item ) {
			this.putBackChar( item );
		}

		public void putBackChar( char previous ) {
			if ( this.previous < 0 ) {
				this.previous = previous;
			} else {
				throw new Alert( "Stream cannot accept putBack because previous operation was not a next()" ).culprit( "Putting back", previous );
			}
		}
		
	}

	public static PeekableCharRepeater makePeekableCharRepeater( final CharRepeater rep ) {
		if ( rep instanceof PeekableCharRepeater ) {
			return (PeekableCharRepeater)rep;
		} else {
			return new StdPeekableCharRepeater( rep );
		}

	}
	
	public static PeekableCharRepeater newPeekableCharRepeater( final CharRepeater rep ) {
		return new StdPeekableCharRepeater( rep );
	}
	
}
