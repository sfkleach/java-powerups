package com.steelypip.powerups.minx;

import java.io.Reader;

import com.steelypip.powerups.exceptions.Alert;
import com.steelypip.powerups.repeaters.AbsRepeater;
import com.steelypip.powerups.repeaters.PeekableRepeater;
import com.steelypip.powerups.repeaters.ReaderCharRepeater;

public abstract class MinXFactory< T extends MinX > {
	
	public abstract T newMinX( final String name );
	
	public T newMinX() {
		return this.newMinX( "." );
	}
	
	private T read( final ReaderCharRepeater r ) {
		final MinXBuilder< T > b = new MinXBuilder< T >( this );
		new MinXParser< T >( r, b ).readElement();
		return b.makeElement();
	}
	
	
	private T read( final Reader r ) {
		return this.read( new ReaderCharRepeater( r ) );
	}
	
	
	public T readMinX( final Reader r ) {
		final T mx = this.read( r );
		if ( mx == null ) {
			throw new Alert( "Unexpected end of stream" );
		}
		return mx;
	}
	
	public T readMinX( final Reader r, T def ) {
		final T mx = this.read( r );
		return mx == null ? def : mx;
	}
	
	public PeekableRepeater< T > readMinXRepeater( final Reader r ) {
		return new ReadRepeater< T >( this, r );
	}
	
	static class ReadRepeater< T extends MinX > extends AbsRepeater< T > implements PeekableRepeater< T > {
		
		private final MinXFactory< T > factory;
		private final ReaderCharRepeater cucharin;

		//	When the stream is exhausted, this flag is set to true. If done is set
		//	current is guaranteed to be null.
		private boolean done = false;

		//	This is either null, meaning the next element has not been read, or non-null
		//  and holding the next element.
		private T current = null;
		
		
		final MinXBuilder< T > builder;
		final MinXParser< T > parser;
		
		public ReadRepeater( final MinXFactory< T > factory, final Reader r ) {
			this.factory = factory;
			this.cucharin = new ReaderCharRepeater( r );
			this.builder = new MinXBuilder< T >( this.factory );
			this.parser = new MinXParser< T >( this.cucharin, this.builder );
		}
		
		private T read1() {
			this.parser.readElement();
			return this.builder.makeElement();
		}
		
		//	After fill, either current has the next element or done is true and current is null. 
		private void fill() {
			if ( ! this.done && this.current == null ) {
				this.current = this.read1();
				if ( this.current == null ) this.done = true;
			}
		}
		
		//	Requires that we are not done.
		private void doneCheck() {
			if ( this.done ) {
				throw new Alert( "Expected end of stream" );
			}
		}
		
		//	Returns the current value, which must not be null, and then nullifies it.
		private T flush() {
			T x = this.current;
			this.current = null;
			return x;
		}

		public boolean hasNext() {
			if ( this.done ) {
				return false;
			} else if ( this.current != null ) { 
				return true;
			} else {
				this.fill();
				return !this.done;	
			}
		}

		public T next() {
			if ( this.current != null ) {
				return this.flush();
			} else {
				this.fill();
				this.doneCheck();
				return this.flush();
			}
		}

		public T peek() {
			if ( this.current != null ) {
				return this.current;
			} else {
				this.fill();
				this.doneCheck();
				return this.current;
			}
		}

		public T peek( T value_if_at_end ) {
			if ( this.current != null ) {
				return this.current;
			} else {
				this.fill();
				return this.done ? value_if_at_end : this.current;
			}
		}

		public void putBack( T prev ) {
			if ( this.current == null ) {
				this.current = prev;
				this.done = false;
			} else {
				throw new Alert( "Unexpected putback" );
			}
		}

		
	}
}
