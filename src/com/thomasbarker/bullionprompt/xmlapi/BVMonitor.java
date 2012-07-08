package com.thomasbarker.bullionprompt.xmlapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thomasbarker.bullionprompt.model.Deal;
import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.Price;

public final class BVMonitor {

	private BVSession session;
	private Set<BVListener> listeners = new HashSet<BVListener>();

	private static Set<Deal> lastExecutions = new HashSet<Deal>();
	private static Set<Price> lastSpotPrices = new HashSet<Price>();
	private static Set<Price> lastMarketPrices = new HashSet<Price>();
	private static Set<Order> lastAccountOrderStates = new HashSet<Order>();

	public BVMonitor( BVSession session ) {
		this.session = session;
	}

	public void addBVListener( BVListener listener ) {
		listeners.add( listener );
	}

	public void removeBVListener( BVListener listener ) {
		listeners.remove( listener );
	}

	// Needs to be called at least every 5 seconds
	public void tick() {

		// Notify on latest deals
		for ( Deal deal : fresh( lastExecutions, session.tickerDeals() ) ) {
			for ( BVListener listener : listeners ) {
				listener.deal( deal );
			}
		}

		// Notify on latest BV market prices
		for ( Price price : fresh( lastMarketPrices, session.markets() ) ) {
			for ( BVListener listener : listeners ) {
				listener.marketPrice( price );
			}
		}

		// Notify on latest wholesale spot market prices
		for ( Price price : fresh( lastSpotPrices, session.wholesalePrice() ) ) {
			for ( BVListener listener : listeners ) {
				listener.spotPrice( price );
			}
		}

		// If the session is logged in, report on changes to orders
		if ( session.isLoggedIn() ) {
			for ( Order order : fresh( lastAccountOrderStates, session.orders() ) ) {
				for ( BVListener listener : listeners ) {
					listener.execution( order );
				}
			}
		}
	}

	public interface BVListener {

		void spotPrice( Price price );

		void marketPrice( Price price );

		void deal( Deal deal );

		void execution( Order order );

	}

	private static <T> List<T> fresh( Set<T> previous, List<T> current ) {

		// What's new?
		List<T> fresh = new ArrayList<T>();
		fresh.addAll( current );
		fresh.removeAll( previous );

		// Mutate previous observation to the latest
		previous.clear();
		previous.addAll( current );
		
		return fresh;
	}
}
