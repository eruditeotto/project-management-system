package com.projecteams.project_management.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projecteams.project_management.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String email;

    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public static UserResponse toResponse(User user) {
        if (Objects.isNull(user)) return null;

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.getIsActive())

                .build();
    }

    public static UserResponse toBasicResponse(User user) {
        if (Objects.isNull(user)) return null;

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())

                .build();
    }




}
