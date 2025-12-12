package com.projecteams.project_management.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerUtils {

    private final StringBuilder sb = new StringBuilder();

    private static final String EVENT = "event=[\"%s\"]";
    private static final String RESOURCE_ID = " resourceId=\"%s\"";
    private static final String ERROR_CLASS = " errorClass=\"%s\"";
    private static final String MESSAGE = " message=\"%s\"";

    public static final String PROCESSING = "PROCESSING: ";
    public static final String SUCCESS = "SUCCESS IN: ";
    public static final String FAILED = "FAILED IN: ";

    private String format(String event, String statusMessage, Object resourceId, Throwable e) {
        sb.append(EVENT.formatted(event));
        sb.append(MESSAGE.formatted(statusMessage));
        if (resourceId != null)
            sb.append(RESOURCE_ID.formatted(resourceId));
        if (e != null)
            sb.append(ERROR_CLASS.formatted(e.getClass().getSimpleName()));
        return sb.toString();
    }

    public String formatProcess(String event, String message) {
        return format(event, PROCESSING + message, null, null);
    }

    public String formatProcess(String event, String message, Object resourceId) {
        return format(event, PROCESSING + message, resourceId, null);
    }

    public String formatSuccess(String event, String message) {
        return format(event, SUCCESS + message, null, null);
    }

    public String formatSuccess(String event, String message, Object resourceId) {
        return format(event, SUCCESS + message, resourceId, null);
    }

    public String formatError(String event, String message) {
        return format(event, FAILED + message, null, null);
    }

    public String formatError(String event, String message, Object resourceId) {
        return format(event, FAILED + message, resourceId, null);
    }

    public String formatError(String event, Throwable e) {
        return format(event, FAILED, null, e);
    }

    public String formatError(String event, String message, Throwable e, Object resourceId) {
        return format(event, FAILED + message, resourceId, e);
    }
}
