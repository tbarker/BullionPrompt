package com.thomasbarker.bullionprompt.xml.documents;

import com.thomasbarker.bullionprompt.model.Position;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement( name = "envelope" )
public final class PositionsMessage
	extends AbstractBalanceMessage< List<Position> >
{
	public List<Position> getContent() {
		return getMessage().getBalance().getPositions();
	}
}
