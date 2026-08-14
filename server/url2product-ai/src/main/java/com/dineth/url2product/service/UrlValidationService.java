package com.dineth.url2product.service;

import com.dineth.url2product.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlValidationService  {

    public void validate(String url) {

        if (url == null || url.isBlank()) {
            throw new BadRequestException("URL is required");
        }

        try {
            URI uri = URI.create(url);

            String scheme = uri.getScheme();

            if (scheme == null ||
                    (!scheme.equalsIgnoreCase("http")
                            && !scheme.equalsIgnoreCase("https"))) {

                throw new BadRequestException("Only HTTP and HTTPS URLs are supported");
            }

            if (uri.getHost() == null || uri.getHost().isBlank()) {
                throw new BadRequestException("Invalid URL");
            }

        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid URL");
        }
    }
}