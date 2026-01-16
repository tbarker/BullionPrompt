package com.thomasbarker.bullionprompt.cli.commands;

import com.thomasbarker.bullionprompt.model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PrettyPrint {

	final static SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm" );

	public static void deal( Deal deal ) {
		tabSeparatedPrint(
			deal.security,
			deal.considerationCurrency,
			deal.dealTime,
			deal.pricePerUnit,
			deal.quantity
		);
	}

	public static void price( Price price ) {
		tabSeparatedPrint(
			price.security,
			price.considerationCurrency,
			( null == price.actionIndicator ? "SPOT" : price.actionIndicator ),
			price.price,
			price.quantity
		);
	}

	public static void position( Position position ) {
		tabSeparatedPrint(
			position.security,
			position.total,
			position.available,
			position.valuationCurrency,
			position.valuation,
			position.narrative
		);
	}

	public static void pendingSettlement( PendingSettlement pendingSettlement ) {
		tabSeparatedPrint(
			pendingSettlement.security,
			pendingSettlement.total,
			pendingSettlement.narrative,
			pendingSettlement.valuation,
			pendingSettlement.valuationCurrency
		);
	}

	public static void order( Order order ) {
		List<Object> stringsToPrint = new ArrayList<Object>();

		stringsToPrint.addAll( Arrays.<Object>asList( new Object[] {
			order.id,
			dateFormat.format( order.time ),
			order.actionIndicator.name(),
			order.security.getCode(),
			order.considerationCurrency,
			order.quantity,
			order.limit,
			order.status.getCode(),
			order.quantityMatched
		} ) );

		if ( null != order.goodUntil ) {
			stringsToPrint.add( dateFormat.format( order.goodUntil ) );
		}

		tabSeparatedPrint( stringsToPrint.toArray() );
	}

	private static void tabSeparatedPrint( Object ... args ) {

		int count = 0;
		for( Object arg : args ) {
			count++;
			if ( count != args.length ) {
				System.out.print( arg );
				System.out.print( '\t' );
			}
		}

		System.out.print( args[args.length-1] );
		System.out.print( '\n' );
	}
}
