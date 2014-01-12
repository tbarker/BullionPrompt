package com.thomasbarker.bullionprompt.xmlapi.test;

import com.thomasbarker.bullionprompt.model.*;
import com.thomasbarker.bullionprompt.model.enums.*;
import com.thomasbarker.bullionprompt.xml.documents.*;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class ReaderTests {

	@Test
	public final void testReadPrices() {
		Price p = ( (List<Price>) readTestFileAsModel( "samplePrices.xml", PricesMessage.class ) ).get(1);

		assertEquals(  Security.AUXLN, p.getSecurity() );
		assertEquals(  200, p.getQuantity() );
		assertEquals(  ActionIndicator.BUY, p.getActionIndicator() );

		p = ( (List<Price>) readTestFileAsModel( "samplePrices.xml", PricesMessage.class ) ).get(3);
		assertEquals(  ActionIndicator.SELL, p.getActionIndicator() );
	}

	@Test
	@SuppressWarnings("deprecation")
	public final void testReadOrders() {
		Order o = ( (List<Order>) readTestFileAsModel( "sampleOrders.xml", OrdersMessage.class ) ).get(1);

		assertEquals( 139721159, o.getId().longValue() );
		assertEquals( "120209000332633", o.getClientTransRef() );
		assertEquals( ActionIndicator.BUY, o.getActionIndicator() );
		assertEquals( Security.AGXLN, o.getSecurity() );
		assertEquals( Currency.getInstance("USD"), o.getConsiderationCurrency() );
		assertEquals( 110, o.getTotalConsideration().longValue() );
		assertEquals( 1, o.getTotalCommission().longValue() );
		assertEquals( 110400, o.getLimit().longValue() );
		assertEquals( OrderType.TIL_CANCEL, o.getType() );
		assertEquals( 51, o.getTime().getSeconds() );
		assertNull( o.getGoodUntil() );
		assertEquals( 2012, o.getLastModified().getYear() + 1900);
		assertEquals( o.getStatus(), OrderStatus.DONE);
		assertEquals( 1, o.getQuantity().longValue(), 0 );
		assertEquals( 1, o.getQuantityMatched().longValue(), 0 );
	}

	@Test
	@SuppressWarnings("deprecation")
	public final void testReadSingleOrder() {
		Order o = (Order) readTestFileAsModel( "sampleOrder.xml", SingleOrderMessage.class );
		assertEquals( OrderStatus.TO_BE_SETTLED, o.getStatus() );
		assertEquals( TradeType.CLIENT_ORDER, o.getTradeType() );
		assertEquals( Security.AGXTR, o.getSecurity() );
		assertEquals( Boolean.FALSE, o.getCancellable() );
	}

	@Test
	public final void testReadBalances() {
		Position p = ( (List<Position>) readTestFileAsModel( "sampleBalance.xml", PositionsMessage.class ) ).get(1);

		assertEquals( Security.AUXNY, p.getSecurity() );
		assertEquals( 5000, p.getAvailable().longValue() );
		assertEquals( 5000, p.getTotal().longValue() );
		assertEquals( "GOLD", p.getNarrative() );
		assertEquals( 6705000, p.getValuation().longValue() );
		assertEquals( Currency.getInstance("USD"), p.getValuationCurrency() );
	}

	@Test
	@SuppressWarnings("deprecation")
	public final void testReadCompleteBalances() throws JAXBException, IOException {

		@Cleanup InputStream stream = ReaderTests.class.getResource( "sampleCompleteBalance.xml" ).openStream();

		// Process
		JAXBContext context = JAXBContext.newInstance( PendingSettlementMessage.class );
		Unmarshaller unmarshaller = context.createUnmarshaller();
		MessageContainer message = ( (MessageContainer) unmarshaller.unmarshal( stream ) );

		assertNotNull( message );
		assertTrue( ( (Collection) message.getContent() ).size() > 0 );
		assertNotNull( message.getContent() );

		PendingSettlement pendingSettlement = ( (List<PendingSettlement>) message.getContent() ).get( 0 );
		assertEquals( Security.AUXZU, pendingSettlement.getSecurity() );
		assertEquals( 2229, pendingSettlement.getTotal().longValue() );
		assertEquals( "GOLD", pendingSettlement.getNarrative() );
		assertEquals( 8784489, pendingSettlement.getValuation().longValue() );
		assertEquals( Currency.getInstance("USD"), pendingSettlement.getValuationCurrency() );

		PendingTransfer pendingTransfer = pendingSettlement.getTransfers().get( 0 );
		assertEquals( "OFF_MARKET_TRADE",  pendingTransfer.getType() );
		assertEquals( "UNS_DIS", pendingTransfer.getLowestLedger() );
		assertEquals( -150, pendingTransfer.getBalance().longValue() );
		assertNotNull( pendingTransfer.getDueDate() );
		assertEquals( -5911500, pendingTransfer.getValuation().longValue() );
		assertEquals( Currency.getInstance( "USD" ), pendingTransfer.getValuationCurrency() );
	}

	@Test
	public final void testReadExternalSpotPrice() {
		List<Price> p = (List<Price>) readTestFileAsModel( "sampleSilverEuroSpotPrice.xml", SpotPriceMessage.class );
	}

	@Test
	public final void testReadTickerDeals() {
		List<Deal> d = (List<Deal> ) readTestFileAsModel( "sampleTickerDeals.xml", Ticker.class );
		assertEquals( 77900, d.get( 0 ).getPricePerUnit().intValue() );
	}

	@Test
	@SneakyThrows( { IOException.class, java.util.NoSuchElementException.class } )
	public final void testReadMarketDepth() {
		@Cleanup InputStream stream = ReaderTests.class.getResource( "sampleMarketDepth.html" ).openStream();
		String document = new java.util.Scanner( stream ).useDelimiter("\\A").next();
		List<Price> prices = MarketDepth.parse( document );
		assertEquals( 220046, prices.get( 0 ).getQuantity() );
	}

	@SneakyThrows( { IOException.class, JAXBException.class } )
	private static Object readTestFileAsModel( String path, Class modelClass ) {

		@Cleanup InputStream stream = ReaderTests.class.getResource( path ).openStream();

		// Process
		JAXBContext context = JAXBContext.newInstance( modelClass );
		Unmarshaller unmarshaller = context.createUnmarshaller();
		MessageContainer message = ( (MessageContainer) unmarshaller.unmarshal( stream ) );

		return message.getContent();
	}

}
