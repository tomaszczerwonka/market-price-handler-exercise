package pl.tomaszczerwonka.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomaszczerwonka.function.CalculateMargin;
import pl.tomaszczerwonka.marketprice.MarketPrice;
import pl.tomaszczerwonka.marketprice.MarketPriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final MarketPriceRepository marketPriceRepository;

    private final CalculateMargin calculateMargin = new CalculateMargin();

    @Autowired
    public ProductService(ProductRepository productRepository, MarketPriceRepository marketPriceRepository) {
        this.productRepository = productRepository;
        this.marketPriceRepository = marketPriceRepository;
    }

    public Product addMarketPriceWithMargin(String productName, String baseCurrency, String quoteCurrency, BigDecimal bidPrice, BigDecimal askPrice, LocalDateTime updateDate) {
        Product product = productRepository.findByName(productName);
        MarketPrice marketPrice = new MarketPrice(baseCurrency, quoteCurrency, bidPrice, askPrice, updateDate);
        product.add(marketPrice);
        return productRepository.save(product);
    }

    public Product addMarketPriceWithMargin(MarketPrice newMarketPrice) {
        addMarginToAskPrice(newMarketPrice);
        subtractMarginFromBidPrice(newMarketPrice);

        Optional<MarketPrice> marketPriceOptional = marketPriceRepository.findByProductNameAndBaseCurrencyAndQuoteCurrency(newMarketPrice.getProduct().getName(), newMarketPrice.getBaseCurrency(), newMarketPrice.getQuoteCurrency());
        if (marketPriceOptional.isPresent()) {
            MarketPrice marketPrice = marketPriceOptional.get();
            newMarketPrice.setId(marketPrice.getId());
            newMarketPrice.setProduct(marketPrice.getProduct());
            newMarketPrice = marketPriceRepository.save(newMarketPrice);
        } else {
            setProductForMarginPriceIfExists(newMarketPrice);
            newMarketPrice.getProduct().add(newMarketPrice);
            newMarketPrice = marketPriceRepository.save(newMarketPrice);
            productRepository.save(newMarketPrice.getProduct());
        }


        return newMarketPrice.getProduct();
    }

    private void setProductForMarginPriceIfExists(MarketPrice newMarketPrice) {
        Product product = productRepository.findByName(newMarketPrice.getProduct().getName());
        if (product != null) {
            newMarketPrice.setProduct(product);
        }
    }

    private void subtractMarginFromBidPrice(MarketPrice marketPrice) {
        marketPrice.setBidPrice(marketPrice.getBidPrice().subtract(calculateMargin.apply(marketPrice.getBidPrice())));
    }

    private void addMarginToAskPrice(MarketPrice marketPrice) {
        marketPrice.setAskPrice(marketPrice.getAskPrice().add(calculateMargin.apply(marketPrice.getAskPrice())));
    }
}
