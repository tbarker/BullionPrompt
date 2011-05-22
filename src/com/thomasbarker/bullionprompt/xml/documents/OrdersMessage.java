package com.thomasbarker.bullionprompt.xml.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;

import com.thomasbarker.bullionprompt.model.Order;

@XmlRootElement( name = "envelope" )
public final class OrdersMessage
	extends AbstractMessageDocument< List<Order> >
{
	@XmlElement( name = "message" )
	@Getter protected Message message;

	public static final class Message extends AbstractMessage
	{
		@XmlElementWrapper( name = "orders" )
	    @XmlElement( name = "order" )
		@Getter
		private List<Order> orders = new ArrayList<Order>();

		@Getter protected String requiredType = "ORDERS";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.4" );
	}

	public List<Order> getContent() {
		return getMessage().getOrders();
	}

}
