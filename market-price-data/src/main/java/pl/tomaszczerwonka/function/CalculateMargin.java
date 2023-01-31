package pl.tomaszczerwonka.function;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public class CalculateMargin implements UnaryOperator<BigDecimal> {

    private static final BigDecimal MARGIN = BigDecimal.valueOf(0.001);

    @Override
    public BigDecimal apply(BigDecimal price) {
        return price.multiply(MARGIN);
    }
}
