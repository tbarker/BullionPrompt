package com.thomasbarker.bullionprompt.xmlapi.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Currency;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.Cleanup;
import lombok.SneakyThrows;

import org.junit.Assert;
import org.junit.Test;

import com.thomasbarker.bullionprompt.model.Order;
import com.thomasbarker.bullionprompt.model.Position;
import com.thomasbarker.bullionprompt.model.Price;
import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.OrderStatus;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.documents.MessageContainer;
import com.thomasbarker.bullionprompt.xml.documents.OrdersMessage;
import com.thomasbarker.bullionprompt.xml.documents.PositionsMessage;
import com.thomasbarker.bullionprompt.xml.documents.PricesMessage;

@SuppressWarnings("unchecked")
public class ReaderTests {

	@Test
    public final void testReadPrices() {
        Price p = ( (List<Price>) readTestFileAsModel( "samplePrices.xml", PricesMessage.class ) ).get(1);

        Assert.assertEquals(  Security.AUXLN, p.getSecurity() );
        Assert.assertEquals(  200, p.getQuantity() );
        Assert.assertEquals(  ActionIndicator.BUY, p.getActionIndicator() );

        p = ( (List<Price>) readTestFileAsModel( "samplePrices.xml", PricesMessage.class ) ).get(3);
        Assert.assertEquals(  ActionIndicator.SELL, p.getActionIndicator() );
    }

	@Test
    @SuppressWarnings("deprecation")
    public final void testReadOrders() {
        Order o = ( (List<Order>) readTestFileAsModel( "sampleOrders.xml", OrdersMessage.class ) ).get(1);

        Assert.assertEquals( 1061, o.getId().longValue() );
        Assert.assertEquals( "050520115557474", o.getClientTransRef() );
        Assert.assertEquals( ActionIndicator.BUY, o.getActionIndicator() );
        Assert.assertEquals( Security.AUXNY, o.getSecurity() );
        Assert.assertEquals( Currency.getInstance("USD"), o.getConsiderationCurrency() );
        Assert.assertEquals( 2680, o.getTotalConsideration().longValue() );
        Assert.assertEquals( 0, o.getTotalCommission().longValue() );
        Assert.assertEquals( 1340000, o.getLimit().longValue() );
        Assert.assertEquals( OrderType.TIL_CANCEL, o.getType() );
        Assert.assertEquals( 33, o.getTime().getSeconds() );
        Assert.assertNull(o.getGoodUntil() );
        Assert.assertEquals( 2005, o.getLastModified().getYear() + 1900);
        Assert.assertEquals( o.getStatus(), OrderStatus.DONE);
        Assert.assertEquals( 2, o.getQuantity().longValue() );
        Assert.assertEquals( 2, o.getQuantityMatched().longValue() );
    }

    @Test
    public final void testReadBalances() {
        Position p = ( (List<Position>) readTestFileAsModel( "sampleBalance.xml", PositionsMessage.class ) ).get(1);

        Assert.assertEquals( Security.AUXNY, p.getSecurity() );
        Assert.assertEquals( 5000, p.getAvailable().longValue() );
        Assert.assertEquals( 5000, p.getTotal().longValue() );
        Assert.assertEquals( "GOLD", p.getNarrative() );
        Assert.assertEquals( 6705000, p.getValuation().longValue() );
        Assert.assertEquals( Currency.getInstance("USD"), p.getValuationCurrency() );
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
