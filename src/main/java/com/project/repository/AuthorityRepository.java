package com.project.repository;

import com.project.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    List<Authority> findAllByName(String name);
}
