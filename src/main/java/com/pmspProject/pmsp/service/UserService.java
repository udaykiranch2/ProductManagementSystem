package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.model.Role;
import com.pmspProject.pmsp.model.User;
import com.pmspProject.pmsp.repo.RoleRepository;
import com.pmspProject.pmsp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepo repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    private Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public User saveUser(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = findRoleByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        return repo.save(user);
    }

    public User saveAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = findRoleByName("ROLE_USER");
        Role adminRole = findRoleByName("ROLE_ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        user.setRoles(roles);
        return repo.save(user);
    }

}
