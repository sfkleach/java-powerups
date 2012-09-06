package com.steelypip.powerups.repeaters;

import java.io.IOException;
import java.io.Reader;

import com.steelypip.powerups.exceptions.Alert;

public class ReaderCharRepeater extends AbsCharRepeater implements PeekableCharRepeater {

	static private final int NO_BUFFERED_CHARACTER = -2;
	static private final int END_OF_STREAM = -1;

	/**
	 * cuchar is the current character. It is either
	 * 	1.	a codepoint that is >= 0, representing a buffered character.
	 * 	2.	the special value NO_BUFFERED_CHARACTER, representing no buffered character.
	 * 	3.	the special value END_OF_STREAM, representing that the end of the stream has been reached.
	 * 		N.B. END_OF_STREAM is deliberately chosen to be the SAME value as returned by
	 * 		java.io.Reader.read at end of file.
	 */
	private int cuchar = NO_BUFFERED_CHARACTER;
	private final Reader reader;
	
	public ReaderCharRepeater( final Reader reader ) {
		super();
		this.reader = reader;
	}
	
	/**
	 * Guaranteed to return a new object.
	 */
	public static ReaderCharRepeater newReaderCharIterator( final Reader reader ) {
		return new ReaderCharRepeater( reader );
	}
	
	/**
	 * Might return a cached.
	 */
	public static ReaderCharRepeater makeReaderCharIterator( final Reader reader ) {
		return newReaderCharIterator( reader );
	}
	
	public static ReaderCharRepeater makeReaderCharIterator( final Reader reader, final boolean newCopy ) {
		return newCopy ? newReaderCharIterator( reader ) : makeReaderCharIterator( reader );
	}

	private void fillCucharBuffer() {
		if ( this.cuchar == NO_BUFFERED_CHARACTER ) {
			try {
				this.cuchar = this.reader.read();
			} catch ( IOException e ) {
				throw new Alert( e, "Reader threw exception" ).culprit( "Reader", this.reader ).culprit( "Exception", e );
			}
		}
	}
	
	public boolean hasNext() {
		this.fillCucharBuffer();
		return this.cuchar >= 0;
	}

	public char nextChar() {
		if ( this.cuchar < 0 ) {
			this.fillCucharBuffer();
			if ( this.cuchar == END_OF_STREAM ) {
				return 0;
			}
		}
		final char c = (char)this.cuchar;
		this.cuchar = NO_BUFFERED_CHARACTER;
		return c;
	}

	public Character peek() {
		return this.peekChar();
	}

	public char peekChar( final char value_if_at_end ) {
		this.fillCucharBuffer();
		if ( this.cuchar >= 0 ) return (char)this.cuchar;
		return value_if_at_end;
	}

	public char peekChar() {
		this.fillCucharBuffer();
		if ( this.cuchar >= 0 ) return (char)this.cuchar;
		throw new Alert( "Reading (peeking) past end of stream" );
	}

	public boolean hasNextChar( char ch ) {
		return this.hasNext() && this.peekChar() == ch;
	}
	
	@Override
	public void skip() {
		this.fillCucharBuffer();
		this.cuchar = NO_BUFFERED_CHARACTER;
	}

	public Character peek( Character value_if_at_end ) {
		return this.peekChar( value_if_at_end );
	}

	public void putBack( final Character item ) {
		this.putBackChar( item );
	}

	public void putBackChar( final char previous ) {
		if ( this.cuchar == NO_BUFFERED_CHARACTER ) {
			this.cuchar = previous;
		} else {
			throw new Alert( "Putback not after a next()" ).culprit( "Putting back", previous );
		}
	}
	
}
