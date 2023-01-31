package pl.tomaszczerwonka.function;

import pl.tomaszczerwonka.marketprice.MarketPrice;
import pl.tomaszczerwonka.product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Function;

public class StringToMarketPrice implements Function<String, MarketPrice> {

    private static final String COMMA_DELIMITER = ",";
    private static final String FORWARD_SLASH_DELIMITER = "/";
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss:SSS";

    @Override
    public MarketPrice apply(String csvLine) {
        try(Scanner scanner = new Scanner(csvLine)) {
            scanner.useDelimiter(COMMA_DELIMITER);

            MarketPrice marketPrice = new MarketPrice();
            Product product = new Product();
            marketPrice.setProduct(product);
            if (scanner.hasNextInt()) {
                product.setName(scanner.next());
            }
            if (scanner.hasNext()) {
                String[] currencies = scanner.next().trim().split(FORWARD_SLASH_DELIMITER);
                marketPrice.setBaseCurrency(currencies[0]);
                marketPrice.setQuoteCurrency(currencies[1]);
            }
            if (scanner.hasNext()) {
                marketPrice.setBidPrice(new BigDecimal(scanner.next().trim()));
            }
            if (scanner.hasNext()) {
                marketPrice.setAskPrice(new BigDecimal(scanner.next().trim()));
            }
            if (scanner.hasNext()) {
                marketPrice.setUpdateDate(LocalDateTime.parse(scanner.next().trim(), DateTimeFormatter.ofPattern(DATE_PATTERN)));
            }
            return marketPrice;
        }
    }
}
