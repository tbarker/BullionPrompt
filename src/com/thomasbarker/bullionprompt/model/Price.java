package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;

import java.util.Currency;


public final class Price {
	public ActionIndicator actionIndicator;
	public long price;
	public long quantity;
	public Security security;
	public Currency considerationCurrency;
}
