package com.thomasbarker.bullionprompt.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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

import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.BullionVaultErrors;
import com.thomasbarker.bullionprompt.xml.documents.MessageContainer;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class DoMethodGetObject<T> {

	private final Class modelClass;

	public T fetch( HttpClient client, HttpPost method, List<NameValuePair> params ) {
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity( params, "UTF-8" );
		} catch ( UnsupportedEncodingException uee ) {
			throw new RuntimeException( uee );
		}
		method.setEntity( entity );

		return fetch( client, method );
	}

	public T fetch( HttpClient client, String schema, String host, String path, List<NameValuePair> params ) {
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
		return fetch( client, method );
	}

	public T fetch( HttpClient client, HttpUriRequest method ) {

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