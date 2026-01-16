package com.thomasbarker.bullionprompt.cli.commands;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.model.Order;

@Parameters( commandDescription = "Cancel an existing market order." )
public final class CancelOrderAction extends AbstractAccountCommand {

	@Parameter( names = { "--order", "-o" }, required = true, description = "Order ID number" )
	private Long orderId;

	@Override
	protected void perform() {
		Order order = session.cancelOrder( orderId );
		System.out.println( order.status );
	}

}
