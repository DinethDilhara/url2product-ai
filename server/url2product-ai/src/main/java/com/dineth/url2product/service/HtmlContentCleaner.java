package com.dineth.url2product.service;

import com.dineth.url2product.model.ExtractedPageContent;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;

import java.util.*;

@Slf4j
@Service
public class  HtmlContentCleaner {

    private static final int MAX_TEXT_LENGTH = 50_000;

    public ExtractedPageContent clean(
            String html,
            String url
    ) {

        log.debug("Cleaning webpage | url={}", url);

        Document document = Jsoup.parse(html, url);

        removeUnnecessaryElements(document);

        String title = extractTitle(document);
        String text = extractText(document);

        String price = extractPrice(document);
        String currency = extractCurrency(document);

        List<String> images = extractImages(document);
        Map<String, String> metadata = extractMetadata(document);

        return ExtractedPageContent.builder()
                .title(title)
                .text(text)
                .price(price)
                .currency(currency)
                .images(images)
                .metadata(metadata)
                .build();
    }

    private void removeUnnecessaryElements( Document document) {
        document.select(
                "script, style, noscript, iframe, " +
                        "svg, canvas, nav, footer, header, " +
                        "form, aside, .advertisement, .ads, .cookie"
        ).remove();
    }

    private String extractTitle(Document document) {

        Element titleElement = document.selectFirst("title");

        if (titleElement != null && !titleElement.text().isBlank()) {
            return titleElement.text().trim();
        }

        Element h1 = document.selectFirst("h1");

        return h1 != null ? h1.text().trim() : null;
    }

    private String extractText(Document document) {

        String text = document
                .body()
                .wholeText()
                .replaceAll("\\s+", " ")
                .trim();

        if (text.length() > MAX_TEXT_LENGTH) {
            return text.substring(0, MAX_TEXT_LENGTH);
        }

        return text;
    }

    private List<String> extractImages(Document document) {

        Set<String> images = new LinkedHashSet<>();

        Elements imageElements = document.select("img[src]");

        for (Element image : imageElements) {

            String src = image.absUrl("src");

            if (!src.isBlank()) {
                images.add(src);
            }
        }

        return new ArrayList<>(images);
    }

    private Map<String, String> extractMetadata(Document document) {

        Map<String, String> metadata = new LinkedHashMap<>();

        Elements metaTags = document.select("meta");

        for (Element meta : metaTags) {

            String key = firstNonBlank(
                    meta.attr("property"),
                    meta.attr("name")
            );

            String value = meta.attr("content");

            if (!key.isBlank() && !value.isBlank()) {
                metadata.put(key, value);
            }
        }

        return metadata;
    }

    private String firstNonBlank(String first, String second) {

        if (first != null && !first.isBlank()) {
            return first;
        }

        return second;
    }

    private String extractPrice(Document document) {

        Element price = document.selectFirst(
                "[itemprop='price']," +
                        "meta[property='product:price:amount']," +
                        "meta[property='og:price:amount']," +

                        // Common ecommerce attributes
                        "[data-price]," +
                        "[data-testid*='price']," +
                        "[data-test*='price']," +

                        // AliExpress
                        "[class*='price-default--current']," +

                        // Daraz
                        "[class*='pdp-price_type_normal']," +

                        // Generic current price
                        "[class*='current-price']," +
                        "[class*='currentPrice']," +
                        "[class*='sale-price']," +
                        "[class*='salePrice']," +
                        "[class*='selling-price']," +
                        "[class*='sellingPrice']"
        );

        if (price == null) {
            return null;
        }

        String value = firstNonBlank(
                price.attr("content"),
                price.attr("data-price")
        );

        if (value == null || value.isBlank()) {
            value = price.text();
        }

        return value;
    }

    private String extractCurrency(Document document) {

        Element currency = document.selectFirst(
                "[itemprop='priceCurrency']," +
                        "meta[property='product:price:currency']," +
                        "meta[property='og:price:currency']," +
                        "[data-currency]," +
                        "[data-price-currency]," +
                        "[data-testid='currency']," +
                        "[data-testid*='currency']," +
                        "[data-test='currency']," +
                        "[data-test*='currency']"
        );

        if (currency == null) {
            return null;
        }

        String value = firstNonBlank(
                currency.attr("content"),
                currency.attr("data-currency")
        );

        if (value == null || value.isBlank()) {
            value = firstNonBlank(
                    currency.attr("data-price-currency"),
                    currency.text()
            );
        }

        return value;
    }
}

