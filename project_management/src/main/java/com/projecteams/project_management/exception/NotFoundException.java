package com.projecteams.project_management.exception;

public class NotFoundException extends RuntimeException {
    private final String resourceId;
    
    public NotFoundException(String message) {
        super(message);
        this.resourceId = null;
    }

    public NotFoundException(String message, Object id) {
        super(message);
        this.resourceId = String.valueOf(id);
    }

    public NotFoundException(String message, String resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }
}