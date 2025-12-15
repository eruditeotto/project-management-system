package com.projecteams.project_management.user_project.repository;

import com.projecteams.project_management.user_project.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    List<UserProject> findAllByProject_Id(Long projectId);
    List<UserProject> findAllByUser_Id(Long projectId);
}
