package com.projecteams.project_management.user.dto.response;

import com.projecteams.project_management.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String email;

    private boolean isActive;

    private String createdAt;

    private String updatedAt;

    public static UserResponse toResponse(User user) {
        if (Objects.isNull(user)) return null;

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())

                .build();
    }

    public static UserResponse toBasicResponse(User user) {
        if (Objects.isNull(user)) return null;

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())

                .build();
    }




}
