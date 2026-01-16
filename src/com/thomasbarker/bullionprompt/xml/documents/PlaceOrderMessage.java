package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Order;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement( name = "envelope" )
public final class PlaceOrderMessage
	extends AbstractMessageDocument<Order>
{
	@XmlElement( name = "message" )
	protected Message message;

	@XmlRootElement( name = "message" )
	public static final class Message extends AbstractMessage {
		@XmlElement( name = "order" )
		public Order order;

		@Override
		protected String getRequiredType() {
			return "PLACE_ORDER";
		}

		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.1" );
		}
	}

	public Message getMessage() {
		return message;
	}

	public Order getContent() {
		return message.order;
	}

}
