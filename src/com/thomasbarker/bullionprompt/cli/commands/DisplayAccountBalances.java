package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Position;

@Parameters( commandDescription = "Display account property balances and valuations." )
public final class DisplayAccountBalances extends AbstractAccountCommand {

	@Override
	protected void perform() {
		for( Position position : session.balance() ) {
			System.out.print( position.getSecurity() + "\t" );
			System.out.print( position.getTotal() + "\t" );
			System.out.print( position.getAvailable() + "\t" );
			System.out.print( position.getValuationCurrency() + "\t" );
			System.out.print( position.getValuation() + "\t" );
			System.out.print( position.getNarrative() + "\n" );
		}
	}

}
