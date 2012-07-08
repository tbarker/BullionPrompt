package com.thomasbarker.bullionprompt.xml.documents;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;

public final class MarketDepth {

	public static List<Price> parse( String document ) {
		List<Price> prices = new ArrayList<Price>();

		Matcher matchTape = Pattern.compile(
			"actionIndicator=([A-Z])&securityId=([A-Z]+)&considerationCurrency=([A-Z]+)&quantity=([0-9]+\\.[0-9]+)&limit=([0-9]+)"
		).matcher( document );
		while ( matchTape.find() ) {

			Price price = new Price();
			if ( matchTape.group( 1 ).equals( "S" ) ) {
				price.setActionIndicator( ActionIndicator.SELL );
			} else if ( matchTape.group( 1 ).equals( "B" ) ) {
				price.setActionIndicator( ActionIndicator.BUY );
			}
			price.setSecurity( Security.valueOf( matchTape.group( 2 ) ) );
			price.setConsiderationCurrency( Currency.getInstance( matchTape.group( 3 ) ) );
			price.setQuantity( (long) (1000 * Float.valueOf( matchTape.group( 4 ) )) ); 
			price.setPrice( Long.valueOf( matchTape.group( 5 ) ) );

			prices.add( price );
		}
		return prices;
	}
}
