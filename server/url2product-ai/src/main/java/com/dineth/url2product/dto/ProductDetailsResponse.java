package com.dineth.url2product.dto;

import com.dineth.url2product.model.ExtractionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse {

    private String link;
    private String title;
    private String description;
    private String currency;
    private BigDecimal price;
    private List<String> images;
    private ExtractionStatus status;
}