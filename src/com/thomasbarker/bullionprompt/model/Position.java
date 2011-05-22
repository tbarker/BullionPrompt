package com.thomasbarker.bullionprompt.model;

import java.util.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.Position;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;

@XmlRootElement( name = "clientPosition" )
@XmlAccessorType( XmlAccessType.FIELD )
@Data
public final class Position {

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long available;
	@XmlAttribute( name = "classNarrative" ) public String narrative;
	@XmlAttribute( name = "securityId" ) private Security security;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long total;
	@XmlAttribute( name = "totalValuation" )
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long valuation;
	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency valuationCurrency;

	public Long getValuation() {
		return this.valuation * ( "CURRENCY".equals( narrative ) ? 10 : 1 );
	}

}
