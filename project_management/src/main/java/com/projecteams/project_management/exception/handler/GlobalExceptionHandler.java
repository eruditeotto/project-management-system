package com.projecteams.project_management.exception.handler;

import static com.projecteams.project_management.common.constant.CommonMessages.INVALID_FORMAT;
import static com.projecteams.project_management.common.constant.CommonMessages.INVALID_FORMAT_MESSAGE;
import static com.projecteams.project_management.common.constant.CommonMessages.INVALID_REQUEST;
import static com.projecteams.project_management.common.constant.CommonMessages.TYPE_MISMATCH;
import static com.projecteams.project_management.common.constant.CommonMessages.TYPE_MISMATCH_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

        @ExceptionHandler(InvalidFormatException.class)
        public ResponseEntity<?> handleInvalidFormat(InvalidFormatException ex) {

                if (ex.getTargetType() != null && ex.getTargetType().isEnum()) {

                        String field = ex.getPath().get(0).getFieldName();

                        String message = String.format(
                                        INVALID_FORMAT,
                                        ex.getValue(),
                                        field,
                                        Arrays.toString(ex.getTargetType().getEnumConstants()));

                        return ResponseEntity
                                        .badRequest()
                                        .body(Map.of(INVALID_FORMAT_MESSAGE, message));
                }

                return ResponseEntity
                                .status(BAD_REQUEST)
                                .body(ResponseUtils.buildErrorResponse(
                                                BAD_REQUEST,
                                                INVALID_FORMAT));
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
                String name = ex.getName();
                String type = ex.getRequiredType().getSimpleName();
                Object value = ex.getValue();
                String msg = String.format(TYPE_MISMATCH_MESSAGE, name, type, value);
                return ResponseEntity
                                .status(BAD_REQUEST)
                                .body(ResponseUtils.buildErrorResponse(BAD_REQUEST, TYPE_MISMATCH, msg));
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
                log.error(LoggerUtils.formatError(FORBIDDEN.name(), e.getMessage(), e.getResourceId()));
                return ResponseEntity
                                .status(FORBIDDEN)
                                .body(ResponseUtils.buildErrorResponse(
                                                FORBIDDEN,
                                                e.getResourceId(),
                                                e.getMessage()));
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<?> handleBadRequestError(BadRequestException e) {
                log.error(LoggerUtils.formatError(BAD_REQUEST.name(), e.getMessage(), e.getResourceId()));
                return ResponseEntity
                                .status(BAD_REQUEST)
                                .body(ResponseUtils.buildErrorResponse(
                                                BAD_REQUEST,
                                                e.getResourceId(),
                                                e.getMessage()));
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
                log.error(LoggerUtils.formatError(NOT_FOUND.name(), e.getMessage(), e.getResourceId()));
                return ResponseEntity
                                .status(NOT_FOUND)
                                .body(ResponseUtils.buildErrorResponse(
                                                NOT_FOUND,
                                                e.getResourceId(),
                                                e.getMessage()));
        }

        @ExceptionHandler(ServiceException.class)
        public ResponseEntity<?> handleServiceException(ServiceException e) {
                log.error(LoggerUtils.formatError(INTERNAL_SERVER_ERROR.name(), e.getMessage(), e.getResourceId()));
                return ResponseEntity
                                .status(INTERNAL_SERVER_ERROR)
                                .body(ResponseUtils.buildErrorResponse(
                                                INTERNAL_SERVER_ERROR,
                                                e.getResourceId(),
                                                e.getMessage()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleError(Exception e) {
                log.error(LoggerUtils.formatError(INTERNAL_SERVER_ERROR.name(), e));
                return ResponseEntity
                                .status(INTERNAL_SERVER_ERROR)
                                .body(ResponseUtils.buildErrorResponse(
                                                INTERNAL_SERVER_ERROR,
                                                e.getMessage()));
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
