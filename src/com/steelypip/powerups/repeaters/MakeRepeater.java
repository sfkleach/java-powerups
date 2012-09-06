package com.steelypip.powerups.repeaters;

import java.util.Iterator;

import com.steelypip.powerups.exceptions.Alert;

public class MakeRepeater {

	static abstract class SimpleAbstractRepeater< T > implements Repeater< T > {

		public abstract boolean hasNext();
		public abstract T next();

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public void skip() {
			if ( this.hasNext() ) this.next();  
		}
		
	}

	static class ZeroShotIterator< T > extends SimpleAbstractRepeater< T > {
		
		public boolean hasNext() {
			return false;
		}

		public T next() {
			throw new Alert( "Trying to read from an empty stream" );
		}

		public T next( T value_if_at_end ) {
			return value_if_at_end;
		}
		
	}

	static class OneShotIterator< T > extends SimpleAbstractRepeater< T > {
		private boolean has_next = true;
		private T object;

		OneShotIterator( final T _object ) {
			this.object = _object;
		}

		public boolean hasNext() {
			return this.has_next;
		}

		//	We null out the fields that have been used to permit early garbage collection.
		public T next() {
			if ( this.has_next ) {
				this.has_next = false;
				final T x = this.object;
				this.object = null;
				return x;				
			} else {
				throw new Alert( "Reading from exhausted repeater" );
			}
		}

		public T next( T value_if_at_end ) {
			if ( this.has_next ) {
				this.has_next = false;
				final T x = this.object;
				this.object = null;
				return x;				
			} else {
				return value_if_at_end;
			}
		}
	}

	static class TwoShotIterator< T > extends SimpleAbstractRepeater< T > {

		private int n = 0;
		private T x;
		private T y;

		public TwoShotIterator( final T x, final T y ) {
			this.x = x;
			this.y = y;
		}

		public boolean hasNext() {
			return n < 2;
		}

		/**
		 * Note that we null out x & y to assist with garbage collection.
		 */
		public T next() {
			if ( n == 0 ) {
				final T z = x;
				this.x = null;
				n += 1;
				return z;
			} else if ( n == 1 ) {
				final T z = y;
				this.y = null;
				n += 1;
				return z;
			} else {
				throw new Alert( "Reading from exhausted repeater" );
			}
		}

		public T next( T value_if_at_end ) {
			if ( n == 0 ) {
				final T z = x;
				this.x = null;
				n += 1;
				return z;
			} else if ( n == 1 ) {
				final T z = y;
				this.y = null;
				n += 1;
				return z;
			} else {
				return value_if_at_end;
			}
		}

	}

	public static < T > Iterator< T > make0() {
		return new ZeroShotIterator< T >();
	}

	public static < T > Iterator< T > make1( final T x ) {
		return new OneShotIterator< T >( x );
	}

	public static < T > Iterator< T > make2( final T x, final T y ) {
		return new TwoShotIterator< T >( x, y );
	}

	static class MultiRepeater< T > implements Repeater< T > {

		final Iterator< Iterator < T > > itit;
		Iterator< T > it = new ZeroShotIterator< T >();

		public MultiRepeater( final Iterator< Iterator < T > > itit ) {
			this.itit = itit;
		}

		public boolean hasNext() {
			if ( this.it.hasNext() ) return true;
			if ( !this.itit.hasNext() ) return false;
			this.it = (Iterator< T >)itit.next();
			return this.hasNext();
		}

		public T next() {
			if ( this.it.hasNext() ) {
				return this.it.next();
			} else if ( this.itit.hasNext() ) {
				this.it = (Iterator< T >)itit.next();
				return this.next();
			} else {
				throw new Alert( "Repeater exhausted" );
			}
		}

		public void remove() {
			this.it.remove();
		}
		
		public void skip() {
			if ( this.hasNext() ) this.next();  
		}

		public T next( T value_if_at_end ) {
			if ( this.it.hasNext() ) {
				return this.it.next();
			} else if ( this.itit.hasNext() ) {
				this.it = (Iterator< T >)itit.next();
				return this.next();
			} else {
				return value_if_at_end;
			}
		}

	}
	

}