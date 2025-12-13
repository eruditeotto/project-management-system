package com.projecteams.project_management.comment.dto.response;


import com.projecteams.project_management.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Integer id;

    private String comment;

    private Integer commentId;

    private Integer taskId;

    private Integer userId;

    private String createdAt;

    private String updatedAt;

    public static CommentResponse toResponse(Comment comment){
        if(Objects.isNull(comment)) return null;

        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .commentId(comment.getCommentId())
                .taskId(comment.getTaskId())
                .userId(comment.getUserId())

                .build();

    }

    public static CommentResponse toBasicResponse(Comment comment){
        if(Objects.isNull(comment))return null;

        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .commentId(comment.getCommentId())
                .taskId(comment.getTaskId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())

                .build();

    }


}
