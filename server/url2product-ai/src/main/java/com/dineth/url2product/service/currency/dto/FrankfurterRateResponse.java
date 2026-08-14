package com.dineth.url2product.service.currency.dto;

import java.math.BigDecimal;

public record FrankfurterRateResponse(
        String date,
        String base,
        String quote,
        BigDecimal rate
) {
}