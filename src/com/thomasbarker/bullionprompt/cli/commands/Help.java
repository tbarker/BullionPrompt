package com.thomasbarker.bullionprompt.cli.commands;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

@RequiredArgsConstructor
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
