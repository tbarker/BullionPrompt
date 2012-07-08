package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Deal;

@Parameters( commandDescription = "Display the last deals on the market." )
public class DisplayLastDeals extends Command {

	@Override
	public void execute() {
		for( Deal deal : session.tickerDeals() ) {
			PrettyPrint.deal( deal );
		}
	}

}
