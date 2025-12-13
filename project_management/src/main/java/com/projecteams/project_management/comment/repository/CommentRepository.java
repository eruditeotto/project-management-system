package com.projecteams.project_management.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projecteams.project_management.comment.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByTaskId(Integer taskId);
    List<Comment> findByUserId(Integer userId);
    List<Comment> findByComment(String commentId);





}
