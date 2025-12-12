package com.projecteams.project_management.project.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.projecteams.project_management.common.enums.PriorityLevel;
import com.projecteams.project_management.common.enums.Status;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.user.dto.response.UserResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private PriorityLevel priorityLevel;
    private Status status;
    private boolean isArchived;
    private UserResponse creator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .dueDate(project.getDueDate())
                .priorityLevel(project.getPriorityLevel())
                .status(project.getStatus())
                .isArchived(project.isArchived())
                .creator(UserResponse.)
                .build();
    }

    public static ProjectResponse toBasicResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .dueDate(project.getDueDate())
                .priorityLevel(project.getPriorityLevel())
                .status(project.getStatus())
                .isArchived(project.isArchived())
                .creator(project.getCreator())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
