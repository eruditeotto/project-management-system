package com.projecteams.project_management.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.exception.BadRequestException;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_REQUEST = "Invalid Request";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, INVALID_REQUEST,
                        getFieldErrorMap(ex.getBindingResult())));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, INVALID_REQUEST,
                        getFieldErrorMap(ex.getBindingResult())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ResponseUtils.buildErrorResponse(FORBIDDEN, INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestError(BadRequestException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, INVALID_REQUEST, e.getMessage()));

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ResponseUtils.buildErrorResponse(NOT_FOUND, INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ResponseUtils.buildErrorResponse(INTERNAL_SERVER_ERROR, INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ResponseUtils.buildErrorResponse(INTERNAL_SERVER_ERROR, INVALID_REQUEST, e.getMessage()));
    }

    private Map<String, String> getFieldErrorMap(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1));
    }
}
