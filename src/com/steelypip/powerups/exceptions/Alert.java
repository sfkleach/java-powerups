package com.steelypip.powerups.exceptions;

import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;

import com.steelypip.powerups.io.StringPrintWriter;

import static java.lang.System.*;

public class Alert extends AlertBase implements StringConverter {

	private static final long serialVersionUID = -1375586262765726163L;
	
	private String explanation;

	public Alert( final Throwable t, final String _complaint, final String _explanation ) {
		super( _complaint, t );
		this.explanation = _explanation;
	}

	public Alert( final Throwable t, final String _complaint ) {
		this( t, _complaint, null );
	}
	
	public Alert( final String _complaint, final String _explanation ) {
		this( null, _complaint, _explanation );
	}

	public Alert( final String _snafu_message ) {
		this( _snafu_message, null );
	}

	public String getComplaint() {
		return this.getMessage();
	}
	
	public String convertToString( final Object x ) {
		return "" + x;
	}

	public Alert culprit( final String desc, final Object arg ) {
		this.add( new Culprit( desc, arg ) );
		return this;
	}

	public Alert culprit( final String desc, final int arg ) {
		return this.culprit( desc, new Integer( arg ) );
	}

	private int arg_count = 1;
	public Alert culprit( final List< Culprit > list ) {
		for ( Iterator< Culprit > it = list.iterator(); it.hasNext(); ) {
			this.culprit( "arg(" + this.arg_count++ + ")", it.next() );
		}
		return this;
	}

	public Alert hint( final String hint_text ) {
		return this.culprit( "hint", hint_text );
	}
	
	public void reportTo( final PrintWriter pw ) {
		pw.print( "ALERT : " ); 
		pw.println( this.getMessage() );
		if ( this.explanation != null ) {
			pw.print( "BECAUSE : " );
			pw.println( this.explanation );
		}
		for ( Culprit c : this.culprits() ) {
			c.output( pw, this );
		}
		pw.println( "" );
		pw.flush();
	}

	public void report() {
		this.reportTo( new PrintWriter( err ) );
	}
	
	public String toString() {
		final StringPrintWriter pw = new StringPrintWriter();
		this.reportTo(  pw );
		return pw.toString();
	}


	//	---- This section just deals with the statics ----

	public static Alert unreachable() {
		throw unreachable( (Throwable)null );
	}

	public static Alert unreachable( final Throwable t ) {
		throw unreachable( "Internal error", t );
	}

	public static Alert unreachable( final String msg ) {
		throw unreachable( msg, null );
	}

	public static Alert unreachable( final String msg, final Throwable t ) {
		throw new Alert( t, msg, "Some supposedly unreachable code has been executed" );
	}

	public static Alert unimplemented( final String msg ) {
		final Alert alert = new Alert( null, "Internal error", "An unimplemented feature is required" );
		alert.culprit( "message", msg );
		throw alert;
	}

	public static Alert unimplemented() {
		return unimplemented( "unimplemented" );
	}

}

