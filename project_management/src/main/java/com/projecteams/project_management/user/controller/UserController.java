package com.projecteams.project_management.user.controller;

import static com.projecteams.project_management.common.constant.CommonMessages.PROCESSING;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_ALL_USER;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_USER;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.user.User;
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

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, RETRIEVING_USER));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, RETRIEVING_USER));
    }

}
