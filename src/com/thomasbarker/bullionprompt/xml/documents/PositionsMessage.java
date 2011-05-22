package com.thomasbarker.bullionprompt.xml.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.Getter;

import com.thomasbarker.bullionprompt.model.Position;

@XmlRootElement( name = "envelope" )
public final class PositionsMessage
	extends AbstractMessageDocument< List<Position> >
{
	@XmlElement( name = "message" )
	@Getter protected Message message;

	public static final class Message extends AbstractMessage {

		@XmlElement( name = "clientBalance" )
		@Getter protected Balance balance;

		@Getter protected String requiredType = "CLIENT_BALANCE";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.2" );
	}

	@XmlRootElement( name = "clientBalance" )
	@XmlAccessorType( XmlAccessType.FIELD )
	@Data
	public static final class Balance {

		@XmlElementWrapper( name = "clientPositions" )
	    @XmlElement( name = "clientPosition" )
		protected List<Position> positions = new ArrayList<Position>();
	}

	public List<Position> getContent() {
		return getMessage().getBalance().getPositions();
	}

}
