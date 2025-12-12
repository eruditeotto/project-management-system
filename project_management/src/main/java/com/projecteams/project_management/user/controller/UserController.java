package com.projecteams.project_management.user.controller;

import static com.projecteams.project_management.common.constant.InfoMessage.USERS_RETRIEVED;
import static org.springframework.http.HttpStatus.OK;

import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.dto.response.UserResponse;
import com.projecteams.project_management.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<UserResponse> response = userService.getAll();

        return ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, USERS_RETRIEVED, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByid(@PathVariable Long id){

        return  ResponseEntity.ok(
                ResponseUtils.buildSuccessResponse(OK, USERS_RETRIEVED)
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user){
        userService.save(user);
    }

}
