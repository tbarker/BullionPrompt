package com.thomasbarker.bullionprompt.xml.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;

import com.thomasbarker.bullionprompt.model.Deal;

@XmlRootElement( name = "envelope" )
public final class Ticker
	extends AbstractMessageDocument< List<Deal> >
{
	public List<Deal> getContent() {
		return this.getMessage().getTicker().getDeals();
	}

	@XmlElement( name = "message" )
	@Getter protected Message message;

	public static final class Message extends AbstractMessage
	{
		@Getter protected String requiredType = "TICKER_A";

		@Getter protected BigDecimal getRequiredVersion = new BigDecimal( "0.2" );

		@XmlElement( name = "ticker" )
		@Getter protected InnerTicker ticker;
	}

	public static final class InnerTicker {

		@XmlElementWrapper( name = "deals" )
		@XmlElement( name = "deal" )
		@Getter protected List<Deal> deals = new ArrayList<Deal>();
	}
}
