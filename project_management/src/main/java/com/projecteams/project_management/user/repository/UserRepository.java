package com.projecteams.project_management.user.repository;

import com.projecteams.project_management.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :userId")
    void deactivateUserById(Long userId); 
}
