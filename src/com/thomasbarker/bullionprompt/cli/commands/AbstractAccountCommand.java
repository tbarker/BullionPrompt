package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameter;

public abstract class AbstractAccountCommand extends Command {

	@Parameter( names = { "--username", "-u" }, required = true )
	public String username;

	@Parameter( names = { "--password", "-p" }, required = true )
	public String password;

	public void execute() {
		session.login( username, password );
		perform();
	}

	protected abstract void perform();
}
