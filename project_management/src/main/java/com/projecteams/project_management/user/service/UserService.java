package com.projecteams.project_management.user.service;

import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVED;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_ALL_USER;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.response.UserResponse;
import com.projecteams.project_management.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAll() {

        return executeWithLogging(RETRIEVED, RETRIEVING_ALL_USER, null, () -> {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return Collections.emptyList();
            }

            return users.stream().map(UserResponse::toResponse).toList();
        });

    }

    private <T> T executeWithLogging(String successEvent, String action, Object resourceId, Supplier<T> supplier) {
        try {
            T result = supplier.get();
            log.info(LoggerUtils.formatSuccess(successEvent, action, resourceId));
            return result;
        } catch (RuntimeException e) {
            throw new ServiceException(action, e);
        }
    }
}