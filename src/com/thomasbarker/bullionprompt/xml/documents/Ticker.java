package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Deal;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class Ticker
	extends AbstractMessageDocument< List<Deal> >
{
	@XmlElement( name = "message" )
	protected Message message;

	public static final class Message extends AbstractMessage
	{
		@XmlElement( name = "ticker" )
		public InnerTicker ticker;

		@Override
		protected String getRequiredType() {
			return "TICKER_A";
		}

		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.2" );
		}
	}

	public static final class InnerTicker {
		@XmlElementWrapper( name = "deals" )
		@XmlElement( name = "deal" )
		public List<Deal> deals = new ArrayList<Deal>();
	}

	public Message getMessage() {
		return message;
	}

	public List<Deal> getContent() {
		return message.ticker.deals;
	}

}
