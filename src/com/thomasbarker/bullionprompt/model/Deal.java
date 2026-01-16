package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.TimestampDateAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.Date;

@XmlRootElement( name = "deal" )
@XmlAccessorType( XmlAccessType.FIELD )

public class Deal {

	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	public Currency considerationCurrency;

	@XmlAttribute( name = "securityId" ) public Security security;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long quantity;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long pricePerUnit;

	@XmlAttribute
	@XmlJavaTypeAdapter( TimestampDateAdaptor.class )
	public Date dealTime;public void setConsiderationCurrency(Currency considerationCurrency) {
		this.considerationCurrency = considerationCurrency;
	}public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}public void setPricePerUnit(Long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

}
