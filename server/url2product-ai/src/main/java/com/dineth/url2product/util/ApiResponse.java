package com.dineth.url2product.util;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private ZonedDateTime timestamp;

}


