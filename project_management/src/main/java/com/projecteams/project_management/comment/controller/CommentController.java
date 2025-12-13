package com.projecteams.project_management.comment.controller;


import com.projecteams.project_management.comment.Comment;
import com.projecteams.project_management.comment.dto.request.CommentRequest;
import com.projecteams.project_management.comment.dto.response.CommentResponse;
import com.projecteams.project_management.comment.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest request){

        CommentResponse response = commentService.create(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(){

        List<CommentResponse> comments = commentService.getAll();

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetCommentById(@PathVariable Integer id){

        CommentResponse response = commentService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentResponse>> getCommentById(@PathVariable Integer taskid){

        List<CommentResponse> comments = commentService.getByTaskId(taskid);

        return ResponseEntity.ok(comments);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(@PathVariable Integer userid){

        List<CommentResponse> comments = commentService.getByUserId( userid);

        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Integer id, @Valid @RequestBody CommentRequest request){

        CommentResponse response = commentService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletteComments(@PathVariable Integer id){

        commentService.delete(id);

        return ResponseEntity.noContent().build();
    }









}
