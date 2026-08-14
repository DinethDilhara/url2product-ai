package com.dineth.url2product.service.llm;

import com.dineth.url2product.model.ExtractedPageContent;
import com.dineth.url2product.model.LlmProductDetails;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiProductLlmExtractor implements ProductLlmExtractor {

    private final ChatClient chatClient;

    @Override
    public LlmProductDetails extract(ExtractedPageContent content) {

        log.debug("Starting LLM product extraction");

        return chatClient
                .prompt()
                .system(ProductExtractionPrompt.SYSTEM_PROMPT)
                .user(ProductExtractionPrompt.buildUserPrompt(content))
                .call()
                .entity(LlmProductDetails.class);
    }

}
