package com.thomasbarker.bullionprompt.cli.commands;

import com.thomasbarker.bullionprompt.model.Deal;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.xmlapi.BVMonitor;
import com.thomasbarker.bullionprompt.xmlapi.BVMonitor.BVListener;


public final class WatchAction extends Command {

	@Override
	public void execute() {

		BVMonitor monitor = new BVMonitor( session );
		monitor.addBVListener( new PrintingListener() );

		while ( true ) {
			monitor.tick();
			try {
				Thread.sleep( 4500 );
			} catch ( InterruptedException e ) {
				break;
			}
		}
	}

	private class PrintingListener implements BVListener {

		public void spotPrice( Price price ) {
			PrettyPrint.price( price );
		}

		public void marketPrice( Price price ) {
			PrettyPrint.price( price );
			
		}

		public void deal( Deal deal ) {
			PrettyPrint.deal( deal );
		}

		public void execution( Order order ) {
			PrettyPrint.order( order );
		}

	}
}
