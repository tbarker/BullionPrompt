package com.thomasbarker.bullionprompt.xml.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;
import lombok.Getter;

import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;

@XmlRootElement( name = "envelope" )
public final class PricesMessage
	extends AbstractMessageDocument< List<Price> >
{
	public List<Price> getContent() {
		List<Price> prices = new ArrayList<Price>();

		for( Pitch pitch : getMessage().getMarket().getPitches() ) {
			List<PriceQuote> quotes = new ArrayList<PriceQuote>();
			quotes.addAll( pitch.getBuyPrices() );
			quotes.addAll( pitch.getSellPrices() );

			for( PriceQuote quote : quotes ) {
				Price price = new Price();
				price.setSecurity( pitch.getSecurity() );
				price.setPrice( quote.getLimit() );
				price.setQuantity( quote.getQuantity() );
				price.setConsiderationCurrency( pitch.getConsiderationCurrency() );
				price.setActionIndicator( quote.getActionIndicator() );

				prices.add( price );
			}
		}

		return prices;
	}

	@XmlElement( name = "message" )
	@Getter protected Message message;

	public static final class Message extends AbstractMessage {

		@XmlElement( name = "market" )
		@Getter private Market market;

		@Getter protected String requiredType = "MARKET_DEPTH";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.1" );
	}

	@XmlType
	@XmlAccessorType( XmlAccessType.FIELD )
	@Data
	public static final class Market {
		@XmlElementWrapper( name = "pitches" )
		@XmlElement( name = "pitch" )
		private List<Pitch> pitches = new ArrayList<Pitch>();
	}

	@XmlType
	@XmlAccessorType( XmlAccessType.FIELD )
	@Data
	public static final class Pitch {
		@XmlAttribute( name = "securityId" ) private Security security;
		@XmlAttribute
	    @XmlJavaTypeAdapter( CurrencyAdaptor.class )
		private Currency considerationCurrency;

		@XmlElementWrapper( name = "buyPrices" )
		@XmlElement( name = "price" )
		private List<PriceQuote> buyPrices = new ArrayList<PriceQuote>();

		@XmlElementWrapper( name = "sellPrices" )
		@XmlElement( name = "price" )
		private List<PriceQuote> sellPrices = new ArrayList<PriceQuote>();
	}

	@XmlType
	@XmlAccessorType( XmlAccessType.FIELD )
	@Data
	public static final class PriceQuote {
		@XmlAttribute private ActionIndicator actionIndicator;

		@XmlAttribute
	    @XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	    private Long quantity;

		@XmlAttribute
		@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	    private Long limit;
	}
}
