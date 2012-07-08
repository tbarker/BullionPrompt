package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Position;

@Parameters( commandDescription = "Display account property balances and valuations." )
public final class DisplayAccountBalances extends AbstractAccountCommand {

	@Override
	protected void perform() {
		for( Position position : session.balance() ) {
			PrettyPrint.position( position );
		}
	}

}
