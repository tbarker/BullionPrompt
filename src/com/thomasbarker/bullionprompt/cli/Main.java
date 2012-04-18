package com.thomasbarker.bullionprompt.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.thomasbarker.bullionprompt.cli.commands.CancelOrderAction;
import com.thomasbarker.bullionprompt.cli.commands.CheckSpot;
import com.thomasbarker.bullionprompt.cli.commands.Command;
import com.thomasbarker.bullionprompt.cli.commands.DisplayAccountBalances;
import com.thomasbarker.bullionprompt.cli.commands.DisplayLastDeals;
import com.thomasbarker.bullionprompt.cli.commands.DisplayMarket;
import com.thomasbarker.bullionprompt.cli.commands.DisplayOrders;
import com.thomasbarker.bullionprompt.cli.commands.Help;
import com.thomasbarker.bullionprompt.cli.commands.PlaceOrderAction;
import com.thomasbarker.bullionprompt.cli.commands.TailMarket;
import com.thomasbarker.bullionprompt.error.BVWireError;
import com.thomasbarker.bullionprompt.error.BullionVaultErrors;
import com.thomasbarker.bullionprompt.error.LoginException;

public final class Main {

	public static void main( String[] args ) {

		Map<String, Command> commands = new HashMap<String, Command>();

		commands.put( "tail",		new TailMarket() );
		commands.put( "market",		new DisplayMarket() );
		commands.put( "balance",	new DisplayAccountBalances() );
		commands.put( "orders",		new DisplayOrders() );
		commands.put( "place",		new PlaceOrderAction() );
		commands.put( "cancel",		new CancelOrderAction() );
		commands.put( "spot",		new CheckSpot() );
		commands.put( "deals",		new DisplayLastDeals() );

		JCommander commander = new JCommander();
		for( Entry<String, Command> commandEntry : commands.entrySet() ) {
			commander.addCommand( commandEntry.getKey(), commandEntry.getValue() );
		}

		// Inject special help command
		Help help = new Help( commander );
		commander.addCommand( "help", help );

		try {
			commander.parse( args );
		} catch ( ParameterException pe ) {
			System.err.println( pe.getLocalizedMessage() );
			help.usage();
			siteReference();
			System.exit( 1 );			
		}

		if ( null == commander.getParsedCommand() ) {
			commander.usage();
			siteReference();
			System.exit( 1 );
		}

		if ( "help".equals( commander.getParsedCommand() ) ) {
			help.usage();
			siteReference();
			System.exit( 1 );
		}

		Command command = (Command) commands.get( commander.getParsedCommand() );

		// Actually do the command
		try {
			command.start();
		} catch ( BullionVaultErrors bve ) {
			System.err.println( "-- Errors from BullionVault --" );
			for ( String error : bve.getErrors() ) {
				System.err.println( "\t" + error );
			}
			System.exit( 1 );
		} catch ( BVWireError bwe ) {
			System.err.println( "-- Networking failure (Use --wire to debug.) --" );
			System.err.println( bwe.getMessage() );
			bwe.printStackTrace();
			System.exit( 1 );
		} catch ( LoginException le ) {
			System.err.println( "-- Login Failed --" );
			System.exit( 1 );
		}
	}

	private static void siteReference() {
		System.out.println( "See http://www.bullionvault.com/help/?xml_api.html for more." );
		System.out.println();
	}
}
