package com.projecteams.project_management.user.service;

import static com.projecteams.project_management.common.constant.CommonMessages.CREATE;
import static com.projecteams.project_management.common.constant.CommonMessages.DELETE;
import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVE;
import static com.projecteams.project_management.common.constant.CommonMessages.UPDATE;
import static com.projecteams.project_management.user.constant.UserMessages.CREATING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.DELETING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_ALL_USER;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.UPDATING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.USER_NOT_FOUND;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.request.UserRequest;
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
        try {
            List<User> users = userRepository.findAll();

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_ALL_USER));

            return users.stream().map(UserResponse::toBasicResponse).toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_USER, e);
        }
    }

    public UserResponse getById(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, userId));

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_USER, userId));

            return UserResponse.toResponse(user);
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_USER, e);
        }
    }

    public void save(UserRequest userRequest) {
        try {
            User user = userRequest.toEntity(null);

            userRepository.save(user);

            log.info(LoggerUtils.formatSuccess(CREATE, CREATING_USER, user.getId()));
        } catch (RuntimeException e) {
            throw new ServiceException(CREATING_USER, e);
        }
    }

    public void update(Long userId, UserRequest userRequest) {
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, userId));

            User updatedUser = userRequest.toEntity(existingUser);
            userRepository.save(updatedUser);

            log.info(LoggerUtils.formatSuccess(UPDATE, UPDATING_USER, userId));
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(UPDATING_USER, e);
        }
    }

    public void delete(Long userId) {
        try {
            if(!userRepository.existsById(userId)) throw new NotFoundException(USER_NOT_FOUND, userId);

            User user = userRepository.findById(userId).orElseThrow();
            user.setIsActive(false);
            userRepository.save(user);

            log.info(LoggerUtils.formatSuccess(DELETE, DELETING_USER, userId));
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(DELETING_USER, e);
        }
    }
}