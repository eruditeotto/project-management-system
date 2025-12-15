package com.projecteams.project_management.comment.controller;


import com.projecteams.project_management.comment.Comment;
import com.projecteams.project_management.comment.dto.request.CommentRequest;
import com.projecteams.project_management.comment.dto.response.CommentResponse;
import com.projecteams.project_management.comment.service.CommentService;


import com.projecteams.project_management.common.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<?> getAllComments(){

        List<CommentResponse> comments = commentService.getAll();

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetCommentById(@PathVariable Long id){

        CommentResponse response = commentService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long taskid){

        List<CommentResponse> comments = commentService.getByTaskId(taskid);

        return ResponseEntity.ok(comments);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllCommentsByUserId(@PathVariable Long userid){

        List<CommentResponse> comments = commentService.getByUserId( userid);

        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<?> createComment( @Validated @RequestBody CommentRequest request){

        CommentResponse response = commentService.create(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,  @Validated @RequestBody CommentRequest request){

        CommentResponse response = commentService.getById(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable Long id){

        commentService.delete(id);

        return ResponseEntity.ok(deleteComments(id));
    }

    public static <T> SuccessResponse<T> buildSuccess(HttpStatus status, String message){
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setStatusCode(status.value());

        return response;
    }









}
