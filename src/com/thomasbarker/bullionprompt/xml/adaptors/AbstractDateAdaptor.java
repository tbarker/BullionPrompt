package com.thomasbarker.bullionprompt.xml.adaptors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public abstract class AbstractDateAdaptor extends XmlAdapter<String, Date> {

	abstract protected String getFormat();

	DateFormat dateFormat = new SimpleDateFormat( getFormat() );

	public Date unmarshal( String date ) throws Exception {
		return dateFormat.parse( date );
	}

	public String marshal( Date date ) throws Exception {
		return dateFormat.format( date );
	}

}
