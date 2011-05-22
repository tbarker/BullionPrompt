package com.thomasbarker.bullionprompt.model;

import java.util.Currency;
import java.util.Date;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;

public interface PlaceOrder {

	public ActionIndicator getActionIndicator();
	public Currency getConsiderationCurrency();
	public Security getSecurity();
	public Long getLimit();
	public Date getGoodUntil();
	public Long getQuantity();
	public OrderType getType();
	public String getClientTransRef();

}
