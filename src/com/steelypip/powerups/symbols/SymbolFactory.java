package com.steelypip.powerups.symbols;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SymbolFactory {
	
	static class Ref extends WeakReference< Symbol > {
		
		private Ref( final Symbol referent ) {
			super( referent );
		}

	}
	
	static class CleanUp {
		final SymbolFactory factory;

		private CleanUp( SymbolFactory factory ) {
			super();
			this.factory = factory;
		}
		
		@Override
		protected void finalize() throws Throwable {
			this.factory.cleanUp();
		}
		
	}
	
	private final Map< Object, Ref > map = new HashMap< Object, Ref >();
	private WeakReference< CleanUp > cleanUp = null;
	private long count = 0;
	
	public void setCleanUpRequired() {
		if ( this.cleanUp == null ) {
			this.cleanUp = new WeakReference< CleanUp >( new CleanUp( this ) );
		}
	}
	
	private void cleanUp() {
		synchronized ( this.map ) {
			final Iterator< Map.Entry< Object, Ref > > it = this.map.entrySet().iterator();
			while ( it.hasNext() ) {
				final Map.Entry< Object, Ref > me = it.next();
				if ( me.getValue().get() == null ) it.remove();
			}
		}
		this.cleanUp = null;
	}
	
	private Symbol addNewSymbol( final Object key ) {
		final Symbol s = new Symbol( this, key );
		this.map.put( key, new Ref( s ) );
		this.count += 1;
		return s;		
	}
	
	public Symbol makeSymbol( final Object key ) {
		synchronized ( this.map ) {
			final Ref w = this.map.get( key );
			if ( w == null ) {
				return this.addNewSymbol( key );
			} else {
				final Symbol s = w.get();
				if ( s != null ) return s;
				return this.addNewSymbol( key );
			}
		}
	}
	
	public Symbol newAnonSymbol( final String printString ) {
		return new Symbol( this, printString + this.count++ );
	}
	
	public Symbol newAnonSymbol() {
		return newAnonSymbol( "anonymous" );
	}
	
	public boolean isInterned( final Symbol s ) {
		final WeakReference< Symbol > w = this.map.get( s.getKey() );
		return w != null && w.get() == s;
	}
	
	private static Map< String, SymbolFactory > SYMBOL_FACTORY_TABLE = (
		new HashMap< String, SymbolFactory >()
	);
	
	static public SymbolFactory makeSymbolFactory( final String key ) {
		final SymbolFactory s = SYMBOL_FACTORY_TABLE.get( key );
		if ( s != null ) return s;
		final SymbolFactory t = new SymbolFactory();
		SYMBOL_FACTORY_TABLE.put( key, t );
		return t;
	}
}
