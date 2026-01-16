package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.List;

@Parameters( commandDescription = "Display this message" )
public final class Help {

	private final JCommander commander;

	@Parameter( required = false )
	List<String> commandName;

	public Help(JCommander commander) {
		this.commander = commander;
	}

	public JCommander getCommander() {
		return commander;
	}

	public void usage() {
		if ( null != commandName && !commandName.isEmpty() ) {
			StringBuilder out = new StringBuilder();
			commander.getUsageFormatter().usage( commandName.get( 0 ), out );
			System.out.println( out.toString() );
		} else {
			commander.usage();
		}
	}

}
