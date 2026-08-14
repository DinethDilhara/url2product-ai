package com.dineth.url2product.util;

import com.dineth.url2product.model.LlmProductDetails;
import com.dineth.url2product.model.ExtractionStatus;

public final class ExtractionStatusUtil {

    private ExtractionStatusUtil() {
    }

    public static ExtractionStatus determineStatus(LlmProductDetails details) {

        if (details == null) {
            return ExtractionStatus.FAILED;
        }

        int populatedFields = 0;

        // Count the total number of fields in LlmProductDetails
        int totalFields = 5;

        if (isNotBlank(details.title())) {
            populatedFields++;
        }

        if (isNotBlank(details.description())) {
            populatedFields++;
        }

        if (details.price() != null) {
            populatedFields++;
        }

        if (isNotBlank(details.currency())) {
            populatedFields++;
        }

        if (details.images() != null && !details.images().isEmpty()) {
            populatedFields++;
        }

        if (populatedFields == 0) {
            return ExtractionStatus.FAILED;
        }

        if (populatedFields == totalFields) {
            return ExtractionStatus.SUCCESS;
        }

        return ExtractionStatus.PARTIAL;
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

}