package pl.tomaszczerwonka.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import pl.tomaszczerwonka.marketprice.MarketPrice;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Tomasz Czerwonka
 */
@Entity
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MarketPrice> marketPrices = new HashSet<>();

    public Product() {
        name = null;
    }

    public Product add(MarketPrice marketPrice) {
        this.marketPrices.add(marketPrice);
        return this;
    }

    public Product(String name) {
        this.name = name;
    }
}
