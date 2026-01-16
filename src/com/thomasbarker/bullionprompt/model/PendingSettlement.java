package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD )
@Data
public final class PendingSettlement {

	@XmlAttribute( name = "securityId" )
	private Security security;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long total;

	@XmlAttribute( name = "classNarrative" )
	private String narrative;

	@XmlAttribute( name = "totalValuation" )
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long valuation;

	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency valuationCurrency;

	@XmlElementWrapper( name = "pendingTransfers" )
	@XmlElement( name = "pendingTransfer" )
	private List<PendingTransfer> transfers;
}
