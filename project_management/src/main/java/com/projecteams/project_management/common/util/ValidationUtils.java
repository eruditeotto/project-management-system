package com.projecteams.project_management.common.util;

import java.util.List;
import java.util.Objects;

import com.projecteams.project_management.exception.BadRequestException;
import com.projecteams.project_management.exception.NotFoundException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {

    public void checkIfNull(String message, Object... objects) {
        for (Object obj : objects) {
            if (Objects.isNull(obj))
                throw new BadRequestException(message);
        }
    }

    public <T> List<T> validateNotEmpty(List<T> list, String notFoundMessage, Long id) {
        if (list.isEmpty()) {
            throw new NotFoundException(notFoundMessage, id);
        }
        return list;
    }
}