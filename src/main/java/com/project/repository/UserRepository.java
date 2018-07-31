package com.project.repository;

import com.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
    User findByUsername( String username );
}

