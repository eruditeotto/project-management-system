package com.projecteams.project_management.comment.service;

import static com.projecteams.project_management.common.constant.InfoMessage.RETRIEVE;

import com.projecteams.project_management.comment.Comment;
import com.projecteams.project_management.comment.dto.request.CommentRequest;
import com.projecteams.project_management.comment.dto.response.CommentResponse;
import com.projecteams.project_management.comment.repository.CommentRepository;
import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.ServiceException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {


    private final CommentRepository commentRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

    public List<CommentResponse> getAll(){

        try{
            List<Comment> comments = commentRepository.findAll();
            log.info(LoggerUtils.formatInfo(RETRIEVE, "Comments retrieved"));

            if(comments.isEmpty()) return Collections.emptyList();
            return comments.stream().map(CommentResponse::toBasicResponse).toList();

        }catch(RuntimeException e){
            throw new ServiceException(e.getMessage());
        }

    }

    public CommentResponse getById(Integer id){

        try{
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Comment id not found"+id));

            log.info(LoggerUtils.formatInfo(RETRIEVE,"Comment with id:" + id));

            return CommentResponse.toBasicResponse(comment);

        }catch(RuntimeException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<CommentResponse> getByTaskId(Integer taskid){

        try{
            List<Comment> comments = commentRepository.findByTaskId(taskid);
            log.info(LoggerUtils.formatInfo(RETRIEVE, "Comments retrieved for task id" + taskid));

            if(comments.isEmpty()) return Collections.emptyList();

            return comments.stream().map(CommentResponse::toBasicResponse).toList();

        }catch(RuntimeException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public CommentResponse create(CommentRequest request){

        try{
            String currentTime = LocalDateTime.now().format(formatter);

            Comment comment = Comment.builder()

                    .comment(request.getComment())
                    .commentId(request.getCommentId())
                    .taskId(request.getTaskId())
                    .userId(request.getUserId())
                    .createdAt(currentTime)
                    .updatedAt(currentTime)

                    .build();

            Comment savedComment = comment.save(comment);

            log.info(LoggerUtils.formatInfo("CREATED", "New Comment created with id:" + savedComment.getId()));


        }
    }
}
