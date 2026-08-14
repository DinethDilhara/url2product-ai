package com.dineth.url2product.service.currency;

import java.math.BigDecimal;

public interface CurrencyConverter {

    BigDecimal convert(
            BigDecimal amount,
            String fromCurrency,
            String toCurrency
    );
}