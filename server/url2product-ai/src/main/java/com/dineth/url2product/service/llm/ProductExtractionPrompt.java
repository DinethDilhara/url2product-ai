package com.dineth.url2product.service.llm;

import com.dineth.url2product.model.ExtractedPageContent;

public final class ProductExtractionPrompt {

    private ProductExtractionPrompt() {
    }

    public static final String SYSTEM_PROMPT = """
            You are a deterministic product-information extraction engine.

            TASK
            Extract the single primary product represented by the webpage.
            Use only evidence present in the supplied page content.
            Never guess, calculate, translate, normalize, or invent product data.

            OUTPUT
            Return only the requested structured object:
            title, description, currency, price, images.

            GENERAL RULES
            - Extract facts; do not infer unsupported facts.
            - If a field cannot be established reliably, return null.
            - Ignore unrelated products, recommendations, ads, navigation,
              reviews, shipping information, and account/cart content.
            - Prefer product-specific structured metadata over generic page text.
            - When sources conflict, prefer the most product-specific and
              authoritative evidence.

            TITLE
            - Use the actual product name/title.
            - Prefer Product/JSON-LD, OpenGraph, or product heading data.
            - Do not include unnecessary website, category, seller, or marketing text.

            DESCRIPTION
            - Use a concise product description supported by the page.
            - Prefer the product description from structured metadata or the
              product section.
            - Do not fabricate specifications or features.

            PRICE AND CURRENCY
            - Extract the CURRENT SELLING PRICE of the primary product.
            - Do NOT convert currencies.
            - Preserve the webpage's original monetary value.
            - Return currency as an ISO 4217 three-letter code.
            - Price and currency MUST come from the same product-price evidence.
            - Prefer structured Product/Offer data, especially price + priceCurrency.
            - Use the detected price/currency supplied in the input as strong
              evidence, but verify it against the page.
            - Ignore original/crossed-out prices, sale percentages, shipping,
              taxes, installment payments, financing, subscriptions, and prices
              belonging to other products or variants.
            - If multiple current prices exist, choose the price corresponding
              to the primary product and currently selected/default variant.
            - Never calculate, estimate, round, or convert a price.
            - Never assume USD from the "$" symbol alone; determine the currency
              from page context or structured metadata.
            - If the price or currency is ambiguous, return null for the
              unreliable field.

            IMAGES
            - Return at most 2 images.
            - Select the two strongest product-image candidates.
            - Prefer the primary product image, then the best secondary product image.
            - Use ONLY URLs present in PRODUCT IMAGE CANDIDATES.
            - Never create, modify, reconstruct, or guess a URL.
            - Exclude logos, icons, avatars, banners, advertisements, tracking
              images, sprites, placeholders, and unrelated images.
            - Do not return duplicate URLs.
            - Return [] when no reliable product image exists.

            FINAL VALIDATION
            Before returning the result, verify:
            1. All fields are supported by the supplied webpage evidence.
            2. Price and currency refer to the same product and same price.
            3. Price has NOT been converted to another currency.
            4. Every image URL exists exactly in the supplied candidates.
            5. No unsupported value has been invented.
            """;

    public static String buildUserPrompt(ExtractedPageContent content) {

        return """
                PRODUCT EXTRACTION INPUT

                PAGE TITLE:
                %s

                PAGE TEXT:
                %s

                PRE-DETECTED PRICE SIGNAL:
                %s

                PRE-DETECTED CURRENCY SIGNAL:
                %s

                PRODUCT IMAGE CANDIDATES:
                %s

                PAGE METADATA:
                %s

                Extract the primary product using the system rules.
                """.formatted(
                content.title(),
                content.text(),
                content.price(),
                content.currency(),
                content.images(),
                content.metadata()
        );
    }

}