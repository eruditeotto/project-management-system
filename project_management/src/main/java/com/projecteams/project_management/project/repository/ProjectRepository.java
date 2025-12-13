package com.projecteams.project_management.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projecteams.project_management.project.Project;
import com.projecteams.project_management.project.dto.response.ProjectResponse;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
                SELECT p
                FROM Project p
                JOIN p.members m
                WHERE m.id = :userId
                  AND p.isArchived = false
            """)
    List<Project> findAllActiveByMember(@Param("userId") Long userId);

    @Query("""
                SELECT p
                FROM Project p
                JOIN p.members m
                WHERE m.id = :userId
                  AND p.isArchived = true
            """)
    List<Project> findAllArchivedByMember(@Param("userId") Long userId);

    @Query("""
                SELECT p
                FROM Project p
                JOIN p.members m
                WHERE m.id = :userId
            """)
    List<Project> findAllByMember(@Param("userId") Long userId);

    @Query(value = """
                SELECT EXISTS(
                    SELECT 1 FROM project_members
                    WHERE project_id = :projectId AND user_id = :userId
                )
            """, nativeQuery = true)
    boolean isMember(Long projectId, Long userId);

}
