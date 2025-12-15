package com.projecteams.project_management.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projecteams.project_management.project.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.isArchived = true")
    List<Project> findAllActive();

    List<Project> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query("""
                UPDATE Project p
                SET p.isArchived = true
                WHERE p.id = :projectId
            """)
    void archiveById(@Param("projectId") Long projectId);


}
