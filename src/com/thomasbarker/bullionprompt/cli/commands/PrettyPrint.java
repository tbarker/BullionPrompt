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
			deal.getSecurity(),
			deal.getConsiderationCurrency(),
			deal.getDealTime(),
			deal.getPricePerUnit(),
			deal.getQuantity()
		);
	}

	public static void price( Price price ) {
		tabSeparatedPrint(
			price.getSecurity(),
			price.getConsiderationCurrency(),
			( null == price.getActionIndicator() ? "SPOT" : price.getActionIndicator() ),
			price.getPrice(),
			price.getQuantity()
		);
	}

	public static void position( Position position ) {
		tabSeparatedPrint(
			position.getSecurity(),
			position.getTotal(),
			position.getAvailable(),
			position.getValuationCurrency(),
			position.getValuation(),
			position.getNarrative()
		);
	}

	public static void pendingSettlement( PendingSettlement pendingSettlement ) {
		tabSeparatedPrint(
			pendingSettlement.getSecurity(),
			pendingSettlement.getTotal(),
			pendingSettlement.getNarrative(),
			pendingSettlement.getValuation(),
			pendingSettlement.getValuationCurrency()
		);
	}

	public static void order( Order order ) {
		List<Object> stringsToPrint = new ArrayList<Object>();

		stringsToPrint.addAll( Arrays.<Object>asList( new Object[] {
			order.getId(),
			dateFormat.format( order.getTime() ),
			order.getActionIndicator().name(),
			order.getSecurity().getCode(),
			order.getConsiderationCurrency(),
			order.getQuantity(),
			order.getLimit(),
			order.getStatus().getCode(),
			order.getQuantityMatched()
		} ) );

		if ( null != order.getGoodUntil() ) {
			stringsToPrint.add( dateFormat.format( order.getGoodUntil() ) );
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
