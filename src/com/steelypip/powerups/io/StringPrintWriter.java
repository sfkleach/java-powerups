package com.steelypip.powerups.io;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class StringPrintWriter extends PrintWriter {
	
	public StringPrintWriter() {
		super( new CharArrayWriter(), false );
	}

	public void reset() {
		getCharArrayWriter().reset();
	}

	public int size() {
		return getCharArrayWriter().size();
	}

	public char[] toCharArray() {
		return getCharArrayWriter().toCharArray();
	}

	public String toString() {
		return getCharArrayWriter().toString();
	}

	public void writeTo( Writer out ) throws IOException {
		getCharArrayWriter().writeTo( out );
	}

	private CharArrayWriter getCharArrayWriter() {
		return (CharArrayWriter)this.out;
	}
	
}
