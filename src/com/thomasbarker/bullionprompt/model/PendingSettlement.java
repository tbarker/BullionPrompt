package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD )

public final class PendingSettlement {

	@XmlAttribute( name = "securityId" )
	public Security security;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long total;

	@XmlAttribute( name = "classNarrative" )
	public String narrative;

	@XmlAttribute( name = "totalValuation" )
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long valuation;

	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	public Currency valuationCurrency;

	@XmlElementWrapper( name = "pendingTransfers" )
	@XmlElement( name = "pendingTransfer" )
	public List<PendingTransfer> transfers;public void setSecurity(Security security) {
		this.security = security;
	}public void setTotal(Long total) {
		this.total = total;
	}public void setNarrative(String narrative) {
		this.narrative = narrative;
	}public void setValuation(Long valuation) {
		this.valuation = valuation;
	}public void setValuationCurrency(Currency valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}public void setTransfers(List<PendingTransfer> transfers) {
		this.transfers = transfers;
	}

}
