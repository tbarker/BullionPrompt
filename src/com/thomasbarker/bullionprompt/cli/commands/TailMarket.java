package com.thomasbarker.bullionprompt.cli.commands;

import java.util.HashSet;
import java.util.Set;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Price;

@Parameters( commandDescription = "Show changes to the markets." )
public final class TailMarket extends AbstractMarketCommand {

	public void execute() {
        Set<Price> seenPrices = new HashSet<Price>();
        while ( true ) {
            for( Price price : session.markets( considerationCurrency, security, quantity, marketWidth ) ) {
                if( seenPrices.add( price ) ) {
                    PrettyPrint.price( price );
                }
            }

            try {
                Thread.sleep( 2000 );
            } catch ( InterruptedException e ) {
                break;
            }
        }
    }

}
