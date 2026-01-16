package com.thomasbarker.bullionprompt.xmlapi.test;

import com.thomasbarker.bullionprompt.model.*;
import com.thomasbarker.bullionprompt.model.enums.*;
import com.thomasbarker.bullionprompt.xml.documents.*;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class ReaderTests {

	@Test
	public final void testReadPrices() throws IOException, JAXBException {
		Price p = ( (List<Price>) readTestFileAsModel( "samplePrices.xml", PricesMessage.class ) ).get(1);

		assertEquals(  Security.AUXLN, p.security );
		assertEquals(  200, p.quantity );
		assertEquals(  ActionIndicator.BUY, p.actionIndicator );

		p = ( (List<Price>) readTestFileAsModel( "samplePrices.xml", PricesMessage.class ) ).get(3);
		assertEquals(  ActionIndicator.SELL, p.actionIndicator );
	}

	@Test
	@SuppressWarnings("deprecation")
	public final void testReadOrders() throws IOException, JAXBException {
		Order o = ( (List<Order>) readTestFileAsModel( "sampleOrders.xml", OrdersMessage.class ) ).get(1);

		assertEquals( 139721159, o.id.longValue() );
		assertEquals( "120209000332633", o.clientTransRef );
		assertEquals( ActionIndicator.BUY, o.actionIndicator );
		assertEquals( Security.AGXLN, o.security );
		assertEquals( Currency.getInstance("USD"), o.considerationCurrency );
		assertEquals( 110, o.totalConsideration.longValue() );
		assertEquals( 1, o.totalCommission.longValue() );
		assertEquals( 110400, o.limit.longValue() );
		assertEquals( OrderType.TIL_CANCEL, o.type );
		assertEquals( 51, o.time.getSeconds() );
		assertNull( o.goodUntil );
		assertEquals( 2012, o.lastModified.getYear() + 1900);
		assertEquals( o.status, OrderStatus.DONE);
		assertEquals( 1, o.quantity.longValue(), 0 );
		assertEquals( 1, o.quantityMatched.longValue(), 0 );
	}

	@Test
	@SuppressWarnings("deprecation")
	public final void testReadSingleOrder() throws IOException, JAXBException {
		Order o = (Order) readTestFileAsModel( "sampleOrder.xml", SingleOrderMessage.class );
		assertEquals( OrderStatus.TO_BE_SETTLED, o.status );
		assertEquals( TradeType.CLIENT_ORDER, o.tradeType );
		assertEquals( Security.AGXTR, o.security );
		assertEquals( Boolean.FALSE, o.cancellable );
	}

	@Test
	public final void testReadBalances() throws IOException, JAXBException {
		Position p = ( (List<Position>) readTestFileAsModel( "sampleBalance.xml", PositionsMessage.class ) ).get(1);

		assertEquals( Security.AUXNY, p.security );
		assertEquals( 5000, p.available.longValue() );
		assertEquals( 5000, p.total.longValue() );
		assertEquals( "GOLD", p.narrative );
		assertEquals( 6705000, p.valuation.longValue() );
		assertEquals( Currency.getInstance("USD"), p.valuationCurrency );
	}

	@Test
	@SuppressWarnings("deprecation")
	public final void testReadCompleteBalances() throws JAXBException, IOException {

		try (InputStream stream = ReaderTests.class.getResource( "sampleCompleteBalance.xml" ).openStream()) {
			// Process
			JAXBContext context = JAXBContext.newInstance( PendingSettlementMessage.class );
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MessageContainer message = ( (MessageContainer) unmarshaller.unmarshal( stream ) );

			assertNotNull( message );
			assertTrue( ( (Collection) message.getContent() ).size() > 0 );
			assertNotNull( message.getContent() );

			PendingSettlement pendingSettlement = ( (List<PendingSettlement>) message.getContent() ).get( 0 );
			assertEquals( Security.AUXZU, pendingSettlement.security );
			assertEquals( 2229, pendingSettlement.total.longValue() );
			assertEquals( "GOLD", pendingSettlement.narrative );
			assertEquals( 8784489, pendingSettlement.valuation.longValue() );
			assertEquals( Currency.getInstance("USD"), pendingSettlement.valuationCurrency );

			PendingTransfer pendingTransfer = pendingSettlement.transfers.get( 0 );
			assertEquals( "OFF_MARKET_TRADE",  pendingTransfer.type );
			assertEquals( "UNS_DIS", pendingTransfer.lowestLedger );
			assertEquals( -150, pendingTransfer.balance.longValue() );
			assertNotNull( pendingTransfer.dueDate );
			assertEquals( -5911500, pendingTransfer.valuation.longValue() );
			assertEquals( Currency.getInstance( "USD" ), pendingTransfer.valuationCurrency );
		}
	}

	@Test
	public final void testReadExternalSpotPrice() throws IOException, JAXBException {
		List<Price> p = (List<Price>) readTestFileAsModel( "sampleSilverEuroSpotPrice.xml", SpotPriceMessage.class );
	}

	@Test
	public final void testReadTickerDeals() throws IOException, JAXBException {
		List<Deal> d = (List<Deal> ) readTestFileAsModel( "sampleTickerDeals.xml", Ticker.class );
		assertEquals( 77900, d.get( 0 ).pricePerUnit.intValue() );
	}

	@Test
	public final void testReadMarketDepth() throws IOException {
		try (InputStream stream = ReaderTests.class.getResource( "sampleMarketDepth.html" ).openStream()) {
			String document = new java.util.Scanner( stream ).useDelimiter("\\A").next();
			List<Price> prices = MarketDepth.parse( document );
			assertEquals( 220046, prices.get( 0 ).quantity );
		}
	}

	private static Object readTestFileAsModel( String path, Class modelClass ) throws IOException, JAXBException {

		try (InputStream stream = ReaderTests.class.getResource( path ).openStream()) {
			// Process
			JAXBContext context = JAXBContext.newInstance( modelClass );
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MessageContainer message = ( (MessageContainer) unmarshaller.unmarshal( stream ) );

			return message.getContent();
		}
	}

}
