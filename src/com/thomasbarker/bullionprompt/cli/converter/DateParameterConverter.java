package com.thomasbarker.bullionprompt.cli.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.beust.jcommander.IStringConverter;

public final class DateParameterConverter implements IStringConverter<Date> {

	public Date convert( String dateText ) {
		try {
			return new SimpleDateFormat( "yyyy-MM-dd-HH:mm:ss" ).parse( dateText );
		} catch ( ParseException e ) {
			return null;
		}
	}

}
