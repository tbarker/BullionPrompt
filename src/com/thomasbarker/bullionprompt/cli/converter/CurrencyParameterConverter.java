package com.thomasbarker.bullionprompt.cli.converter;

import java.util.Currency;

import com.beust.jcommander.IStringConverter;

public final class CurrencyParameterConverter implements IStringConverter<Currency> {

	public Currency convert( String currencyCode ) {
		return Currency.getInstance( currencyCode );
	}

}
