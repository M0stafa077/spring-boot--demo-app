package com.springbootrestdemo.global.shared;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <Void> ApiResponse<Void> emptySuccess() {
        return new ApiResponse<>(true, null, null);
    }

    public static ApiResponse<Void> error(
            int status,
            String message,
            Map<String, String> details
    ) {
        return new ApiResponse<>(false, null, new ApiError(status, message, details));
    }
}
