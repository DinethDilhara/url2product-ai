package com.dineth.url2product.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ProductExtractionRequest(

        @NotBlank(message = "URL is required")
        @URL(message = "Invalid URL")
        String url

) {}