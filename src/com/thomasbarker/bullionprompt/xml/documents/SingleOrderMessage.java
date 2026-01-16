package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Order;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement( name = "envelope" )
public final class SingleOrderMessage
	extends AbstractMessageDocument<Order>
{
	public Order getContent() {
		return message.order;
	}

	@XmlElement( name = "message" )
	protected Message message;

	@XmlRootElement( name = "message" )
	public static final class Message extends AbstractMessage {
		@XmlElement( name = "order" )
		public Order order;

		@Override
		protected String getRequiredType() {
			return "SINGLE_ORDER";
		}
		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.1" );
		}
	}

	@XmlElement( name = "cancellable" )
	// This ought to be a Boolean but JAXB would not co-operate
	public String cancellable;

	public Message getMessage() {
		return message;
	}

	@Override
	protected final void fixUp()
	{
		message.order.cancellable = Boolean.valueOf( this.cancellable );
	}

}
