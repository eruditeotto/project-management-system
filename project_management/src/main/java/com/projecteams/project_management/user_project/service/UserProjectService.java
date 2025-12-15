package com.projecteams.project_management.user_project.service;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.exception.ServiceException;
import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.project.dto.response.ProjectResponse;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user_project.UserProject;
import com.projecteams.project_management.user_project.dto.response.UserProjectResponse;
import com.projecteams.project_management.user_project.repository.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.projecteams.project_management.common.constant.CommonMessages.ADD;
import static com.projecteams.project_management.common.constant.CommonMessages.RETRIEVE;
import static com.projecteams.project_management.user_project.constant.UserProjectMessages.ADDING_NEW_MEMBERS;
import static com.projecteams.project_management.user_project.constant.UserProjectMessages.RETRIEVING_MEMBERS;
import static com.projecteams.project_management.user_project.constant.UserProjectMessages.RETRIEVING_PROJECT_IDS;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserProjectService {
    private final UserProjectRepository userProjectRepository;

    @Transactional
    public List<Long> getAllProjectByMemberId(Long memberId) {
        try {
            List<UserProject> userProjects = userProjectRepository.findAllByUser_Id(memberId);

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_PROJECT_IDS, memberId));
            return userProjects.stream()
                    .map(p -> p.getProject().getId())
                    .toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_PROJECT_IDS, e);
        }
    }

    @Transactional
    public List<Long> getMembersFromProjectId(Long projectId) {
        try {
            List<UserProject> members = userProjectRepository.findAllByProject_Id(projectId);

            log.info(LoggerUtils.formatSuccess(RETRIEVE, RETRIEVING_MEMBERS));
            return members.stream()
                    .map(m -> m.getUser().getId())
                    .toList();
        } catch (RuntimeException e) {
            throw new ServiceException(RETRIEVING_MEMBERS, e);
        }
    }

    @Transactional
    public void addMembers(List<UserRequest> memberRequests, Project project) {
        try {
            List<UserProject> userProjects = memberRequests.stream()
                    .map(user -> new UserProject(user.toEntity(null), project))
                    .collect(Collectors.toList());

            userProjectRepository.saveAll(userProjects);

            log.info(LoggerUtils.formatSuccess(ADD, ADDING_NEW_MEMBERS));
        } catch (RuntimeException e) {
            throw new ServiceException(ADDING_NEW_MEMBERS, e);
        }
    }
}

