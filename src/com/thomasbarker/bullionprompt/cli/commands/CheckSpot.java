package com.thomasbarker.bullionprompt.cli.commands;

import java.util.Currency;

import lombok.Getter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.cli.converter.CurrencyParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.SecurityParameterConverter;
import com.thomasbarker.bullionprompt.model.enums.Security;

@Parameters( commandDescription = "Check the spot price." )
public class CheckSpot extends Command {

	@Parameter( names = { "--security", "-s" }, required = true, converter = SecurityParameterConverter.class )
	@Getter public Security security;

	@Parameter( names = { "--currency", "-c" }, required = true, converter = CurrencyParameterConverter.class )
	@Getter public Currency considerationCurrency;

	@Override
	public void execute() {
		System.out.println ( session.wholesalePrice( security, considerationCurrency ).getPrice() );
	}

}
