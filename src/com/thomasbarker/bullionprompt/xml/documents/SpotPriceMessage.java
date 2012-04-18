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
import lombok.Setter;

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
	@Getter protected Message message;

	@XmlType
	public static final class Message extends AbstractMessage {

		@XmlElement( name = "point" )
		@XmlElementWrapper( name = "points" )
		@Getter private List<Point> points;

		@Getter protected String requiredType = "CHART_LINE_A";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.4" );

		@XmlAttribute( name = "securityId" )
		@Getter
		private Security security;

		@XmlAttribute( name = "currency" )
	    @XmlJavaTypeAdapter( CurrencyAdaptor.class )
		@Getter
		private Currency considerationCurrency;
	}

	public List<Price> getContent() {
		List<Price> prices = new ArrayList<Price>();

		for( Point point : getMessage().getPoints() ) {

			if ( null == point.getPrice() ) { continue; }

			Price price = new Price();
			price.setSecurity( getMessage().getSecurity() );
			price.setPrice( point.getPrice() );
			price.setConsiderationCurrency( getMessage().getConsiderationCurrency() );

			prices.add( price );
		}

		return prices;
	}


	@XmlType
	private static class Point {

		@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
		@Getter private Long price;
	}
}
