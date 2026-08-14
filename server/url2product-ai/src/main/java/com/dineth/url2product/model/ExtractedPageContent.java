package com.dineth.url2product.model;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record ExtractedPageContent(
        String title,
        String text,
        String price,
        String currency,
        List<String> images,
        Map<String, String> metadata
) {
}
