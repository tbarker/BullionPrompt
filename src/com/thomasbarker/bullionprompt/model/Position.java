package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;

@XmlRootElement( name = "clientPosition" )
@XmlAccessorType( XmlAccessType.FIELD )

public final class Position {

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long available;
	@XmlAttribute( name = "classNarrative" ) public String narrative;
	@XmlAttribute( name = "securityId" ) public Security security;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long total;
	@XmlAttribute( name = "totalValuation" )
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long valuation;
	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	public Currency valuationCurrency;}
