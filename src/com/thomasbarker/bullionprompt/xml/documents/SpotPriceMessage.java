package com.thomasbarker.bullionprompt.xml.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.documents.AbstractMessageDocument.AbstractMessage;
import com.thomasbarker.bullionprompt.xml.documents.PricesMessage.Market;
import com.thomasbarker.bullionprompt.xml.documents.PricesMessage.Message;
import com.thomasbarker.bullionprompt.xml.documents.PricesMessage.PriceQuote;

@XmlRootElement( name = "envelope" )
public final class SpotPriceMessage
	extends AbstractMessageDocument< List<Price> >
{

	@XmlElement( name = "message" )
	protected Message message;

	@XmlType
	public static final class Message extends AbstractMessage {

		@XmlElement( name = "point" )
		@XmlElementWrapper( name = "points" )
		public List<Point> points;

		@Override
		protected String getRequiredType() {
			return "CHART_LINE_A";
		}
		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.4" );
		}

		@XmlAttribute( name = "securityId" )
		public Security security;

		@XmlAttribute( name = "currency" )
	    @XmlJavaTypeAdapter( CurrencyAdaptor.class )
		public Currency considerationCurrency;
	}

	@XmlType
	public static class Point {
		@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
		public Long price;
	}

	public Message getMessage() {
		return message;
	}

	public List<Price> getContent() {
		List<Price> prices = new ArrayList<Price>();

		for( Point point : message.points ) {
			if ( null == point.price ) { continue; }

			Price price = new Price();
			price.security = message.security;
			price.price = point.price;
			price.considerationCurrency = message.considerationCurrency;

			prices.add( price );
		}

		return prices;
	}

}
