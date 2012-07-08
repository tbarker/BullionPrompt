package com.thomasbarker.bullionprompt.cli.commands;

import java.text.SimpleDateFormat;

import com.thomasbarker.bullionprompt.model.Deal;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.Position;
import com.thomasbarker.bullionprompt.model.Price;

public final class PrettyPrint {

	final static SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm" );

	public static void deal( Deal deal ) {
		System.out.print( deal.getSecurity() + "\t" );
		System.out.print( deal.getConsiderationCurrency() + "\t" );
		System.out.print( deal.getDealTime() + "\t" );
		System.out.print( deal.getPricePerUnit() + "\t" );
		System.out.print( deal.getQuantity() + "\n" );
	}

	public static void price( Price price ) {
		System.out.print( price.getSecurity() + "\t" );
		System.out.print( price.getConsiderationCurrency() + "\t" );
		System.out.print( ( null == price.getActionIndicator() ? "SPOT" : price.getActionIndicator() ) + "\t" );
		System.out.print( price.getPrice() + "\t" );
		System.out.print( price.getQuantity() + "\n" );
	}

	public static void position( Position position ) {
		System.out.print( position.getSecurity() + "\t" );
		System.out.print( position.getTotal() + "\t" );
		System.out.print( position.getAvailable() + "\t" );
		System.out.print( position.getValuationCurrency() + "\t" );
		System.out.print( position.getValuation() + "\t" );
		System.out.print( position.getNarrative() + "\n" );
	}

	public static void order( Order order ) {
		System.out.print( order.getId() +"\t" );
		System.out.print( dateFormat.format( order.getTime() ) +"\t" );
		System.out.print( order.getActionIndicator().name() +"\t" );
		System.out.print( order.getSecurity().getCode() +"\t" );
		System.out.print( order.getConsiderationCurrency() +"\t" );
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
