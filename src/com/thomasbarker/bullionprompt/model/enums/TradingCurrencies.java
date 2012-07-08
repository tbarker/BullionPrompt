package com.thomasbarker.bullionprompt.model.enums;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public final class TradingCurrencies {

	public static Set<Currency> values() {
		Set<Currency> tradingCurrencies = new HashSet<Currency>();

		tradingCurrencies.add( Currency.getInstance( "USD" ) );
		tradingCurrencies.add( Currency.getInstance( "GBP" ) );
		tradingCurrencies.add( Currency.getInstance( "EUR" ) );

		return tradingCurrencies;
	}
}
