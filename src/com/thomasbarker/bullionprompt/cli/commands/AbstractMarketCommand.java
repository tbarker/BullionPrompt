package com.thomasbarker.bullionprompt.cli.commands;

import java.util.Currency;


import com.beust.jcommander.Parameter;
import com.thomasbarker.bullionprompt.cli.converter.CurrencyParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.SecurityParameterConverter;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.Security;

public abstract class AbstractMarketCommand extends Command {

	@Parameter( names = { "--currency", "-c" }, required = false, converter = CurrencyParameterConverter.class )
	Currency considerationCurrency;

	@Parameter( names = { "--security", "-s" }, required = false, converter = SecurityParameterConverter.class )
	Security security;

	@Parameter( names = { "--quantity", "-q" }, required = false, description = "In grams" )
	Long quantity;

	@Parameter( names = { "--width", "-w" }, description = "Maximum number of bids and offers returned for each market.", required = false )
	Integer marketWidth;

}
