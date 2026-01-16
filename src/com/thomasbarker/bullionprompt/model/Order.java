package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.*;
import com.thomasbarker.bullionprompt.xml.adaptors.*;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.Date;

@XmlRootElement( name = "order" )
@XmlAccessorType( XmlAccessType.FIELD )

public final class Order {

	@XmlAttribute public ActionIndicator actionIndicator;
	@XmlAttribute public String clientTransRef;
	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	public Currency considerationCurrency;
	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	public Date goodUntil;
	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	public Date lastModified;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long limit;
	@XmlAttribute( name = "orderId" ) public Long id;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long quantity;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	public Long quantityMatched;
	@XmlAttribute( name = "securityId" ) public Security security;
	@XmlAttribute( name = "statusCode" ) public OrderStatus status;
	@XmlAttribute( name = "typeCode" ) public OrderType type;
	@XmlAttribute( name = "tradeType" ) public TradeType tradeType;
	@XmlAttribute( name = "orderTime" )
	@XmlJavaTypeAdapter( BVDateAdaptor.class )
	public Date time;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long totalCommission;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	public Long totalConsideration;

	// Unfortunately this is not represented as an attribute
	public Boolean cancellable;

}
