package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Order;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
