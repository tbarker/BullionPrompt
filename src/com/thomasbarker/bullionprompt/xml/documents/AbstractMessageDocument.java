package com.thomasbarker.bullionprompt.xml.documents;


import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD )

public abstract class AbstractMessageDocument<T>
	implements MessageContainer<T>
{
	public abstract AbstractMessage getMessage();

	@XmlRootElement( name = "message" )
	@XmlAccessorType( XmlAccessType.FIELD )
	public static abstract class AbstractMessage {

		@XmlAttribute public String type;
		@XmlAttribute public BigDecimal version;

		protected abstract String getRequiredType();
		protected abstract BigDecimal getRequiredVersion();

		final void afterUnmarshal( Unmarshaller unmarshaller, Object parent)
			throws JAXBException
		{
			if ( !type.startsWith( getRequiredType() ) ) {
				throw new JAXBException( "Type is " + type + " not " + getRequiredType() );
			}
			if ( 0 != version.compareTo( getRequiredVersion() ) ) {
				throw new JAXBException( "Version is " + version + " not " + getRequiredVersion() );
			}
		}

		@XmlElementWrapper( name = "errors" )
	    @XmlElement( name = "error" )
	    public List<String> errors = new ArrayList<String>();
	}

	@XmlElementWrapper( name = "errors" )
    @XmlElement( name = "error" )
    public List<String> errors = new ArrayList<String>();

	public List<String> getErrors() {
		return errors;
	}

	final void afterUnmarshal( Unmarshaller unmarshaller, Object parent)
		throws JAXBException
	{
		if ( getMessage().type.endsWith( "_E" ) ) {
			errors.add( "BV API error envelope." );
		}

		errors.addAll( getMessage().errors );

		fixUp();
	}

	// Allow safe extension of afterUnmarshal
	protected void fixUp() { ; }
}
