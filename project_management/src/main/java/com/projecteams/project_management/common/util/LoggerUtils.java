package com.projecteams.project_management.common.util;

import java.util.Objects;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class LoggerUtils {

    private static final String EVENT = "event=[\"{}\"] ";
    private static final String RESOURCE_ID = "resourceId=\"{}\" ";
    private static final String ERROR_CLASS = "errorClass=\"{}\" ";
    private static final String MESSAGE = "message=\"{}\"";

    public void logInfo(String event, String resourceId, String message) {
        if (Objects.nonNull(resourceId))
            log.info(EVENT + RESOURCE_ID + MESSAGE, event, resourceId, message);
        else
            log.info(EVENT + MESSAGE, event, message);
    }

    public void logInfo(String event, String message) {
        log.info(EVENT + MESSAGE, event, message);
    }

    public void logError(String event, String resourceId, String message) {
        if (Objects.nonNull(resourceId))
            log.error(EVENT + RESOURCE_ID + MESSAGE, event, resourceId, message);
        else
            log.error(EVENT + MESSAGE, event, message);
    }

    public void logError(String event, Throwable e) {
        log.error(EVENT + MESSAGE + ERROR_CLASS, event, e.getClass().getSimpleName(), e.getMessage());
    }

    public void logError(String event, String message) {
        log.error(EVENT + MESSAGE, event, message);
    }
}
