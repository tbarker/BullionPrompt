package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.PendingSettlement;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class PendingSettlementMessage
	extends AbstractMessageDocument< List<PendingSettlement> >
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
		@XmlElementWrapper( name = "pendingSettlements" )
		@XmlElement( name = "pendingSettlement" )
		public List<PendingSettlement> pendingSettlements = new ArrayList<PendingSettlement>();
	}

	public Message getMessage() {
		return message;
	}

	public List<PendingSettlement> getContent() {
		return message.clientBalance.pendingSettlements;
	}

}
