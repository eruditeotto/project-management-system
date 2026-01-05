package com.projecteams.project_management.comment.repository;

import com.projecteams.project_management.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {



    @Query("SELECT c FROM Comment c WHERE c. taskId = :taskId ORDER BY c.createdAt")
    List<Comment> findAllComment();
    List<Comment> findBytaskId(@Param("taskId") Long taskId);


    @Query("SELECT c FROM Comment c WHERE c. commentId = :commentId ORDER BY c.createdAt")
    List<Long> findRepliesByCommentId(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c WHERE c.taskId = :taskId AND c.commentId IS NULL ORDER BY c.createdAt ASC")
    List<Comment> findCommentsByTaskId(@Param("taskId") Long taskId);

    @Query("SELECT c FROM Comment c WHERE c.userId = :userId ORDER BY c.createdAt DESC")
    boolean findByUserId(@Param("userId") Long userId);




}
