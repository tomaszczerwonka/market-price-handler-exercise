package pl.tomaszczerwonka.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomaszczerwonka.function.StringToMarketPrice;
import pl.tomaszczerwonka.product.ProductService;


@Component
public class MarketPriceQueueListener implements MessageListener {

    private final ProductService productService;

    private final StringToMarketPrice stringToMarketPrice = new StringToMarketPrice();


    @Autowired
    public MarketPriceQueueListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void onMessage(String message) {
        validateMessage(message);

        productService.addMarketPriceWithMargin(stringToMarketPrice.apply(message));
    }

    private void validateMessage(String message) {
        //todo: implement more corner cases
    }
}
