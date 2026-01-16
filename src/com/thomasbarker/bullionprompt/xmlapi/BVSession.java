package com.thomasbarker.bullionprompt.xmlapi;

import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.LoginException;
import com.thomasbarker.bullionprompt.model.*;
import com.thomasbarker.bullionprompt.model.enums.*;
import com.thomasbarker.bullionprompt.network.DoMethodGetObject;
import com.thomasbarker.bullionprompt.xml.documents.*;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.CookieManager;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Wraps http://www.bullionvault.com/help/?xml_api.html
 *
 */
public final class BVSession {

	private static final String LIVE_BV_DOMAIN = "www.bullionvault.com";
	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
		.cookieHandler(new CookieManager())
		.build();

	private final HttpClient client;
	private final String domain;
	private boolean isLoggedIn = false;

	public BVSession() {
		this( LIVE_BV_DOMAIN );
	}

	public BVSession( String domain ) {
		this( HTTP_CLIENT, domain );
	}

	public BVSession( HttpClient client, String domain ) {
		this.client = client;
		this.domain = domain;
	}

	public void login( String username, String password ) {
		try {
			Map<String, String> loginParams = new HashMap<>();
			loginParams.put("j_username", username);
			loginParams.put("j_password", password);

			String query = encodeFormData(loginParams);
			URI uri = URI.create("https://" + domain + "/secure/j_security_check?" + query);

			HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();

			HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
			int statusCode = response.statusCode();

			if ( 200 != statusCode ) {
				throw new LoginException();
			}

			isLoggedIn = true;

		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	public List<Position> balance() {
		try {
			HttpRequest request = makeBVApiHttpRequest( "view_balance_xml.do?simple=true" );
			return new DoMethodGetObject< List<Position> >( PositionsMessage.class ).fetch( client, request );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public List<PendingSettlement> pendingSettlements() {
		try {
			HttpRequest request = makeBVApiHttpRequest( "view_balance_xml.do?simple=false" );
			return new DoMethodGetObject< List<PendingSettlement> >( PendingSettlementMessage.class ).fetch( client, request );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public PositionsAndPendingSettlements positionsAndPendingSettlements() {
		try {
			HttpRequest request = makeBVApiHttpRequest( "view_balance_xml.do?simple=false" );
			Document document = DoMethodGetObject.fetchXML( client, request );

			List<Position> positions = new DoMethodGetObject< List<Position> >( PositionsMessage.class ).fetch( document );
			List<PendingSettlement> pendingSettlements = new DoMethodGetObject< List<PendingSettlement> >( PendingSettlementMessage.class ).fetch( document );

			return new PositionsAndPendingSettlements( positions, pendingSettlements );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public List<Order> orders() {
		return orders( null, null, null );
	}

	public List<Order> orders(
			Security security,
			Currency considerationCurrency,
			OrderStatus status
	) {
		try {
			HttpRequest request = makeBVApiHttpRequest( "view_orders_xml.do" );
			return new DoMethodGetObject< List<Order> >( OrdersMessage.class ).fetch( client, request );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public List<Price> markets() {
		return markets( null, null, null, null );
	}

	public List<Price> markets(
			Currency considerationCurrency,
			Security security,
			Long quantity,
			Integer marketWidth
	) {
		try {
			Map<String, String> params = new HashMap<>();
			if ( null != considerationCurrency ) {
				params.put( "considerationCurrency", considerationCurrency.getCurrencyCode() );
			}
			if ( null != security ) {
				params.put( "securityId", security.getCode() );
			}
			if ( null != quantity ) {
				params.put( "quantity", new BigDecimal( quantity ).movePointLeft( 3 ).toPlainString() );
			}
			if ( null != marketWidth ) {
				params.put( "marketWidth", marketWidth.toString() );
			}

			return new DoMethodGetObject< List<Price> >( PricesMessage.class ).fetch( client, "https", domain, "/view_market_xml.do", params );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public Order placeOrder( PlaceOrder order ) {
		if ( null != order.getGoodUntil() && OrderType.TIL_TIME != order.getType() ) {
			throw new IllegalArgumentException( "Good until time is for good until orders only." );
		}

		try {
			Map<String, String> params = new HashMap<>();
			params.put( "actionIndicator", order.getActionIndicator().getCode() );
			params.put( "considerationCurrency", order.getConsiderationCurrency().getCurrencyCode() );
			params.put( "securityId", order.getSecurity().getCode() );
			params.put( "quantity", new BigDecimal( order.getQuantity() ).movePointLeft( 3 ).toPlainString() );
			params.put( "limit", Long.toString( order.getLimit() ) );
			params.put( "typeCode", order.getType().getCode() );
			params.put( "clientTransRef", order.getClientTransRef() );
			if ( null != order.getGoodUntil() ) {
				params.put( "goodUntil", new SimpleDateFormat( "yyyy-MM-dd HH:mmZ" ).format( order.getGoodUntil() ) );
			}
			params.put( "confirmed", "true" );

			URI uri = URI.create( String.format( "https://%s/secure/api/v2/place_order_xml.do", domain ) );
			return new DoMethodGetObject<Order>( PlaceOrderMessage.class ).fetch( client, uri, params );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public void cancelOrder( Order order ) {
		cancelOrder( order.id );
	}

	public Order cancelOrder( Long orderId ) {
		try {
			Map<String, String> params = new HashMap<>();
			params.put( "orderId", orderId.toString() );

			URI uri = URI.create( "https://live.bullionvault.com/secure/cancel_order_xml.do" );
			return new DoMethodGetObject<Order>( SingleOrderMessage.class ).fetch( client, uri, params );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public Order order( Long orderId ) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( "https://live.bullionvault.com/secure/view_single_order_xml.do?orderId=" + orderId ) )
				.GET()
				.build();
			return new DoMethodGetObject<Order>( SingleOrderMessage.class ).fetch( client, request );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public List<Deal> tickerDeals() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( "https://live.bullionvault.com/view_ticker_xml.do" ) )
				.GET()
				.build();
			return new DoMethodGetObject< List<Deal> >( Ticker.class ).fetch( client, request );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public List<Price> marketDepth( Security security, Currency considerationCurrency ) {
		try {
			// Construct GET request
			Map<String, String> params = new HashMap<>();
			params.put( "considerationCurrency", considerationCurrency.getCurrencyCode() );
			params.put( "securityId", security.getCode() );
			params.put( "marketWidth", "26" );
			params.put( "priceInterval", "0" );

			String query = encodeFormData(params);
			URI uri = URI.create( "http://" + domain + "/view_market_depth.do?" + query );

			// Make HTTP request
			HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();

			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

			String document;
			try (InputStream stream = response.body()) {
				document = new java.util.Scanner( stream ).useDelimiter("\\A").next();
			}

			return MarketDepth.parse( document );
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
	}

	public List<Price> wholesalePrice() {
		List<Price> prices = new ArrayList<Price>();

		for ( Security security : Security.bullionValues() ) {
			for ( Currency considerationCurrency : TradingCurrencies.values() ) {

				try {
					Thread.sleep( 600 ); // Do not hammer chart
				} catch ( InterruptedException e ) {
					; // Actually we don't usually mind waking up early
				}
				Price price = this.wholesalePrice( security, considerationCurrency );

				if ( null != price ) {
					prices.add( price );
				}
			}
		}

		return prices;
	}

	public Price wholesalePrice( Security security, Currency considerationCurrency ) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( "http://www.galmarley.com/prices/CHART_LINE/" + security.getSecurityClass().getCode() + "/" + considerationCurrency + "/5/Full" ) )
				.GET()
				.build();
			List<Price> prices = new DoMethodGetObject<List<Price>>( SpotPriceMessage.class ).fetch( client, request );

			Price price = prices.isEmpty() ? null : prices.get( prices.size() - 1 );
			if ( null != price ) {
				price.considerationCurrency =  considerationCurrency ;
				price.security =  security ;
			}
			return price;
		} catch ( IOException | InterruptedException e ) {
			throw new BVWireError( e );
		}
    }

	private HttpRequest makeBVApiHttpRequest( String url ) {
		URI uri = URI.create( String.format( "https://%s/secure/api/v2/%s", domain, url ) );
		return HttpRequest.newBuilder()
			.uri(uri)
			.GET()
			.build();
	}

	private static String encodeFormData(Map<String, String> data) {
		if (data == null || data.isEmpty()) {
			return "";
		}

		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (Map.Entry<String, String> entry : data.entrySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
		}

		return result.toString();
	}
}
