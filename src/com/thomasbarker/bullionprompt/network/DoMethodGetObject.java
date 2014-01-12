package com.thomasbarker.bullionprompt.network;

import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.BullionVaultErrors;
import com.thomasbarker.bullionprompt.xml.documents.MessageContainer;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class DoMethodGetObject<T> {

	@SuppressWarnings( "rawtypes" )
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
		return fetch( fetchXML( client, method ) );
	}

	public T fetch( Document document ) {

		// Map to object
		JAXBContext context = null;
		Unmarshaller unmarshaller = null;
		MessageContainer message = null;
		try {
			context = JAXBContext.newInstance( modelClass );
			unmarshaller = context.createUnmarshaller();
			unmarshaller.unmarshal( document );
			message = ( (MessageContainer) unmarshaller.unmarshal( document.getFirstChild() ) );
		} catch ( JAXBException je ) {
			throw new BVWireError( je );
		}

		// Return contents
		if ( message.getErrors().isEmpty() ) {
			return (T) message.getContent();
		} else {
			throw new BullionVaultErrors( message.getErrors() );
		}

	}

	public static Document fetchXML( HttpClient client, HttpUriRequest method ) {

		try {

			// Read the response body.
			HttpResponse response = client.execute( method );
			@Cleanup InputStream stream = response.getEntity().getContent();

			// Parse to XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document =  dBuilder.parse( stream );

			return document;

		} catch ( ClientProtocolException cpee ) {
			throw new BVWireError( cpee );
		} catch ( IOException ioe ) {
			throw new BVWireError( ioe );
		} catch ( IllegalStateException ise ) {
			throw new BVWireError( ise );
		} catch ( ParserConfigurationException pce ) {
			throw new BVWireError( pce );
		} catch ( SAXException se ) {
			throw new BVWireError( se );
		}

	}
}