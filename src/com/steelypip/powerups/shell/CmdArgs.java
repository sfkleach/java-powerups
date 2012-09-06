package com.steelypip.powerups.shell;

import java.util.Iterator;
import java.util.LinkedList;

import com.steelypip.powerups.exceptions.Alert;


public abstract class CmdArgs implements Iterator< String > {
	
	public abstract void check();
	public abstract boolean hasNext();
	public abstract String next();

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public void clear() {
		while ( this.hasNext() ) {
			this.next();
		}
	}

	public static class NoArgs extends CmdArgs {

		@Override
		public void check() {
			//	All is OK.
		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public String next() {
			throw new Alert( "Missing argument for command line option" );
		}

	}

	
	
	public static class LimitedOptionalArg extends CmdArgs {
		
		private LinkedList< String > args;

		public LimitedOptionalArg( LinkedList< String > args ) {
			this.args = args;
		}

		public void check() {
			//	All is OK. Does not matter whether or not 1 is taken.
		}

		@Override
		public boolean hasNext() {
			return args != null && !this.args.isEmpty(); 
		}

		@Override
		public String next() {
			String answer = this.args.removeFirst();
			if ( answer.startsWith( "-" ) ) {
				//	Violates limited argument restriction.
				throw new Alert( "Missing argument for command line between two options" ).culprit( "Next option", answer );
			}
			this.args = null;	//	And prevent any more being removed. Maximum of 1.
			return answer;
		}
		
	}
	
	public static class UnlimitedMandatoryArg extends CmdArgs {
		
		private String value;

		public UnlimitedMandatoryArg( String value ) {
			this.value = value;
		}

		@Override
		public void check() {
			if ( this.value != null ) {
				throw new Alert( "Unused argument to command-line option" ).culprit( "Argument", this.value );
			}
		}

		@Override
		public boolean hasNext() {
			return this.value != null;
		}

		@Override
		public String next() {
			if ( this.value == null ) {
				throw new Alert( "Command line option looking for second parameter" );
			}
			String answer = value;
			this.value = null;			//	Prevent repeats.
			return answer;
		}		
		
	}
	
	public static class RestArgs extends CmdArgs {
		
		final private LinkedList< String > args;
		
		public RestArgs( LinkedList< String > args ) {
			this.args = args;
		}
		
		@Override
		public boolean hasNext() {
			return !this.args.isEmpty();
		}

		@Override
		public String next() {
			return this.args.removeFirst();
		}

		@Override
		public void check() {
			if ( !this.args.isEmpty() ) {
				Alert a = new Alert( "Unprocessed arguments" );
				for ( String x : args ) {
					a.culprit( "Unused", x  );
				}
				throw a;
			}
		}
		
	
	}
	
	
}
