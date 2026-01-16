package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Position;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class PositionsMessage
	extends AbstractMessageDocument< List<Position> >
{
	@XmlElement( name = "message" )
	protected Message message;

	public static final class Message extends AbstractMessage
	{
		@XmlElement( name = "clientBalance" )
		public ClientBalance clientBalance;

		@Override
		protected String getRequiredType() {
			return "CLIENT_BALANCE";
		}
		@Override
		protected BigDecimal getRequiredVersion() {
			return new BigDecimal( "0.2" );
		}
	}

	public static final class ClientBalance {
		@XmlElementWrapper( name = "clientPositions" )
		@XmlElement( name = "clientPosition" )
		public List<Position> clientPositions = new ArrayList<Position>();
	}

	public Message getMessage() {
		return message;
	}

	public List<Position> getContent() {
		return message.clientBalance.clientPositions;
	}

}
