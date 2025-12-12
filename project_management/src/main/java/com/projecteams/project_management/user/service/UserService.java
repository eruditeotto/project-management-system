package com.projecteams.project_management.user.service;


import static com.projecteams.project_management.common.constant.InfoMessage.RETRIEVE;
import static com.projecteams.project_management.common.constant.InfoMessage.USERS_RETRIEVED;
import static com.projecteams.project_management.common.constant.InfoMessage.USER_SAVING;
import static com.projecteams.project_management.common.constant.InfoMessage.USER_SAVED;


import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.response.UserResponse;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAll(){
        try{
            List<User> users = userRepository.findAll();

            log.info(LoggerUtils.formatInfo(RETRIEVE, USERS_RETRIEVED));

            if(users.isEmpty()) return Collections.emptyList();

            return users.stream().map(UserResponse::toResponse).toList();

        } catch (RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
