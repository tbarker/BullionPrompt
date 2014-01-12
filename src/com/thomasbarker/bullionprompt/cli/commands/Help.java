package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Parameters( commandDescription = "Display this message" )
public final class Help {

	@Getter final JCommander commander;

	@Parameter( required = false )
	List<String> commandName;

	public void usage() {
		if ( null != commandName ) {
			commander.usage( commandName.get( 0 ) );
		} else {
			commander.usage();
		}
	}

}
