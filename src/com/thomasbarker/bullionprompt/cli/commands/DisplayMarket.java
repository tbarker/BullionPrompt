package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Price;

@Parameters( commandDescription = "Show the markets prices." )
public final class DisplayMarket extends AbstractMarketCommand {

	public void execute() {
        for( Price price : session.markets( considerationCurrency, security, quantity, marketWidth ) ) {
        	PrettyPrint.price( price );
        }
	}

}
