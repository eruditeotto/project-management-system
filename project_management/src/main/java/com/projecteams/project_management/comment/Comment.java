package com.projecteams.project_management.comment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")

public class Comment {

    @Serial
    private static final long serialVersionUID= 5942483735188732053L;

    @Id
    @Column(name = "id")
    private Integer id;


    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public Comment save(Comment comment){
        return null;
    }

}
