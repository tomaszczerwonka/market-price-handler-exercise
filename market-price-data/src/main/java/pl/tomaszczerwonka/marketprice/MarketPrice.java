package pl.tomaszczerwonka.marketprice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.tomaszczerwonka.product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@ToString
@EqualsAndHashCode(of = {"id", "baseCurrency", "quoteCurrency", "product"})
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String baseCurrency;
    private String quoteCurrency;
    @Column(precision = 10, scale = 4)
    private BigDecimal bidPrice;
    @Column(precision = 10, scale = 4)
    private BigDecimal askPrice;

    private LocalDateTime updateDate;

    @ManyToOne
    private Product product;

    public MarketPrice() {
        baseCurrency = null;
        quoteCurrency = null;
        bidPrice = null;
        askPrice = null;
        updateDate = LocalDateTime.now();
    }

    public MarketPrice(String baseCurrency, String quoteCurrency, BigDecimal bidPrice, BigDecimal askPrice, LocalDateTime updateDate) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.updateDate = updateDate;
    }
}
