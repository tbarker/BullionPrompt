package com.thomasbarker.bullionprompt.error;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public final class BVWireError extends RuntimeException {

	public BVWireError( IllegalStateException ise ) {
		super( ise );
	}

	public BVWireError( ClientProtocolException cpee ) {
		super( cpee );
	}

	public BVWireError( JAXBException je ) {
		super( je );
	}

	public BVWireError( IOException ioe ) {
		super( ioe );
	}

	public BVWireError( ParserConfigurationException pce ) {
		super( pce );
	}

	public BVWireError( SAXException se ) {
		super( se );
	}
}
