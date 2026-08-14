package com.dineth.url2product.service;

import com.dineth.url2product.service.currency.CurrencyConverter;
import com.dineth.url2product.service.llm.ProductLlmExtractor;
import com.dineth.url2product.dto.ProductDetailsResponse;
import com.dineth.url2product.model.ExtractedPageContent;
import com.dineth.url2product.util.ExtractionStatusUtil;
import com.dineth.url2product.model.LlmProductDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductExtractionService {

    private final UrlValidationService urlValidationService;
    private final WebPageFetcher webPageFetcher;
    private final HtmlContentCleaner htmlContentCleaner;
    private final ProductLlmExtractor productLlmExtractor;
    private final CurrencyConverter currencyConverter;

    public ProductDetailsResponse extract(String url) {

        urlValidationService.validate(url);

        System.out.println("===============================================");
        System.out.println("Fetching webpage content for URL: " + url);
        System.out.println("===============================================");

        String html = webPageFetcher.fetch(url);

        System.out.println("===============================================");
        System.out.println("Extracted HTML : " + html);
        System.out.println("===============================================");

        ExtractedPageContent content = htmlContentCleaner.clean(html, url);

        System.out.println("===============================================");
        System.out.println("Extracted content : " + content);
        System.out.println("===============================================");

        LlmProductDetails llmProductDetails = productLlmExtractor.extract(content);

        System.out.println("===============================================");
        System.out.println("Extracted product details from LLM : " + llmProductDetails);
        System.out.println("===============================================");

        ProductDetailsResponse product = mapToProductDetails(llmProductDetails);
        product.setLink(url);

        return product;
    }

    // map to llmProductDetails to ProductDetails
    private ProductDetailsResponse mapToProductDetails(LlmProductDetails llmProductDetails) {

        ProductDetailsResponse product = new ProductDetailsResponse();

        product.setTitle(llmProductDetails.title());
        product.setDescription(llmProductDetails.description());
        product.setImages(llmProductDetails.images());

        BigDecimal price = llmProductDetails.price();
        String currency = llmProductDetails.currency();

        if (price != null && currency != null && !currency.isBlank()) {

            if (!"USD".equalsIgnoreCase(currency)) {

                price = currencyConverter.convert(
                        price,
                        currency,
                        "USD"
                );

                currency = "USD";
            }
        }

        product.setPrice(price);
        product.setCurrency(currency);

        product.setStatus(
                ExtractionStatusUtil.determineStatus(llmProductDetails)
        );

        return product;
    }
}