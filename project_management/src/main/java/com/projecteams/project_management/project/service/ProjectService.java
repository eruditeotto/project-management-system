package com.projecteams.project_management.project.service;

import static com.projecteams.project_management.common.constant.CommonMessages.CREATED;
import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVED;
import static com.projecteams.project_management.common.constant.CommonMessages.UPDATED;
import static com.projecteams.project_management.project.constant.ProjectMessages.CREATING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.PROJECT_NOT_FOUND;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_ACTIVE_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_MEMBER_PROJECT_BY_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.UPDATING_PROJECT;

import java.util.List;

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
        try {
            List<Project> projects = ValidationUtils.validateNotEmpty(
                    projectRepository.findAll(),
                    PROJECT_NOT_FOUND + RETRIEVING_ALL,
                    null);

            log.info(LoggerUtils.formatInfo(RETRIEVED, RETRIEVING_ALL));
            return projects.stream()
                    .map(ProjectResponse::toResponse)
                    .toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL);
        }
    }

    public List<ProjectResponse> getAllByMemberId(Long userId) {
        try {
            ValidationUtils.checkIfNull(RETRIEVING_ALL_BY_MEMBER_ID, userId);

            List<Project> projects = ValidationUtils.validateNotEmpty(
                    projectRepository.findAllByMember(userId),
                    PROJECT_NOT_FOUND + RETRIEVING_ALL_BY_MEMBER_ID,
                    userId);

            log.info(LoggerUtils.formatInfo(RETRIEVED, RETRIEVING_ALL_BY_MEMBER_ID, userId));
            return projects.stream()
                    .map(ProjectResponse::toResponse)
                    .toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_BY_MEMBER_ID, userId);
        }
    }

    public List<ProjectResponse> getAllActiveByMemberId(Long userId) {
        try {
            ValidationUtils.checkIfNull(RETRIEVING_ALL_ACTIVE_BY_MEMBER_ID, userId);

            List<Project> projects = ValidationUtils.validateNotEmpty(
                    projectRepository.findAllActiveByMember(userId),
                    PROJECT_NOT_FOUND + RETRIEVING_ALL_ACTIVE_BY_MEMBER_ID,
                    userId);

            log.info(LoggerUtils.formatInfo(RETRIEVED, RETRIEVING_ALL_ACTIVE_BY_MEMBER_ID, userId));
            return projects.stream()
                    .map(ProjectResponse::toResponse)
                    .toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_ACTIVE_BY_MEMBER_ID, userId);
        }
    }

    public ProjectResponse getById(Long projectId, Long userId) {
        try {
            ValidationUtils.checkIfNull(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId, userId);

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));

            if (projectRepository.isMember(projectId, userId))
                throw new AccessDeniedException(RETRIEVING_MEMBER_PROJECT_BY_ID, project.getId());

            log.info(LoggerUtils.formatInfo(RETRIEVED, RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));
            return ProjectResponse.toBasicResponse(project);
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId);
        }
    }

    private ProjectResponse save(ProjectRequest projectRequest) {
        try {
            ValidationUtils.checkIfNull(CREATING_PROJECT, projectRequest);

            Project project = projectRequest.toEntity(null);
            projectRepository.save(project);

            log.info(LoggerUtils.formatInfo(CREATED, CREATING_PROJECT, project.getId()));
            return ProjectResponse.toBasicResponse(project);
        } catch (RuntimeException e) {
            throw new ServiceException(CREATING_PROJECT);
        }
    }

    private ProjectResponse update(Long projectId, ProjectRequest projectRequest, Long userId) {
        try {
            ValidationUtils.checkIfNull(UPDATING_PROJECT, projectRequest);

            // if (projectRequest.get)
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));
            Project updatedProject = projectRequest.toEntity(project);
            projectRepository.save(updatedProject);

            log.info(LoggerUtils.formatInfo(UPDATED, UPDATING_PROJECT, updatedProject.getId()));
            return ProjectResponse.toBasicResponse(updatedProject);
        } catch (RuntimeException e) {
            throw new ServiceException(UPDATING_PROJECT);
        }
    }
}
