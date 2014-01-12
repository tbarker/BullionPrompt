package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameter;
import com.thomasbarker.bullionprompt.xmlapi.BVSession;
import lombok.Getter;

import java.text.SimpleDateFormat;

public abstract class Command {

	protected BVSession session;

	final SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm" );

	@Parameter( names = "--wire", required = false, hidden = true )
	@Getter private boolean wire = false;
	
	public void start() {

		// Enable debug wire logging if requested
		// These system parameters must be set before initialising the session
		if ( isWire() ) {
			System.setProperty( "org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog" );
			System.setProperty( "org.apache.commons.logging.simplelog.showdatetime", "true" );
			System.setProperty( "org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG" );
		}

		session = new BVSession();
		execute();
	}

	public abstract void execute();
}
