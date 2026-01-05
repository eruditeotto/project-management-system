package com.projecteams.project_management.comment.service;
import static com.projecteams.project_management.comment.constant.CommentMessages.*;
import static com.projecteams.project_management.common.constant.CommonMessages.*;
import static com.projecteams.project_management.project.constant.ProjectMessages.PROJECT_NOT_FOUND;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_MEMBER_PROJECT_BY_ID;
import static com.projecteams.project_management.user.constant.UserMessages.USER_NOT_FOUND;

import com.projecteams.project_management.comment.Comment;
import com.projecteams.project_management.comment.dto.Request.CommentRequest;
import com.projecteams.project_management.comment.dto.Response.CommentResponse;
import com.projecteams.project_management.comment.repository.CommentRepository;
import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.project.dto.response.ProjectResponse;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.dto.response.UserResponse;
import com.projecteams.project_management.user.repository.UserRepository;

import com.projecteams.project_management.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {

    private final UserService userService;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getAll(){

        try{
            List<Comment> comments = commentRepository.findAllComment();

            log.info(LoggerUtils.formatSuccess(RETRIEVE,RETRIEVING_ALL_COMMENTS));

            return comments.stream().map(CommentResponse::toResponse).toList();

        }catch(RuntimeException e){
            throw new ServiceException(RETRIEVING_ALL_COMMENTS, e);
        }

    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllByTaskId(Long taskId){

        try{
            if(userRepository.existsById(taskId))
                throw new NotFoundException(TASK_NOT_FOUND + RETRIEVING_TASK_ID, taskId);

            List<Comment> comments = commentRepository.findBytaskId(taskId);

            List<Comment> taskIds = commentRepository.findCommentsByTaskId(taskId);

            log.info(LoggerUtils.formatSuccess(RETRIEVE,RETRIEVING_ALL_COMMENTS_BY_TASK_ID,taskIds));

            return comments.stream().map(CommentResponse::toResponse).toList();


        }catch(RuntimeException e){
            throw new ServiceException(RETRIEVING_ALL_COMMENTS_BY_TASK_ID, e);
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllByCommentId(Long commentId){
        try{
            if(!commentRepository.existsById(commentId))
                throw new NotFoundException(COMMENT_NOT_FOUND + RETRIEVING_COMMENT_BY_ID);

            List<Long> commentids = commentRepository.findRepliesByCommentId(commentId);
            List<User> user = userRepository.findAllByIdIn(commentids);

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_COMMENT_BY_ID, commentId));
            return user.stream().map(UserResponse::toResponse).toList();

        } catch (NotFoundException e){
            throw e;

        }catch(RuntimeException e){
            throw new ServiceException(RETRIEVING_COMMENTS, e);
        }

    }

    @Transactional(readOnly = true)
    public CommentResponse getById(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_COMMENT_BY_ID,
                            commentId));

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_COMMENT_BY_ID, commentId));
            return CommentResponse.toBasicResponse(comment);
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_COMMENT_BY_ID, e);
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllCommentsByUserId(Long userId){

        try{
            if(commentRepository.findByUserId(userId))
                throw new NotFoundException(USER_NOT_FOUND + RETRIEVING_COMMENTS_BY_USER_ID);

            List<Comment> comment = commentRepository.findAllComment();
            List<User> user = userRepository.findAll();

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_COMMENTS_BY_USER_ID,userId));
            return user.stream().map(UserResponse::toResponse).toList();

        }catch(RuntimeException e){
            throw new ServiceException(RETRIEVING_COMMENTS_BY_USER_ID,e);
        }

    }

    @Transactional
    public void save(CommentRequest commentRequest,List<UserRequest> userRequests){

        try{
            Comment comment = commentRequest.toEntity(null);

            Comment savedComment = commentRepository.save(comment);

            userService.getAll();

            log.info(LoggerUtils.formatSuccess(CREATE, CREATING_COMMENT,savedComment.getId()));

        }catch(RuntimeException e){
            throw new ServiceException(CREATING_COMMENT, e);
        }

    }

    @Transactional
    public void update(Long commentId, CommentRequest commentRequest){

        try{
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(()-> new NotFoundException(COMMENT_NOT_FOUND + RETRIEVING_COMMENTS_BY_USER_ID,
                            commentId));

            commentRepository.save(commentRequest.toEntity(comment));

            log.info(LoggerUtils.formatSuccess(UPDATE, UPDATING_COMMENT, commentId));

        }catch (RuntimeException e){

            throw new ServiceException (UPDATING_COMMENT, e);
        }
    }

    @Transactional
    public void delete(Long commentId){

        try{
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(()-> new NotFoundException(COMMENT_NOT_FOUND + RETRIEVING_COMMENTS_BY_USER_ID,
                            commentId));

            commentRepository.delete(comment);

            log.info(LoggerUtils.formatSuccess(DELETE, DELETING_COMMENT, commentId));

        } catch (RuntimeException e) {

            throw new ServiceException(DELETING_COMMENT, e);
        }
    }
}
