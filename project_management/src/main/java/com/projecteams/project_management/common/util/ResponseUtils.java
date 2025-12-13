package com.projecteams.project_management.common.util;

import org.springframework.http.HttpStatus;

import com.projecteams.project_management.common.dto.response.ErrorResponse;
import com.projecteams.project_management.common.dto.response.SuccessResponse;

public class ResponseUtils {

    public static final String FAILED = " FAILED: ";

    public static <T> SuccessResponse<T> buildSuccessResponse(HttpStatus status, String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(message);

        return response;
    }

    public static <T> SuccessResponse<T> buildSuccessResponse(HttpStatus status, String message, T data) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    public static <T> ErrorResponse<T> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse<T> response = new ErrorResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(FAILED + message);

        return response;
    }

    public static <T> ErrorResponse<T> buildErrorResponse(HttpStatus status, String message, T data) {
        ErrorResponse<T> response = new ErrorResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(FAILED + message);
        response.setData(data);

        return response;
    }
}
