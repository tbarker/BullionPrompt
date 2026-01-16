package com.thomasbarker.bullionprompt.cli.commands;

import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.PendingSettlement;

@Parameters( commandDescription = "Display pending settlements." )
public final class DisplayPendingSettlements extends AbstractAccountCommand {

	@Override
	protected void perform() {
		for( PendingSettlement pendingSettlement : session.pendingSettlements() ) {
			PrettyPrint.pendingSettlement( pendingSettlement );
		}
	}

}
