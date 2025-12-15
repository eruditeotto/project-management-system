package com.projecteams.project_management.project.service;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.NotFoundException;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.project.dto.request.ProjectRequest;
import com.projecteams.project_management.project.dto.response.ProjectResponse;
import com.projecteams.project_management.project.repository.ProjectRepository;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.dto.response.UserResponse;
import com.projecteams.project_management.user.repository.UserRepository;
import com.projecteams.project_management.user_project.service.UserProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.projecteams.project_management.common.constant.CommonMessages.CREATE;
import static com.projecteams.project_management.common.constant.CommonMessages.DELETE;
import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVE;
import static com.projecteams.project_management.common.constant.CommonMessages.UPDATE;
import static com.projecteams.project_management.project.constant.ProjectMessages.CREATING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.DELETING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.PROJECT_NOT_FOUND;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT_BY_MEMBER_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_MEMBER_PROJECT_BY_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.UPDATING_PROJECT;
import static com.projecteams.project_management.user.constant.UserMessages.RETRIEVING_USER;
import static com.projecteams.project_management.user.constant.UserMessages.USER_NOT_FOUND;
import static com.projecteams.project_management.user_project.constant.UserProjectMessages.RETRIEVING_MEMBERS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final UserProjectService userProjectService;

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAll() {
        try {
            List<Project> projects = projectRepository.findAllActive();

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_ALL_PROJECT, null));
            return projects.stream().map(ProjectResponse::toResponse).toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_PROJECT, e);
        }
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllByMemberId(Long memberId) {
        try {
            if (!userRepository.existsById(memberId))
                throw new NotFoundException(USER_NOT_FOUND + RETRIEVING_USER, memberId);

            List<Long> projectIds = userProjectService.getAllProjectByMemberId(memberId);

            List<Project> projects = projectRepository.findAllByIdIn(projectIds);

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, memberId));
            return projects.stream().map(ProjectResponse::toResponse).toList();
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_ALL_PROJECT_BY_MEMBER_ID, e);
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getMembersByProjectId(Long projectId) {
        try {
            if (!projectRepository.existsById(projectId))
                throw new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_MEMBER_PROJECT_BY_ID, projectId);

            List<Long> memberIds = userProjectService.getMembersFromProjectId(projectId);

            List<User> members = userRepository.findAllByIdIn(memberIds);

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));
            return members.stream().map(UserResponse::toResponse).toList();
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_MEMBERS, e);
        }
    }

    @Transactional(readOnly = true)
    public ProjectResponse getById(Long projectId) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_MEMBER_PROJECT_BY_ID,
                            projectId));

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_MEMBER_PROJECT_BY_ID, projectId));
            return ProjectResponse.toBasicResponse(project);
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_MEMBER_PROJECT_BY_ID, e);
        }
    }

    @Transactional
    public void save(ProjectRequest projectRequest, List<UserRequest> memberRequests) {
        try {
            Project project = projectRequest.toEntity(null);

            Project savedProject = projectRepository.save(project);

            userProjectService.addMembers(memberRequests, savedProject);

            log.info(LoggerUtils.formatSuccess(CREATE, CREATING_PROJECT, savedProject.getId()));
        } catch (RuntimeException e) {
            throw new ServiceException(CREATING_PROJECT, e);
        }
    }

    @Transactional
    public void update(Long projectId, ProjectRequest projectRequest) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_MEMBER_PROJECT_BY_ID,
                            projectId));

            projectRepository.save(projectRequest.toEntity(project));

            log.info(LoggerUtils.formatSuccess(UPDATE, UPDATING_PROJECT, projectId));
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(UPDATING_PROJECT, e);
        }
    }

    @Transactional
    public void delete(Long projectId) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND + RETRIEVING_MEMBER_PROJECT_BY_ID,
                            projectId));

            projectRepository.archiveById(project.getId());

            log.info(LoggerUtils.formatSuccess(DELETE, DELETING_PROJECT, projectId));
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException(DELETING_PROJECT, e);
        }
    }
}
