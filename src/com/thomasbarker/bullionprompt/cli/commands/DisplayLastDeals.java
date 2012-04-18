package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Deal;

@Parameters( commandDescription = "Display the last deals on the market." )
public class DisplayLastDeals extends Command {

	@Override
	public void execute() {
		for( Deal deal : session.tickerDeals() ) {
			printDealLn( deal );
		}
	}

	protected static void printDealLn( Deal deal ) {
		System.out.print( deal.getSecurity() + "\t" );
		System.out.print( deal.getConsiderationCurrency() + "\t" );
		System.out.print( deal.getDealTime() + "\t" );
		System.out.print( deal.getPricePerUnit() + "\t" );
		System.out.print( deal.getQuantity() + "\n" );
	}
}
