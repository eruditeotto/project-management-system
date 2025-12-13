package com.projecteams.project_management.project.service;

import static com.projecteams.project_management.common.constant.CommonMessages.CREATED;
import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVED;
import static com.projecteams.project_management.common.constant.CommonMessages.UPDATED;
import static com.projecteams.project_management.project.constant.ProjectMessages.CREATING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.PROJECT_NOT_FOUND;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_MEMBER_PROJECT_BY_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.UPDATING_PROJECT;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.common.util.ValidationUtils;
import com.projecteams.project_management.exception.AccessDeniedException;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.project.dto.request.ProjectRequest;
import com.projecteams.project_management.project.dto.response.ProjectResponse;
import com.projecteams.project_management.project.repository.ProjectRepository;
import com.projecteams.project_management.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectResponse> getAll() {

        return executeWithLogging(RETRIEVED, RETRIEVING_ALL_PROJECT, null, () -> {
            List<Project> projects = projectRepository.findAll();
            if (projects.isEmpty()) {
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_ALL_PROJECT, null);
            }
            return projects.stream().map(ProjectResponse::toResponse).toList();
        });

    }

    public List<ProjectResponse> getAllByMemberId(Long userId) {

        return executeWithLogging(RETRIEVED, RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, userId, () -> {
            ValidationUtils.checkIfNull(RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, userId);
            List<Project> projects = projectRepository.findAllByMember(userId);
            if (projects.isEmpty()) {
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, userId);
            }
            return projects.stream().map(ProjectResponse::toResponse).toList();
        });

    }

    public List<ProjectResponse> getAllActiveByMemberId(Long userId) {

        return executeWithLogging(RETRIEVED, RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID, userId, () -> {
            ValidationUtils.checkIfNull(RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID, userId);
            List<Project> projects = projectRepository.findAllActiveByMember(userId);
            if (projects.isEmpty()) {
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID, userId);
            }
            return projects.stream().map(ProjectResponse::toResponse).toList();
        });

    }

    public ProjectResponse getById(Long projectId, Long userId) {

        return executeWithLogging(RETRIEVED, RETRIEVING_MEMBER_PROJECT_BY_ID, projectId, () -> {
            ValidationUtils.checkIfNull(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId, userId);

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));

            if (!projectRepository.isMember(projectId, userId)) {
                throw new AccessDeniedException(RETRIEVING_MEMBER_PROJECT_BY_ID, project.getId());
            }

            return ProjectResponse.toBasicResponse(project);
        });

    }

    public ProjectResponse save(ProjectRequest projectRequest) {

        return executeWithLogging(CREATED, CREATING_PROJECT, null, () -> {
            ValidationUtils.checkIfNull(CREATING_PROJECT, projectRequest);
            Project project = projectRequest.toEntity(null);
            projectRepository.save(project);
            return ProjectResponse.toBasicResponse(project);
        });

    }

    public ProjectResponse update(Long projectId, ProjectRequest projectRequest, Long userId) {

        return executeWithLogging(UPDATED, UPDATING_PROJECT, projectId, () -> {
            ValidationUtils.checkIfNull(UPDATING_PROJECT, projectRequest);

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));

            Project updatedProject = projectRequest.toEntity(project);
            projectRepository.save(updatedProject);

            return ProjectResponse.toBasicResponse(updatedProject);
        });
        
    }

    private <T> T executeWithLogging(String successEvent, String action, Object resourceId, Supplier<T> supplier) {
        try {
            T result = supplier.get();
            log.info(LoggerUtils.formatSuccess(successEvent, action, resourceId));
            return result;
        } catch (RuntimeException e) {
            throw new ServiceException(action, e);
        }
    }
}
