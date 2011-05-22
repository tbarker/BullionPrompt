package com.thomasbarker.bullionprompt.cli.commands;

import java.util.Currency;

import lombok.Getter;

import com.beust.jcommander.Parameter;
import com.thomasbarker.bullionprompt.cli.converter.CurrencyParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.SecurityParameterConverter;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.Security;

public abstract class AbstractMarketCommand extends Command {

	@Parameter( names = { "--currency", "-c" }, required = false, converter = CurrencyParameterConverter.class )
	@Getter Currency considerationCurrency;

	@Parameter( names = { "--security", "-s" }, required = false, converter = SecurityParameterConverter.class )
	@Getter Security security;

	@Parameter( names = { "--quantity", "-q" }, required = false, description = "In grams" )
	@Getter Long quantity;

	@Parameter( names = { "--width", "-w" }, description = "Maximum number of bids and offers returned for each market.", required = false )
	@Getter Integer marketWidth;

	protected static void printPriceLn( Price price ) {
		System.out.print( price.getSecurity() + "\t" );
		System.out.print( price.getConsiderationCurrency() + "\t" );
		System.out.print( price.getActionIndicator() + "\t" );
		System.out.print( price.getPrice() + "\t" );
		System.out.print( price.getQuantity() + "\n" );
	}

}
