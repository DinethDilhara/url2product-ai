package com.dineth.url2product.model;

import java.math.BigDecimal;
import java.util.List;

public record LlmProductDetails(
        String title,
        String description,
        String currency,
        BigDecimal price,
        List<String> images
) {}
