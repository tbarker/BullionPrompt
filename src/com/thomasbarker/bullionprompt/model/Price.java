package com.thomasbarker.bullionprompt.model;

import java.util.Currency;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;

@Data
public final class Price {

	private ActionIndicator actionIndicator;
	private long price;
	private long quantity;
	private Security security;
	private Currency considerationCurrency;

}
