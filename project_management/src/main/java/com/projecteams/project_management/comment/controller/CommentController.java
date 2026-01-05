package com.projecteams.project_management.comment.controller;

import com.projecteams.project_management.comment.dto.Request.CommentRequest;
import com.projecteams.project_management.comment.dto.Response.CommentResponse;
import com.projecteams.project_management.comment.service.CommentService;
import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.project.dto.request.ProjectRequest;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.projecteams.project_management.comment.constant.CommentMessages.*;
import static com.projecteams.project_management.common.constant.CommonMessages.PROCESSING;
import static com.projecteams.project_management.project.constant.ProjectMessages.*;
import static com.projecteams.project_management.project.constant.ProjectMessages.DELETING_PROJECT;
import static org.springframework.http.HttpStatus.OK;


@Slf4j
@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getAll(){

        log.info(LoggerUtils.formatSuccess(PROCESSING, RETRIEVING_ALL_COMMENTS));

        List<CommentResponse> comments = commentService.getAll();

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_ALL_COMMENTS,
                comments
        ));

    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getAllByUserId(@PathVariable("id") Long id){

            log.info(LoggerUtils.formatSuccess(PROCESSING, RETRIEVING_COMMENTS_BY_USER_ID));

            List<UserResponse> comments = commentService.getAllCommentsByUserId(id);

            return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                    OK,
                    RETRIEVING_COMMENTS_BY_USER_ID,
                    comments

            ));

    }

    @GetMapping("task/{id}")
    public ResponseEntity<?> getAllByTaskId(@PathVariable("id") Long id){

        log.info(LoggerUtils.formatSuccess(PROCESSING, RETRIEVING_COMMENT_BY_ID));

        List<CommentResponse> comments = commentService.getAllByTaskId(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_ALL_COMMENTS_BY_TASK_ID,
                comments

        ));

    }

    @GetMapping("comment/{id}")
    public ResponseEntity<?> getByCommentId(@PathVariable("id") Long id){

        log.info(LoggerUtils.formatSuccess(PROCESSING,RETRIEVING_ALL_BY_COMMENT_ID));

        List<UserResponse> comments = commentService.getAllByCommentId(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_ALL_BY_COMMENT_ID,
                comments
        ));

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){

        log.info(LoggerUtils.formatSuccess(PROCESSING, RETRIEVING_COMMENT_BY_ID));

        CommentResponse comments = commentService.getById(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_COMMENT_BY_ID,
                comments
        ));

    }

    @PostMapping
    public ResponseEntity<?> createComment(
            @Valid @RequestBody CommentRequest commentRequest,
            @RequestBody(required = false) List<UserRequest> comment
    ) {
        log.info(LoggerUtils.formatProcess(PROCESSING, CREATING_PROJECT));
        commentService.save(commentRequest, comment != null ? comment : new ArrayList<>());

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                CREATING_COMMENT
        ));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        log.info(LoggerUtils.formatProcess(PROCESSING, UPDATING_COMMENT));
        commentService.update(id, commentRequest);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                UPDATING_COMMENT
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
        log.info(LoggerUtils.formatProcess(PROCESSING, DELETING_COMMENT));
        commentService.delete(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                DELETING_COMMENT
        ));
    }
}

