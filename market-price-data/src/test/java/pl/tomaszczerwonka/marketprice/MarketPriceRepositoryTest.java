package pl.tomaszczerwonka.marketprice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class MarketPriceRepositoryTest {

    @Autowired
    MarketPriceRepository marketPriceRepository;

    @Test
    void shouldReturnLatestPricesForProductCorrectly() {
        //given
        String productName = "Facebook Inc.";
        String baseCurrency = "EUR";
        String quoteCurrency = "USD";

        //when
        Optional<MarketPrice> marketPriceOptional = marketPriceRepository.findByProductNameAndBaseCurrencyAndQuoteCurrency(productName, baseCurrency, quoteCurrency);

        //then
        assertNotNull(marketPriceOptional);
        assertFalse(marketPriceOptional.isEmpty());
    }
}