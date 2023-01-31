package pl.tomaszczerwonka;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import pl.tomaszczerwonka.marketprice.MarketPrice;
import pl.tomaszczerwonka.product.Product;
import pl.tomaszczerwonka.product.ProductService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : Tomasz Czerwonka
 */
@SpringBootApplication
public class MarketPriceApplication implements RepositoryRestConfigurer {

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(MarketPriceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Product product = new Product("Facebook Inc.");
        MarketPrice marketPrice1 = new MarketPrice("EUR", "USD", new BigDecimal("1.1000"), new BigDecimal("1.2000"), LocalDateTime.now());
        marketPrice1.setProduct(product);
        MarketPrice marketPrice2 = new MarketPrice("EUR", "JPY", new BigDecimal("119.60"), new BigDecimal("119.90"), LocalDateTime.now());
        marketPrice2.setProduct(product);
        product.add(marketPrice1);
        product.add(marketPrice2);
        productService.addMarketPriceWithMargin(marketPrice1);
        productService.addMarketPriceWithMargin(marketPrice2);
    }
}
