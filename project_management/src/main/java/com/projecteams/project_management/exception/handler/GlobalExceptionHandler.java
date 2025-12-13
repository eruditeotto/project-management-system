package com.projecteams.project_management.exception.handler;

import static com.projecteams.project_management.common.constant.CommonMessages.ACCESS_DENIED;
import static com.projecteams.project_management.common.constant.CommonMessages.INVALID_REQUEST;
import static com.projecteams.project_management.common.constant.CommonMessages.SERVICE_FAILURE;
import static com.projecteams.project_management.common.constant.CommonMessages.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.exception.AccessDeniedException;
import com.projecteams.project_management.exception.BadRequestException;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = getFieldErrorMap(ex.getBindingResult());

        log.error(LoggerUtils.formatError(BAD_REQUEST.name(), getErrorSummary(fieldErrors)));

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, INVALID_REQUEST,
                        fieldErrors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        Map<String, String> fieldErrors = getFieldErrorMap(ex.getBindingResult());

        log.error(LoggerUtils.formatError(BAD_REQUEST.name(), getErrorSummary(fieldErrors)));

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, INVALID_REQUEST,
                        fieldErrors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error(LoggerUtils.formatError(FORBIDDEN.name(), e.getResourceId(), e.getMessage()));
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ResponseUtils.buildErrorResponse(FORBIDDEN, ACCESS_DENIED, e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestError(BadRequestException e) {
        log.error(LoggerUtils.formatError(BAD_REQUEST.name(), e.getResourceId(), e.getMessage()));
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        log.error(LoggerUtils.formatError(NOT_FOUND.name(), e.getResourceId(), e.getMessage()));
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ResponseUtils.buildErrorResponse(NOT_FOUND, INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException e) {
        log.error(LoggerUtils.formatError(INTERNAL_SERVER_ERROR.name(), e.getResourceId(), e.getMessage()));
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ResponseUtils.buildErrorResponse(INTERNAL_SERVER_ERROR, SERVICE_FAILURE, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleError(Exception e) {
        log.error(LoggerUtils.formatError(INTERNAL_SERVER_ERROR.name(), e));
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ResponseUtils.buildErrorResponse(INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR, e.getMessage()));
    }

    private Map<String, String> getFieldErrorMap(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1));
    }

    private String getErrorSummary(Map<String, String> fieldErrors) {
        return fieldErrors.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", "));
    }
}
