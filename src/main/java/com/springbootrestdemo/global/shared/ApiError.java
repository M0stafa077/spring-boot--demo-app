package com.springbootrestdemo.global.shared;

import java.util.Map;

public record ApiError(
        int status,
        String message, Map<String,
        String> details) {

}
