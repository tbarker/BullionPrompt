package com.thomasbarker.bullionprompt.xml.documents;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;

import com.thomasbarker.bullionprompt.model.Order;

@XmlRootElement( name = "envelope" )
public final class PlaceOrderMessage
	extends AbstractMessageDocument<Order>
{
	public Order getContent() {
		return getMessage().getOrder();
	}

	@XmlElement( name = "message" )
	@Getter private Message message;

	@XmlRootElement( name = "message" )
	public static final class Message extends AbstractMessage {

		@XmlElement( name = "order" )
		@Getter private Order order;

		@Getter protected String requiredType = "PLACE_ORDER";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.1" );
	}

}
