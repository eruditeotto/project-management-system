package com.projecteams.project_management.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projecteams.project_management.comment.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByTaskId(Long taskId);
    List<Comment> findByUserId(Long userId);
    List<Comment> findByComment(Long commentId);
    
}
