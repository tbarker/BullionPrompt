package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.*;
import com.thomasbarker.bullionprompt.xml.adaptors.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.Date;

@XmlRootElement( name = "order" )
@XmlAccessorType( XmlAccessType.FIELD )
@Data
public final class Order {

	@XmlAttribute private ActionIndicator actionIndicator;
	@XmlAttribute private String clientTransRef;
	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency considerationCurrency;
	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	private Date goodUntil;
	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	private Date lastModified;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long limit;
	@XmlAttribute( name = "orderId" ) private Long id;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long quantity;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long quantityMatched;
	@XmlAttribute( name = "securityId" ) private Security security;
	@XmlAttribute( name = "statusCode" ) private OrderStatus status;
	@XmlAttribute( name = "typeCode" ) private OrderType type;
	@XmlAttribute( name = "tradeType" ) private TradeType tradeType;
	@XmlAttribute( name = "orderTime" )
	@XmlJavaTypeAdapter( BVDateAdaptor.class )
	private Date time;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long totalCommission;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long totalConsideration;

	// Unfortunately this is not represented as an attribute
	private Boolean cancellable;
}
