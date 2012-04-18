package com.thomasbarker.bullionprompt.xmlapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;

import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.BullionVaultErrors;
import com.thomasbarker.bullionprompt.error.LoginException;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.PlaceOrder;
import com.thomasbarker.bullionprompt.model.Position;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.OrderStatus;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.documents.MessageContainer;
import com.thomasbarker.bullionprompt.xml.documents.OrdersMessage;
import com.thomasbarker.bullionprompt.xml.documents.PlaceOrderMessage;
import com.thomasbarker.bullionprompt.xml.documents.PositionsMessage;
import com.thomasbarker.bullionprompt.xml.documents.PricesMessage;
import com.thomasbarker.bullionprompt.xml.documents.SingleOrderMessage;
import com.thomasbarker.bullionprompt.xml.documents.SpotPriceMessage;

/**
 * Wraps http://www.bullionvault.com/help/?xml_api.html
 * 
 */
public final class BVSession {

	private HttpClient client = getThreadSafeClient();

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
	}

	public List<Position> balance() {
		HttpGet method = new HttpGet( "https://live.bullionvault.com/secure/view_balance_xml.do" );
		return new DoMethodGetObject< List<Position> >( PositionsMessage.class ).fetch( method );
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
		return new DoMethodGetObject< List<Order> >( OrdersMessage.class ).fetch( method );
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

		return new DoMethodGetObject< List<Price> >( PricesMessage.class ).fetch( "https", "live.bullionvault.com", "/view_market_xml.do", params );
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
		return new DoMethodGetObject<Order>( PlaceOrderMessage.class ).fetch( method, params );
	}

	public void cancelOrder( Order order ) {
		cancelOrder( order.getId() ); 
	}

	public Order cancelOrder( Long orderId ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add( new BasicNameValuePair( "orderId", orderId.toString() ) );

		HttpPost method = new HttpPost( "https://live.bullionvault.com/secure/cancel_order_xml.do" );
		return new DoMethodGetObject<Order>( SingleOrderMessage.class ).fetch( method, params );
	}

	public Order order( Long orderId ) {
		HttpGet method = new HttpGet( "https://live.bullionvault.com/secure/view_single_order_xml.do?orderId=" + orderId );
		return new DoMethodGetObject<Order>( SingleOrderMessage.class ).fetch( method );
	}

	@RequiredArgsConstructor
	@SuppressWarnings("unchecked")
	private class DoMethodGetObject<T> {

		private final Class modelClass;

		public T fetch( HttpPost method, List<NameValuePair> params ) {
			UrlEncodedFormEntity entity;
			try {
				entity = new UrlEncodedFormEntity( params, "UTF-8" );
			} catch ( UnsupportedEncodingException uee ) {
				throw new RuntimeException( uee );
			}
			method.setEntity( entity );

			return fetch( method );
		}

		public T fetch( String schema, String host, String path, List<NameValuePair> params ) {
			URI uri;
			try {
				uri = URIUtils.createURI(
					schema, host, -1, path, 
					URLEncodedUtils.format( params, "UTF-8" ), null
				);
			} catch ( URISyntaxException use ) {
				throw new RuntimeException( use );
			}

			HttpGet method = new HttpGet( uri );
			return fetch( method );
		}

		public T fetch( HttpUriRequest method ) {

			T values = null;
			try {

				// Read the response body.
				HttpResponse response = client.execute( method );
				@Cleanup InputStream stream = response.getEntity().getContent();

				// Process
				JAXBContext context = JAXBContext.newInstance( modelClass );
				Unmarshaller unmarshaller = context.createUnmarshaller();
				MessageContainer message = ( (MessageContainer) unmarshaller.unmarshal( stream ) );

				if ( message.getErrors().isEmpty() ) {
					values = (T) message.getContent();
				} else {
					throw new BullionVaultErrors( message.getErrors() );
				}

			} catch ( ClientProtocolException cpee ) {
				throw new BVWireError( cpee );
			} catch ( IOException ioe ) {
				throw new BVWireError( ioe );
			} catch ( IllegalStateException ise ) {
				throw new BVWireError( ise );
			} catch ( JAXBException je ) {
				throw new BVWireError( je );
			}

			return values;
		}

	}

	public Price wholesalePrice( Security security, Currency considerationCurrency ) {
		HttpGet method = new HttpGet( "http://www.galmarley.com/prices/CHART_LINE/" + security.getSecurityClass().getCode() + "/" + considerationCurrency + "/5/Full" );
		List<Price> prices = new DoMethodGetObject<List<Price>>( SpotPriceMessage.class ).fetch( method );

		return prices.get( prices.size() - 1 );
    }

	public static DefaultHttpClient getThreadSafeClient() {
		DefaultHttpClient client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		client = new DefaultHttpClient( new ThreadSafeClientConnManager( mgr.getSchemeRegistry() ) );

		return client;
	}

}
