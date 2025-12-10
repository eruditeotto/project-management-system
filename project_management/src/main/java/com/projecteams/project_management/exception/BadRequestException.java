package com.projecteams.project_management.exception;

public class BadRequestException extends RuntimeException {
    private final String resourceId;

    public BadRequestException(String message) {
        super(message);
        this.resourceId = null;
    }

    public BadRequestException(String message, int id) {
        super(message);
        this.resourceId = String.valueOf(id);
    }

    public BadRequestException(String message, String resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }
}