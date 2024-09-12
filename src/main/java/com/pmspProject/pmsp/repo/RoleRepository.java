package com.pmspProject.pmsp.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.pmspProject.pmsp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

