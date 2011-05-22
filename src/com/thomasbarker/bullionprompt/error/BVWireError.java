package com.thomasbarker.bullionprompt.error;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.http.client.ClientProtocolException;

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

}
