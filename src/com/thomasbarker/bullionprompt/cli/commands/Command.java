package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameter;
import com.thomasbarker.bullionprompt.xmlapi.BVSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public abstract class Command {

	private static final Logger logger = LoggerFactory.getLogger(Command.class);

	protected BVSession session;

	final SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm" );

	@Parameter( names = "--wire", required = false, hidden = true )
	private boolean wire = false;

	public void start() {

		// Enable debug wire logging if requested
		// Set SLF4J logging level for Apache HTTP client
		if ( isWire() ) {
			System.setProperty( "org.slf4j.simpleLogger.log.org.apache.http", "debug" );
			logger.debug("Wire logging enabled for HTTP client");
		}

		session = new BVSession();
		execute();
	}

	public abstract void execute();

	public boolean isWire() {
		return wire;
	}
}
