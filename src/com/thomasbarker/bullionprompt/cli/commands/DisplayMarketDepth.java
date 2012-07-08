package com.thomasbarker.bullionprompt.cli.commands;

import java.util.Currency;

import lombok.Getter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.cli.converter.CurrencyParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.SecurityParameterConverter;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.Security;

@Parameters( commandDescription = "Show the market pricing depth." )
public final class DisplayMarketDepth extends Command {

	@Parameter( names = { "--currency", "-c" }, required = false, converter = CurrencyParameterConverter.class )
	@Getter Currency considerationCurrency;

	@Parameter( names = { "--security", "-s" }, required = false, converter = SecurityParameterConverter.class )
	@Getter Security security;

	@Override
	public void execute() {
        for( Price price : session.marketDepth( security, considerationCurrency ) ) {
        	PrettyPrint.price( price );
        }
	}

}
