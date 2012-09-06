package com.steelypip.powerups.minx;

import java.util.LinkedList;

import com.steelypip.powerups.exceptions.Alert;
import com.steelypip.powerups.minx.MinXFactory;

public class MinXBuilder< T extends MinX > {
	
	private final MinXFactory< T > minx_factory;
	
	public MinXBuilder( MinXFactory< T > f ) {
		this.minx_factory = f;
	}
	
//	public MinXBuilder() {
//		this.minx_factory =
//	}
	
	private T minx = null;
	private final LinkedList< T > stack = new LinkedList< T >();
	private T last_ended = null;

	public void startTag( final String tag_name ) {
		this.stack.add( this.minx );
		this.minx = this.minx_factory.newMinX( tag_name );
	}
	
	//	Commented out the original entry points inherited from C++.
//	public abstract void startTagOpen( String tag_name );
//	public abstract void startTagClose( String tag_name );
	
	public void endTag( final String tag_name ) {
		if ( this.stack.isEmpty() ) {
			throw new Alert( "Excess closeTag detected" ).culprit( "Tag name", tag_name );
		} else {
			this.last_ended = this.minx;
			this.minx = this.stack.removeLast();
			if ( this.minx != null ) this.minx.add( this.last_ended );
		}
	}

	public void put( final String key, final String value ) {
		if ( this.minx == null ) {
			throw new Alert( "Put called before startTag" ).culprit( "Attribute name", key ).culprit(  "Attribute value", value );
		}
		this.minx.put( key, value );
	}
	
	public T makeElement() {
		if ( this.minx == null ) {
			//	We null out the last_ended to ensure that there are no shared references to
			//	any elements in the MinX tree.
			final T result = this.last_ended;
			this.last_ended = null;
			return result;
		} else {
			throw new Alert( "Missing closing tag" );
		}
	}
	
}
