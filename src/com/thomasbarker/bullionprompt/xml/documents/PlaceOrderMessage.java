package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Order;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

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
