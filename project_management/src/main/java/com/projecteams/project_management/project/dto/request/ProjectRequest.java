package com.projecteams.project_management.project.dto.request;

import java.time.LocalDate;
import java.util.Objects;

import com.projecteams.project_management.common.enums.PriorityLevel;
import com.projecteams.project_management.common.enums.Status;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.user.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    private Long id;

    @NotBlank(message = "Project name is required")
    private String name;

    @NotBlank(message = "Project description is required")
    private String description;

    @NotNull(message = "Project due date is required")
    private LocalDate dueDate;

    @NotNull(message = "Project priority level is required")
    private PriorityLevel priorityLevel;

    @NotNull(message = "Project priority level is required")
    private Status status;

    @NotNull(message = "Project archive status is required")
    private boolean isArchived;

    @NotNull(message = "Creator ID is required")
    private User creator;

    public Project toEntity(Project project) {
        if (Objects.isNull(project)) {
            project = new Project();
        }

        project.setName(name);
        project.setDescription(description);
        project.setDueDate(dueDate);
        project.setPriorityLevel(priorityLevel);
        project.setStatus(status);
        project.setArchived(isArchived);

        return project;
    }

}
