package pl.tomaszczerwonka.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.tomaszczerwonka.marketprice.MarketPrice;
import pl.tomaszczerwonka.marketprice.MarketPriceRepository;
import pl.tomaszczerwonka.product.Product;
import pl.tomaszczerwonka.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MarketPriceQueueListenerTest {

    @Autowired
    private MarketPriceQueueListener marketPriceQueueListener;
    @Autowired
    private MarketPriceRepository marketPriceRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldAddSingleMarketPriceCorrectly() {
        //given
        String csvMessage = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";

        //when
        marketPriceQueueListener.onMessage(csvMessage);

        //then
        Product product = productRepository.findByName("106");
        assertNotNull(product);
        Set<MarketPrice> marketPrices = product.getMarketPrices();
        assertNotNull(marketPrices);
        assertFalse(marketPrices.isEmpty());
        assertEquals(1, marketPrices.size());
    }

    @Test
    void shouldAddManyMarketPricesCorrectly() {
        //given
        String csvMessage1 = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        String csvMessage2 = "106, EUR/PLN, 1.2000,1.3000,02-06-2020 12:02:01:001";
        String csvMessage3 = "106, EUR/JPY, 1.4000,1.5000,03-06-2020 12:03:01:001";

        //when
        marketPriceQueueListener.onMessage(csvMessage1);
        marketPriceQueueListener.onMessage(csvMessage2);
        marketPriceQueueListener.onMessage(csvMessage3);

        //then
        Product product = productRepository.findByName("106");
        assertNotNull(product);
        Set<MarketPrice> marketPrices = product.getMarketPrices();
        assertNotNull(marketPrices);
        assertFalse(marketPrices.isEmpty());
        assertEquals(3, marketPrices.size());
    }

    @Test
    void shouldUpdateMarketPriceCorrectlyForTheSameProduct() {
        //given
        String csvMessage1 = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        String csvMessage2 = "106, EUR/USD, 1.2000,1.3000,02-06-2020 12:02:01:001";

        //when
        marketPriceQueueListener.onMessage(csvMessage1);
        marketPriceQueueListener.onMessage(csvMessage2);

        //then
        Product product = productRepository.findByName("106");
        assertNotNull(product);
        Set<MarketPrice> marketPrices = product.getMarketPrices();
        assertNotNull(marketPrices);
        assertFalse(marketPrices.isEmpty());
        assertEquals(1, marketPrices.size());
        MarketPrice marketPrice = marketPrices.iterator().next();
        assertEquals("EUR", marketPrice.getBaseCurrency());
        assertEquals("USD", marketPrice.getQuoteCurrency());
        assertEquals("1.1988", marketPrice.getBidPrice().toString());
        assertEquals("1.3013", marketPrice.getAskPrice().toString());
        assertEquals(LocalDateTime.of(2020, 6, 2, 12, 2, 1, 1000000), marketPrice.getUpdateDate());
    }


}