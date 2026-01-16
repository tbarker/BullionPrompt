package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.UtcDateAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.Date;

@XmlAccessorType( XmlAccessType.FIELD )

public final class PendingTransfer {

	@XmlAttribute
	public String type;

	@XmlAttribute
	public String lowestLedger;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long balance;

	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	public Date dueDate;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long valuation;

	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	public Currency valuationCurrency;public void setType(String type) {
		this.type = type;
	}public void setLowestLedger(String lowestLedger) {
		this.lowestLedger = lowestLedger;
	}public void setBalance(Long balance) {
		this.balance = balance;
	}public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}public void setValuation(Long valuation) {
		this.valuation = valuation;
	}public void setValuationCurrency(Currency valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}

}
