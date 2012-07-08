package com.thomasbarker.bullionprompt.xmlapi;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import lombok.Cleanup;
import lombok.SneakyThrows;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.LoginException;
import com.thomasbarker.bullionprompt.model.Deal;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.PlaceOrder;
import com.thomasbarker.bullionprompt.model.Position;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.OrderStatus;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.network.DoMethodGetObject;
import com.thomasbarker.bullionprompt.network.HttpClients;
import com.thomasbarker.bullionprompt.xml.documents.OrdersMessage;
import com.thomasbarker.bullionprompt.xml.documents.PlaceOrderMessage;
import com.thomasbarker.bullionprompt.xml.documents.PositionsMessage;
import com.thomasbarker.bullionprompt.xml.documents.PricesMessage;
import com.thomasbarker.bullionprompt.xml.documents.SingleOrderMessage;
import com.thomasbarker.bullionprompt.xml.documents.SpotPriceMessage;
import com.thomasbarker.bullionprompt.xml.documents.Ticker;

/**
 * Wraps http://www.bullionvault.com/help/?xml_api.html
 * 
 */
public final class BVSession {

	private HttpClient client;
	private boolean isLoggedIn = false;

	public BVSession() {
		client = HttpClients.getThreadSafeClient();
	}

	public BVSession( HttpClient client ) {
		this.client = client;
	}

	public void login( String username, String password ) {

		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add( new BasicNameValuePair( "j_username", username ) );
		loginParams.add( new BasicNameValuePair( "j_password", password ) );

		HttpResponse response;
		int statusCode = 0;
		try {
			URI uri = URIUtils.createURI(
					"https", "live.bullionvault.com", -1, "/secure/j_security_check", 
					URLEncodedUtils.format( loginParams, "UTF-8"), null
				);

			HttpGet method = new HttpGet( uri );
			response = client.execute( method );

			statusCode = response.getStatusLine().getStatusCode();

			response.getEntity().getContent().close();

		} catch ( ClientProtocolException cpee ) {
			throw new BVWireError( cpee );
		} catch ( IOException ioe ) {
			throw new BVWireError( ioe );
		} catch ( URISyntaxException use ) {
			throw new RuntimeException( use );
		}

		if ( 200 != statusCode ) {
			throw new LoginException();
		}

		isLoggedIn = true;
	}

	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	public List<Position> balance() {
		HttpGet method = new HttpGet( "https://live.bullionvault.com/secure/view_balance_xml.do" );
		return new DoMethodGetObject< List<Position> >( PositionsMessage.class ).fetch( client, method );
	}

	public List<Order> orders() {
		return orders( null, null, null );
	}

	public List<Order> orders(
			Security security,
			Currency considerationCurrency,
			OrderStatus status
	) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if ( null != considerationCurrency ) {
			params.add( new BasicNameValuePair( "considerationCurrency", considerationCurrency.getCurrencyCode() ) );
		}
		if ( null != security ) {
			params.add( new BasicNameValuePair( "securityId", security.getCode() ) );
		}
		if ( null != status ) {
			params.add( new BasicNameValuePair( "status", status.getCode() ) );
		}

		HttpGet method = new HttpGet( "https://live.bullionvault.com/secure/view_orders_xml.do" );
		return new DoMethodGetObject< List<Order> >( OrdersMessage.class ).fetch( client, method );
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
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if ( null != considerationCurrency ) {
			params.add( new BasicNameValuePair( "considerationCurrency", considerationCurrency.getCurrencyCode() ) );
		}
		if ( null != security ) {
			params.add( new BasicNameValuePair( "securityId", security.getCode() ) );
		}
		if ( null != quantity ) {
			params.add( new BasicNameValuePair( "quantity", new BigDecimal( quantity ).movePointLeft( 3 ).toPlainString() ) );
		}
		if ( null != marketWidth ) {
			params.add( new BasicNameValuePair( "marketWidth", marketWidth.toString() ) );
		}

		return new DoMethodGetObject< List<Price> >( PricesMessage.class ).fetch( client, "https", "live.bullionvault.com", "/view_market_xml.do", params );
	}

	public Order placeOrder( PlaceOrder order ) {

		if ( null != order.getGoodUntil() && OrderType.TIL_TIME != order.getType() ) {
			throw new IllegalArgumentException( "Good until time is for good until orders only." );
		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair( "actionIndicator", order.getActionIndicator().getCode() ) );
		params.add( new BasicNameValuePair( "considerationCurrency", order.getConsiderationCurrency().getCurrencyCode() ) );
		params.add( new BasicNameValuePair( "securityId", order.getSecurity().getCode() ) );
		params.add( new BasicNameValuePair( "quantity", new BigDecimal( order.getQuantity() ).movePointLeft( 3 ).toPlainString() ) );
		params.add( new BasicNameValuePair( "limit", Long.toString( order.getLimit() ) ) );
		params.add( new BasicNameValuePair( "typeCode", order.getType().getCode() ) );
		params.add( new BasicNameValuePair( "clientTransRef", order.getClientTransRef() ) );
		if ( null != order.getGoodUntil() ) {
			params.add( new BasicNameValuePair( "goodUntil", new SimpleDateFormat( "yyyy-MM-dd HH:mmZ" ).format( order.getGoodUntil() ) ) );
		}
		params.add( new BasicNameValuePair( "confirmed", "true" ) );

		HttpPost method = new HttpPost( "https://live.bullionvault.com/secure/place_order_xml.do" );
		return new DoMethodGetObject<Order>( PlaceOrderMessage.class ).fetch( client, method, params );
	}

	public void cancelOrder( Order order ) {
		cancelOrder( order.getId() ); 
	}

	public Order cancelOrder( Long orderId ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair( "orderId", orderId.toString() ) );

		HttpPost method = new HttpPost( "https://live.bullionvault.com/secure/cancel_order_xml.do" );
		return new DoMethodGetObject<Order>( SingleOrderMessage.class ).fetch( client, method, params );
	}

	public Order order( Long orderId ) {
		HttpGet method = new HttpGet( "https://live.bullionvault.com/secure/view_single_order_xml.do?orderId=" + orderId );
		return new DoMethodGetObject<Order>( SingleOrderMessage.class ).fetch( client, method );
	}

	public List<Deal> tickerDeals() {
		HttpGet method = new HttpGet( "https://live.bullionvault.com/view_ticker_xml.do" );
		return new DoMethodGetObject< List<Deal> >( Ticker.class ).fetch( client, method );
	}

	@SneakyThrows( { IOException.class, URISyntaxException.class } )
	public List<Price> marketDepth( Security security, Currency considerationCurrency ) {

		// Construct GET request
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair( "considerationCurrency", considerationCurrency.getCurrencyCode() ) );
		params.add( new BasicNameValuePair( "securityId", security.getCode() ) );
		params.add( new BasicNameValuePair( "marketWidth", "26" ) );
		params.add( new BasicNameValuePair( "priceInterval", "0" ) );
		URI uri = URIUtils.createURI(
			"http", "www.bullionvault.com", -1, "/view_market_depth.do",
			URLEncodedUtils.format( params, "UTF-8"), null
		);

		// Make HTTP request
		HttpGet method = new HttpGet( uri );
		HttpResponse response = client.execute( method );
		@Cleanup InputStream stream = response.getEntity().getContent();
		String document = new java.util.Scanner( stream ).useDelimiter("\\A").next();

		List<Price> prices = MarketDepth.parse( document );
		return prices;
	}

	public List<Price> wholesalePrice() {
		List<Price> prices = new ArrayList<Price>();

		for ( Security security : Security.values() ) {
			for ( Currency considerationCurrency : TradingCurrencies.values() ) {
				Price price = this.wholesalePrice( security, considerationCurrency );
				if ( null != price ) {
					prices.add( price );
				}
			}
		}

		return prices;
	}

	public Price wholesalePrice( Security security, Currency considerationCurrency ) {
		HttpGet method = new HttpGet( "http://www.galmarley.com/prices/CHART_LINE/" + security.getSecurityClass().getCode() + "/" + considerationCurrency + "/5/Full" );
		List<Price> prices = new DoMethodGetObject<List<Price>>( SpotPriceMessage.class ).fetch( client, method );

		Price price = prices.isEmpty() ? null : prices.get( prices.size() - 1 );
		if ( null != price ) {
			price.setConsiderationCurrency( considerationCurrency );
			price.setSecurity( security );
		}
		return price;
    }

}
