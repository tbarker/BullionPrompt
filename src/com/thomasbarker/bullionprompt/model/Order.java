package com.thomasbarker.bullionprompt.model;

import java.util.Currency;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.OrderStatus;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.BVDateAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.UtcDateAdaptor;

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
	@XmlAttribute( name = "orderTime" )
	@XmlJavaTypeAdapter( BVDateAdaptor.class )
	private Date time;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long totalCommission;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long totalConsideration;

}
