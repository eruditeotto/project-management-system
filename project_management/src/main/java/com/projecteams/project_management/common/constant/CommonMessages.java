package com.projecteams.project_management.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonMessages {

    public static final String RETRIEVE = "RETRIEVED";
    public static final String CREATE = "CREATED";
    public static final String ADD = "ADDED";
    public static final String UPDATE = "UPDATED";
    public static final String DELETE = "DELETED";
    public static final String PROCESSING = "PROCESSING";

    public static final String INVALID_REQUEST = "Invalid request";
    public static final String UNEXPECTED_ERROR = "Unexpected server error occurred";
    public static final String SERVICE_FAILURE = "Service failure occurred while processing the request";
    public static final String ACCESS_DENIED = "You do not have permission to perform this action";
    public static final String INVALID_FORMAT = "Invalid format";
    public static final String TYPE_MISMATCH = "Invalid format";
    public static final String INVALID_FORMAT_MESSAGE = "'%s' is not a valid value for %s. Accepted values: %s";
    public static final String TYPE_MISMATCH_MESSAGE = "'%s' should be a valid %s and not '%s'";
}
