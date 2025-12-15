package com.projecteams.project_management.project.controller;

import com.projecteams.project_management.common.util.LoggerUtils;
import com.projecteams.project_management.common.util.ResponseUtils;
import com.projecteams.project_management.project.dto.request.ProjectRequest;
import com.projecteams.project_management.project.dto.response.ProjectResponse;
import com.projecteams.project_management.project.service.ProjectService;
import com.projecteams.project_management.user.User;
import com.projecteams.project_management.user.dto.request.UserRequest;
import com.projecteams.project_management.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.projecteams.project_management.project.constant.ProjectMessages.CREATING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.DELETING_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_MEMBER_PROJECT_BY_ID;
import static com.projecteams.project_management.project.constant.ProjectMessages.UPDATING_PROJECT;
import static org.springframework.http.HttpStatus.OK;
import static com.projecteams.project_management.common.constant.CommonMessages.PROCESSING;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT;
import static com.projecteams.project_management.project.constant.ProjectMessages.RETRIEVING_ALL_PROJECT_BY_MEMBER_ID;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api/projects")
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        log.info(LoggerUtils.formatProcess(PROCESSING, RETRIEVING_ALL_PROJECT));
        List<ProjectResponse> projects = projectService.getAll();

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_ALL_PROJECT,
                projects
        ));
    }

    @GetMapping("member/{id}")
    public ResponseEntity<?> getAllByMemberId(@PathVariable("id") Long id) {
        log.info(LoggerUtils.formatProcess(PROCESSING, RETRIEVING_ALL_PROJECT_BY_MEMBER_ID));
        List<ProjectResponse> projects = projectService.getAllByMemberId(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_ALL_PROJECT_BY_MEMBER_ID,
                projects
        ));
    }

    @GetMapping("{id}/members")
    public ResponseEntity<?> getMembersByProjectId(@PathVariable("id") Long id) {
        log.info(LoggerUtils.formatProcess(PROCESSING, RETRIEVING_MEMBER_PROJECT_BY_ID));
        List<UserResponse> members = projectService.getMembersByProjectId(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_MEMBER_PROJECT_BY_ID,
                members
        ));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.info(LoggerUtils.formatProcess(PROCESSING, RETRIEVING_MEMBER_PROJECT_BY_ID));
        ProjectResponse project = projectService.getById(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                RETRIEVING_MEMBER_PROJECT_BY_ID,
                project
        ));
    }

    @PostMapping
    public ResponseEntity<?> createProject(
            @Valid @RequestBody ProjectRequest projectRequest,
            @RequestBody(required = false) List<UserRequest> members
    ) {
        log.info(LoggerUtils.formatProcess(PROCESSING, CREATING_PROJECT));
        projectService.save(projectRequest, members != null ? members : new ArrayList<>());

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                CREATING_PROJECT
        ));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProjectRequest projectRequest
    ) {
        log.info(LoggerUtils.formatProcess(PROCESSING, UPDATING_PROJECT));
        projectService.update(id, projectRequest);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                UPDATING_PROJECT
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
        log.info(LoggerUtils.formatProcess(PROCESSING, DELETING_PROJECT));
        projectService.delete(id);

        return ResponseEntity.ok(ResponseUtils.buildSuccessResponse(
                OK,
                DELETING_PROJECT
        ));
    }
}
