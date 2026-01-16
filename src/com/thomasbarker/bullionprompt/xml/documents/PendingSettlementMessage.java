package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.PendingSettlement;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class PendingSettlementMessage
	extends AbstractMessageDocument< List<PendingSettlement> >
{
	@XmlElement( name = "message" )
	@Getter protected Message message;

	public static final class Message extends AbstractMessage
	{
		@XmlElement( name = "clientBalance" )
		@Getter
		private ClientBalance clientBalance;

		@Getter protected String requiredType = "CLIENT_BALANCE";
		@Getter protected BigDecimal requiredVersion = new BigDecimal( "0.2" );
	}

	public static final class ClientBalance {
		@XmlElementWrapper( name = "pendingSettlements" )
		@XmlElement( name = "pendingSettlement" )
		@Getter
		private List<PendingSettlement> pendingSettlements = new ArrayList<PendingSettlement>();
	}

	public List<PendingSettlement> getContent() {
		return getMessage().getClientBalance().getPendingSettlements();
	}
}
