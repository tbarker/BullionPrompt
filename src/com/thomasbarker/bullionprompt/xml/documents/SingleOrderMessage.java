package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Order;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement( name = "envelope" )
public final class SingleOrderMessage
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

		@Getter protected String requiredType = "SINGLE_ORDER";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.1" );
	}

	@XmlElement( name = "cancellable" )
	// This ought to be a Boolean but JAXB would not co-operate
	@Getter private String cancellable;

	@Override
	protected final void fixUp()
	{
		getMessage().getOrder().setCancellable( Boolean.valueOf( this.cancellable ) );
	}
}
