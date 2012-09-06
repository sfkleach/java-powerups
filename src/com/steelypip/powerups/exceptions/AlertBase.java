package com.steelypip.powerups.exceptions;

import java.util.Iterator;
import java.util.LinkedList;

class AlertBase extends RuntimeException {

	private static final long serialVersionUID = -6711653003213607989L;
	
	public AlertBase() {
		super();
	}

	public AlertBase( final String message, final Throwable cause ) {
		super( message, cause );
	}

	public AlertBase( final String message ) {
		super( message );
	}

	public AlertBase( final Throwable cause ) {
		super( cause );
	}

	private LinkedList< Culprit > culprit_list = new LinkedList< Culprit >();

	protected void add( final Culprit culprit ) {
		this.culprit_list.add( culprit );
	}

	protected Iterator< Culprit > culpritIterator() {
		return this.culprit_list.iterator();
	}
	
	protected Iterable< Culprit > culprits() {
		return this.culprit_list;
	}

}