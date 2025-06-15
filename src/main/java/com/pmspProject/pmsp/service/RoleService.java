package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO updateRole(Long id, RoleDTO roleDTO);

    void deleteRole(Long id);

    RoleDTO getRoleById(Long id);

    RoleDTO getRoleByName(String name);

    Page<RoleDTO> getAllRoles(Pageable pageable);

    List<RoleDTO> getAllRoles();
}