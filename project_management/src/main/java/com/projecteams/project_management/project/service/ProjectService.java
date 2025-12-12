package com.projecteams.project_management.project.service;

import static com.projecteams.project_management.common.constant.CommonMessages.CREATE;
import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVE;
import static com.projecteams.project_management.common.constant.CommonMessages.UPDATE;
import static com.projecteams.project_management.project.constant.ProjectMessages.ALREADY_A_PROJECT_MEMBER;
import static com.projecteams.project_management.project.constant.ProjectMessages.CREATING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.PROJECT_NOT_FOUND;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_CREATOR_PROJECT_BY_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_MEMBER_PROJECT_BY_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.SAVING_MEMBER;
import static com.projecteams.project_management.project.constant.ProjectMessages.UPDATING_PROJECT;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.USER_NOT_FOUND;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.AccessDeniedException;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.project.dto.request.ProjectRequest;
import com.projecteams.project_management.project.dto.response.ProjectResponse;
import com.projecteams.project_management.project.repository.ProjectRepository;
import com.projecteams.project_management.user.User;
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
            List<Project> projects = projectRepository.findAll();

            if (projects.isEmpty()) {
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_ALL_PROJECT, null);
            }

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_ALL_PROJECT, null));
            return projects.stream().map(ProjectResponse::toResponse).toList();

        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_PROJECT, e);
        }
    }

    public List<ProjectResponse> getAllByMemberId(Long userId) {
        try {
            List<Project> projects = projectRepository.findAllByMember(userId);

            if (projects.isEmpty()) {
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, userId);
            }

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, userId));
            return projects.stream().map(ProjectResponse::toResponse).toList();

        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, e);
        }
    }

    public List<ProjectResponse> getAllActiveByMemberId(Long userId) {
        try {
            List<Project> projects = projectRepository.findAllActiveByMember(userId);

            if (projects.isEmpty()) {
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID, userId);
            }

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID, userId));
            return projects.stream().map(ProjectResponse::toResponse).toList();

        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_ACTIVE_PROJECT_BY_MEMBER_ID, e);
        }
    }

    public ProjectResponse getById(Long projectId, Long userId) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_MEMBER_PROJECT_BY_ID,
                            projectId));

            if (!projectRepository.isMember(projectId, userId)) {
                throw new AccessDeniedException(RETRIEVING_MEMBER_PROJECT_BY_ID, project.getId());
            }

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));
            return ProjectResponse.toBasicResponse(project);

        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_MEMBER_PROJECT_BY_ID, e);
        }
    }

    public void save(ProjectRequest projectRequest) {
        try {
            Project project = projectRequest.toEntity(null);
            project.getMembers().add(project.getCreator());

            projectRepository.save(project);

            log.info(LoggerUtils.formatSuccess(CREATE, CREATING_PROJECT, project.getId()));

        } catch (RuntimeException e) {
            throw new ServiceException(CREATING_PROJECT, e);
        }
    }

    public void update(Long userId, Long projectId, ProjectRequest projectRequest) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_MEMBER_PROJECT_BY_ID,
                            projectId));

            if (!Objects.equals(project.getCreator().getId(), userId)) {
                throw new AccessDeniedException(RETRIEVING_MEMBER_PROJECT_BY_ID, project.getId());
            }

            projectRepository.save(projectRequest.toEntity(project));

            log.info(LoggerUtils.formatSuccess(UPDATE, UPDATING_PROJECT, projectId));

        } catch (RuntimeException e) {
            throw new ServiceException(UPDATING_PROJECT, e);
        }
    }

    public void addMember(Long projectId, Long userId, Long newMemberId) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_CREATOR_PROJECT_BY_ID,
                            projectId));

            if (!Objects.equals(project.getCreator().getId(), userId)) {
                throw new AccessDeniedException(RETRIEVING_MEMBER_PROJECT_BY_ID, projectId);
            }

            User newMember = userRepository.findById(newMemberId)
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + RETRIEVING_USER, projectId));

            if (projectRepository.isMember(projectId, newMemberId)) {
                throw new AccessDeniedException(ALREADY_A_PROJECT_MEMBER, project.getId());
            }

            project.getMembers().add(newMember);
            projectRepository.save(project);

            log.info(LoggerUtils.formatSuccess(UPDATE, SAVING_MEMBER, projectId));

        } catch (RuntimeException e) {
            throw new ServiceException(SAVING_MEMBER, e);
        }
    }
}
