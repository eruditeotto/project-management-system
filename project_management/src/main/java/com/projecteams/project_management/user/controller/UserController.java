package com.projecteams.project_management.user.controller;

import static com.projecteams.project_management.common.constant.CommonMessages.PROCESSING;
import static com.projecteams.project_management.user.constant.UserMessages.CREATING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.DELETING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_ALL_USER;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.UPDATING_USER;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.dto.response.UserResponse;
import com.projecteams.project_management.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        log.info(LoggerUtils.formatProcess(PROCESSING, RETRIEVING_ALL_USER));
        List<UserResponse> response = userService.getAll();

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, RETRIEVING_ALL_USER, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByid(@PathVariable Long id) {

        log.info(LoggerUtils.formatProcess(PROCESSING, RETRIEVING_USER));
        UserResponse response = userService.getById(id);

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, RETRIEVING_USER, response));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserRequest user) {

        log.info(LoggerUtils.formatProcess(PROCESSING, CREATING_USER));
        userService.save(user);

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, CREATING_USER));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest
    ) {

        log.info(LoggerUtils.formatProcess(PROCESSING, UPDATING_USER));
        userService.update(id, userRequest);

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, UPDATING_USER));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        log.info(LoggerUtils.formatProcess(PROCESSING, DELETING_USER));
        userService.delete(id);

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, DELETING_USER));
    }

}