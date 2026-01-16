package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class PricesMessage
	extends AbstractMessageDocument< List<Price> >
{
	@XmlElement( name = "message" )
	protected Message message;

	public static final class Message extends AbstractMessage {
		@XmlElement( name = "market" )
		public Market market;

		@Override
		protected String getRequiredType() {
			return "MARKET_DEPTH";
		}
		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.1" );
		}
	}

	@XmlType
	@XmlAccessorType( XmlAccessType.FIELD )
	public static final class Market {
		@XmlElementWrapper( name = "pitches" )
		@XmlElement( name = "pitch" )
		public List<Pitch> pitches = new ArrayList<Pitch>();
	}

	@XmlType
	@XmlAccessorType( XmlAccessType.FIELD )
	public static final class Pitch {
		@XmlAttribute( name = "securityId" ) public Security security;
		@XmlAttribute
	    @XmlJavaTypeAdapter( CurrencyAdaptor.class )
		public Currency considerationCurrency;

		@XmlElementWrapper( name = "buyPrices" )
		@XmlElement( name = "price" )
		public List<PriceQuote> buyPrices = new ArrayList<PriceQuote>();

		@XmlElementWrapper( name = "sellPrices" )
		@XmlElement( name = "price" )
		public List<PriceQuote> sellPrices = new ArrayList<PriceQuote>();
	}

	@XmlType
	@XmlAccessorType( XmlAccessType.FIELD )
	public static final class PriceQuote {
		@XmlAttribute public ActionIndicator actionIndicator;

		@XmlAttribute
	    @XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	    public Long quantity;

		@XmlAttribute
		@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	    public Long limit;
	}

	public Message getMessage() {
		return message;
	}

	public List<Price> getContent() {
		List<Price> prices = new ArrayList<Price>();

		for( Pitch pitch : message.market.pitches ) {
			List<PriceQuote> quotes = new ArrayList<PriceQuote>();
			quotes.addAll( pitch.buyPrices );
			quotes.addAll( pitch.sellPrices );

			for( PriceQuote quote : quotes ) {
				Price price = new Price();
				price.security = pitch.security;
				price.price = quote.limit;
				price.quantity = quote.quantity;
				price.considerationCurrency = pitch.considerationCurrency;
				price.actionIndicator = quote.actionIndicator;

				prices.add( price );
			}
		}

		return prices;
	}

}
