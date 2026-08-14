package com.dineth.url2product.service.currency;

import com.dineth.url2product.service.currency.dto.FrankfurterRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrankfurterCurrencyConverter implements CurrencyConverter {

    private static final String BASE_URL = "https://api.frankfurter.dev";

    private final RestClient.Builder restClientBuilder;

    @Override
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (fromCurrency == null || fromCurrency.isBlank()) {
            throw new IllegalArgumentException("Source currency cannot be null");
        }

        if (toCurrency == null || toCurrency.isBlank()) {
            throw new IllegalArgumentException("Target currency cannot be null");
        }

        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }

        log.debug(
                "Converting currency | amount={} | from={} | to={}",
                amount,
                fromCurrency,
                toCurrency
        );

        FrankfurterRateResponse response = restClientBuilder
                .baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/v2/rate/{from}/{to}",
                        fromCurrency.toUpperCase(),
                        toCurrency.toUpperCase())
                .retrieve()
                .body(FrankfurterRateResponse.class);

        if (response == null || response.rate() == null) {
            throw new IllegalStateException(
                    "Unable to retrieve exchange rate for "
                            + fromCurrency + "/" + toCurrency
            );
        }

        return amount.multiply(response.rate());
    }
}