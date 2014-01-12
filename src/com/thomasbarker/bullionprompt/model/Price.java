package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;
import lombok.Data;

import java.util.Currency;

@Data
public final class Price {

	private ActionIndicator actionIndicator;
	private long price;
	private long quantity;
	private Security security;
	private Currency considerationCurrency;

}
