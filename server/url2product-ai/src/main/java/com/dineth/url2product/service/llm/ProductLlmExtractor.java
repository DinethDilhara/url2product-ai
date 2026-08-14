package com.dineth.url2product.service.llm;

import com.dineth.url2product.model.ExtractedPageContent;
import com.dineth.url2product.model.LlmProductDetails;

public interface ProductLlmExtractor {

    LlmProductDetails extract(ExtractedPageContent content);

}