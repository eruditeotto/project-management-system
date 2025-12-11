package com.projecteams.project_management.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerUtils {

    private static final String EVENT = "event=[\"%s\"] ";
    private static final String RESOURCE_ID = "resourceId=\"%s\" ";
    private static final String ERROR_CLASS = "errorClass=\"%s\" ";
    private static final String MESSAGE = "message=\"%s\"";

    public String formatInfo(String event, String resourceId, String message) {
        if (resourceId != null) {
            return EVENT.formatted(event)
                    + RESOURCE_ID.formatted(resourceId)
                    + MESSAGE.formatted(message);
        }
        return EVENT.formatted(event) + MESSAGE.formatted(message);
    }

    public String formatInfo(String event, String message) {
        return EVENT.formatted(event) + MESSAGE.formatted(message);
    }

    public String formatError(String event, String resourceId, String message) {
        if (resourceId != null) {
            return EVENT.formatted(event)
                    + RESOURCE_ID.formatted(resourceId)
                    + MESSAGE.formatted(message);
        }
        return EVENT.formatted(event) + MESSAGE.formatted(message);
    }

    public String formatError(String event, Throwable e) {
        return EVENT.formatted(event)
                + MESSAGE.formatted(e.getMessage())
                + ERROR_CLASS.formatted(e.getClass().getSimpleName());
    }

    public String formatError(String event, String message) {
        return EVENT.formatted(event) + MESSAGE.formatted(message);
    }
}
