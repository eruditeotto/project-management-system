package com.projecteams.project_management.common.util;

import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.projecteams.project_management.common.dto.response.ErrorResponse;
import com.projecteams.project_management.common.dto.response.SuccessResponse;

public class ResponseUtils {

    public static final String FAILED = " FAILED IN: ";
    public static final String SUCCESS = " SUCCESS IN: ";

    public static <T> SuccessResponse<T> buildSuccessResponse(HttpStatus status, String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(SUCCESS + message);

        return response;
    }

    public static <T> SuccessResponse<T> buildSuccessResponse(HttpStatus status, String message, T data) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(SUCCESS + message);
        response.setData(data);

        return response;
    }

    public static <T> ErrorResponse<T> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse<T> response = new ErrorResponse<>();
        response.setStatusCode(status.value());
        response.setMessage(FAILED + message);

        return response;
    }

    public static <T> ErrorResponse<T> buildErrorResponse(HttpStatus status, String resourceId, String message) {
        ErrorResponse<T> response = new ErrorResponse<>();
        if (!Objects.isNull(status))
            response.setStatusCode(status.value());
        if (!Objects.isNull(resourceId))
            response.setResourceId(resourceId);
        if (!Objects.isNull(message))
            response.setMessage(FAILED + message);

        return response;
    }

    public static <T> ErrorResponse<T> buildErrorResponse(HttpStatus status, String message, T data) {
        ErrorResponse<T> response = new ErrorResponse<>();
        if (!Objects.isNull(status))
            response.setStatusCode(status.value());
        if (!Objects.isNull(message))
            response.setMessage(FAILED + message);
        if (!Objects.isNull(data))
            response.setData(data);

        return response;
    }
}
