package com.projecteams.project_management.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {

    public static final String INVALID_REQUEST = "Invalid request";
    public static final String UNEXPECTED_ERROR = "Unexpected server error occurred";
    public static final String SERVICE_FAILURE = "Service failure occurred while processing the request";
    public static final String ACCESS_DENIED = "You do not have permission to perform this action";


    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_DUPLICATE = "User already exists";
    public static final String USER_BAD_REQUEST = "Invalid user request data";
}
