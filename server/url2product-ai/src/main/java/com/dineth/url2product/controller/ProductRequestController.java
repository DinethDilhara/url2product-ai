package com.dineth.url2product.controller;

import com.dineth.url2product.service.ProductExtractionService;
import com.dineth.url2product.dto.ProductExtractionRequest;
import com.dineth.url2product.dto.ProductDetailsResponse;
import com.dineth.url2product.util.ApiResponseBuilder;
import com.dineth.url2product.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-request")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductRequestController {

    private final ProductExtractionService productExtractionService;

    @PostMapping("/extract")
    public ApiResponse<ProductDetailsResponse> extract(
            @Valid @RequestBody ProductExtractionRequest request) {

        ProductDetailsResponse product = productExtractionService.extract(request.url());

        return ApiResponseBuilder.success(
                "Product extracted successfully",
                product
        );
    }

}
