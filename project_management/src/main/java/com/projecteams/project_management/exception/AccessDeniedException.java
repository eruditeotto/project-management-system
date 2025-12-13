package com.projecteams.project_management.exception;

public class AccessDeniedException extends RuntimeException {
    private final String resourceId;

    public AccessDeniedException(String message) {
        super(message);
        this.resourceId = null;
    }

    public AccessDeniedException(String message, Object id) {
        super(message);
        this.resourceId = String.valueOf(id);
    }

    public AccessDeniedException(String message, String resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }
}