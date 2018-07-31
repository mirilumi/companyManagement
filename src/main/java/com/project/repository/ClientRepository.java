package com.project.repository;

import com.project.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client,Long>,JpaSpecificationExecutor<Client> {

//    Page<Client> findAllByCon
}
