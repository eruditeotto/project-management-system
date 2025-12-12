package com.projecteams.project_management.comment.dto.request;

import com.projecteams.project_management.comment.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Integer Id;

    @NotBlank(message = "Comment is required")
    private String comment;

    private Integer commentId;

    @NotNull(message = "Task ID is required")
    private Integer taskId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    public Comment toEntity(Comment commentEntity){
        if(Objects.isNull(commentEntity)){
            commentEntity = new Comment();

        }
        commentEntity.setId(Id);
        commentEntity.setComment(comment);
        commentEntity.setCommentId(commentId);
        commentEntity.setTaskId(taskId);
        commentEntity.setUserId(userId);

        return commentEntity;
    }


}
