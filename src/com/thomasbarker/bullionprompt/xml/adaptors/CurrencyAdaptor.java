package com.thomasbarker.bullionprompt.xml.adaptors;

import java.util.Currency;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class CurrencyAdaptor extends XmlAdapter<String, Currency> {

	@Override
	public String marshal( Currency currency ) throws Exception {
		return currency.getCurrencyCode();
	}

	@Override
	public Currency unmarshal( String currencyString ) throws Exception {
		return Currency.getInstance( currencyString );
	}

}
