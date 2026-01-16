package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Order;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class OrdersMessage
	extends AbstractMessageDocument< List<Order> >
{
	@XmlElement( name = "message" )
	protected Message message;

	public static final class Message extends AbstractMessage
	{
		@XmlElementWrapper( name = "orders" )
	    @XmlElement( name = "order" )
		public List<Order> orders = new ArrayList<Order>();

		@Override
		protected String getRequiredType() {
			return "ORDERS";
		}
		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.4" );
		}
	}

	public Message getMessage() {
		return message;
	}

	public List<Order> getContent() {
		return message.orders;
	}

}
