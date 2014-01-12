package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Deal;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.2" );

		@XmlElement( name = "ticker" )
		@Getter protected InnerTicker ticker;
	}

	public static final class InnerTicker {

		@XmlElementWrapper( name = "deals" )
		@XmlElement( name = "deal" )
		@Getter protected List<Deal> deals = new ArrayList<Deal>();
	}

}
