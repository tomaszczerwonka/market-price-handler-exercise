package pl.tomaszczerwonka.function;

import org.junit.jupiter.api.Test;
import pl.tomaszczerwonka.marketprice.MarketPrice;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StringToMarketPriceTest {

    private final StringToMarketPrice testee = new StringToMarketPrice();

    @Test
    void shouldParseCsvToMarketPriceCorrectly() {
        //given
        String csvLine = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";

        //when
        MarketPrice marketPrice = testee.apply(csvLine);

        //then
        assertNotNull(marketPrice);
        assertNotNull(marketPrice.getBaseCurrency());
        assertEquals("EUR", marketPrice.getBaseCurrency());
        assertNotNull(marketPrice.getQuoteCurrency());
        assertEquals("USD", marketPrice.getQuoteCurrency());
        assertNotNull(marketPrice.getBidPrice());
        assertEquals("1.1000", marketPrice.getBidPrice().toString());
        assertNotNull(marketPrice.getAskPrice());
        assertEquals("1.2000", marketPrice.getAskPrice().toString());
        assertNotNull(marketPrice.getProduct());
        assertEquals("106", marketPrice.getProduct().getName());
        assertNotNull(marketPrice.getUpdateDate());
        assertEquals(LocalDateTime.of(2020, 6, 1, 12, 1, 1, 1000000), marketPrice.getUpdateDate());
    }

}