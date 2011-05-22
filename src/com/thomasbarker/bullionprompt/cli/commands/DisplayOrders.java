package com.thomasbarker.bullionprompt.cli.commands;

import java.util.Currency;

import lombok.Getter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.cli.converter.CurrencyParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.OrderStatusParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.SecurityParameterConverter;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.enums.OrderStatus;
import com.thomasbarker.bullionprompt.model.enums.Security;

@Parameters( commandDescription = "Display your (by default only active) orders.  Or a single given order." )
public final class DisplayOrders extends AbstractAccountCommand {

	@Parameter( names = { "--order", "-o" }, description = "Order ID number", required = false )
	@Getter private Long orderId;

	@Parameter( names = { "--security", "-s" }, required = false, converter = SecurityParameterConverter.class )
	@Getter public Security security;

	@Parameter( names = { "--currency", "-c" }, converter = CurrencyParameterConverter.class, required = false )
	@Getter public Currency considerationCurrency;

	@Parameter( names = { "--status", "-os" },
		description = "One of OPEN, DONE, EXPIRED, CANCELLED, KILLED, NOFUNDS, BADLIMIT, SILVER_RESTRICTED, QUEUED",
		required = false,
		converter = OrderStatusParameterConverter.class
	)
	@Getter public OrderStatus status;

	@Override
	protected void perform() {
		if ( null == orderId ) {
			for( Order order : session.orders() ) {
				printOrder( order );
			}
		} else {
			printOrder( session.order( orderId ) );
		}
	}

	private void printOrder( Order order ) {
		System.out.print( order.getId() +"\t" );
		System.out.print( dateFormat.format( order.getTime() ) +"\t" );
		System.out.print( order.getActionIndicator().name() +"\t" );
		System.out.print( order.getSecurity().getCode() +"\t" );
		System.out.print( order.getConsiderationCurrency().getCurrencyCode() +"\t" );
		System.out.print( order.getQuantity() +"\t" );
		System.out.print( order.getLimit() +"\t" );
		System.out.print( order.getStatus().getCode() +"\t" );
		System.out.print( order.getQuantityMatched() +"\t" );
		if ( null != order.getGoodUntil() ) {
			System.out.print( dateFormat.format( order.getGoodUntil() ) +"\t" );
		}
		System.out.print( "\n" );
	}

}
