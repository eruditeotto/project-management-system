package com.projecteams.project_management.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Contract;

import java.io.Serial;
import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable{

    @Serial
    private static final long serialVersionUID = 2790507535614326868L;

    @Id
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="is_active",nullable = false)
    private boolean isActive;

    @Column(name="created_at",nullable = false)
    private String createdAt;

    @Column(name="updated_at",nullable = false)
    private String updatedAt;

}
