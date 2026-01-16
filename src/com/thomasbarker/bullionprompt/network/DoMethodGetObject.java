package com.thomasbarker.bullionprompt.network;

import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.BullionVaultErrors;
import com.thomasbarker.bullionprompt.xml.documents.MessageContainer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@SuppressWarnings("unchecked")
public class DoMethodGetObject<T> {

	@SuppressWarnings( "rawtypes" )
	private final Class modelClass;

	public DoMethodGetObject(Class modelClass) {
		this.modelClass = modelClass;
	}

	/**
	 * Fetch with POST form data
	 */
	public T fetch( HttpClient client, URI uri, Map<String, String> formData ) throws IOException, InterruptedException {
		String formBody = encodeFormData(formData);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(uri)
			.header("Content-Type", "application/x-www-form-urlencoded")
			.POST(HttpRequest.BodyPublishers.ofString(formBody))
			.build();

		return fetch(client, request);
	}

	/**
	 * Fetch with GET request
	 */
	public T fetch( HttpClient client, String schema, String host, String path, Map<String, String> params ) throws IOException, InterruptedException {
		String query = encodeFormData(params);
		URI uri = URI.create(schema + "://" + host + path + (query.isEmpty() ? "" : "?" + query));

		HttpRequest request = HttpRequest.newBuilder()
			.uri(uri)
			.GET()
			.build();

		return fetch(client, request);
	}

	/**
	 * Fetch with HttpRequest
	 */
	public T fetch( HttpClient client, HttpRequest request ) throws IOException, InterruptedException {
		return fetch( fetchXML( client, request ) );
	}

	/**
	 * Parse XML document to object
	 */
	public T fetch( Document document ) {
		// Map to object
		JAXBContext context;
		Unmarshaller unmarshaller;
		MessageContainer message;
		try {
			context = JAXBContext.newInstance( modelClass );
			unmarshaller = context.createUnmarshaller();
			message = ( (MessageContainer) unmarshaller.unmarshal( document ) );
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

	/**
	 * Fetch XML document from HTTP request
	 */
	public static Document fetchXML( HttpClient client, HttpRequest request ) throws IOException, InterruptedException {
		try {
			// Read the response body
			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

			try (InputStream stream = response.body()) {
				// Parse to XML
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				return dBuilder.parse( stream );
			}

		} catch ( ParserConfigurationException pce ) {
			throw new BVWireError( pce );
		} catch ( SAXException se ) {
			throw new BVWireError( se );
		}
	}

	/**
	 * URL-encode form data
	 */
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