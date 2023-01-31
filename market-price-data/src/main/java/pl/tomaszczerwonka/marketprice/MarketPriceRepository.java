package pl.tomaszczerwonka.marketprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceDescription = @Description("Collection of market prices"))
public interface MarketPriceRepository extends JpaRepository<MarketPrice, String>, JpaSpecificationExecutor<MarketPrice> {

    @Query("SELECT mp FROM MarketPrice mp JOIN FETCH mp.product p WHERE p.name = :productName AND mp.baseCurrency = :baseCurrency AND quoteCurrency = :quoteCurrency")
    Optional<MarketPrice> findByProductNameAndBaseCurrencyAndQuoteCurrency(@Param("productName") String productName, @Param("baseCurrency") String baseCurrency, @Param("quoteCurrency") String quoteCurrency);

}
