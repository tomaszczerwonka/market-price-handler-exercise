package pl.tomaszczerwonka.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.tomaszczerwonka.marketprice.MarketPriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    MarketPriceRepository marketPriceRepository;

    @Test
    void shouldAddMarketPriceCorrectly() {
        //given
        String productName = "Facebook Inc.";

        //when

        Product product = new ProductService(productRepository, marketPriceRepository).addMarketPriceWithMargin(productName, "TST", "TST", new BigDecimal(1), new BigDecimal(1), LocalDateTime.now());

        //then
        assertNotNull(product);
        assertEquals(productName, product.getName());
        assertNotNull(product.getMarketPrices());
        assertFalse(product.getMarketPrices().isEmpty());
        assertEquals(3, product.getMarketPrices().size());
    }

}