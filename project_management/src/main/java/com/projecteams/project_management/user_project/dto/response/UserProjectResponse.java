package com.projecteams.project_management.user_project.dto.response;

import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user_project.UserProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectResponse {

    private Long id;
    private User user;
    private Project project;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserProjectResponse toBasicResponse(UserProject userProject) {
        return UserProjectResponse.builder()
                .id(userProject.getId())
                .user(userProject.getUser())
                .project(userProject.getProject())
                .createdAt(userProject.getCreatedAt())
                .updatedAt(userProject.getUpdatedAt())
                .build();
    }
}
