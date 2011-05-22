package com.thomasbarker.bullionprompt.cli.commands;

import java.util.Currency;
import java.util.Date;
import java.util.Random;

import lombok.Getter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.thomasbarker.bullionprompt.cli.converter.ActionIndicatorParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.CurrencyParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.DateParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.OrderTypeParameterConverter;
import com.thomasbarker.bullionprompt.cli.converter.SecurityParameterConverter;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.PlaceOrder;
import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;

@Parameters( commandDescription = "Place a live market order." )
public final class PlaceOrderAction
	extends AbstractAccountCommand
	implements PlaceOrder
{
	@Parameter(
		names = { "--action", "-a" },
		description = "BUY/SELL",
		converter = ActionIndicatorParameterConverter.class,
		required = true
	)
	@Getter public ActionIndicator actionIndicator;

	@Parameter( names = { "--ref", "-r" }, required = false, description = "Your reference code. Must be unique for this account." )
	@Getter public String clientTransRef = Integer.toString( new Random( System.nanoTime() ).nextInt( 99999999 ) );

	@Parameter( names = { "--currency", "-c" }, converter = CurrencyParameterConverter.class, required = true )
	@Getter public Currency considerationCurrency;

	@Parameter(
		names = { "--goodUntil", "-g" },
		description = "In the format yyyy-MM-dd-hh:mm.  Til time orders only.",
		converter = DateParameterConverter.class,
		required = false
	)
	@Getter public Date goodUntil;

	@Parameter( names = { "--limit", "-l" }, description = "Price in cents/pence", required = true )
	@Getter public Long limit;

	@Parameter( names = { "--quantity", "-q" }, description = "In grams", required = true )
	@Getter public Long quantity;

	@Parameter( names = { "--security", "-s" }, required = true, converter = SecurityParameterConverter.class )
	@Getter public Security security;

	@Parameter(
		names = { "--type", "-t" },
		description = "One of TIL_CANCEL (Good until cancelled), TIL_TIME (Good until time), IMMEDIATE (Execute immediate) or FILL_KILL (Fill or kill).",
		required = true,
		converter = OrderTypeParameterConverter.class
	)
	@Getter public OrderType type;

	@Override
	protected void perform() {
		Order order = session.placeOrder( this );
		System.out.println( order.getId() + "\t" + order.getStatus() + "\t" + order.getQuantityMatched() );
	}

}
