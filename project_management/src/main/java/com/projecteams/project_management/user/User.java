package com.projecteams.project_management.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Contract;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="is_active", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
