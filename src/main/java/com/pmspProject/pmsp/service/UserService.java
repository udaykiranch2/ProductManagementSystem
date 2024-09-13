/**
 * This class provides services related to user management.
 * It includes methods for finding users by username, saving regular users, and
 * saving administrators.
 *
 * @author uday
 * @since 1.0
 */
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

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return the user with the given username, or null if not found
     */
    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    /**
     * Finds a role by its name.
     *
     * @param roleName the name of the role to search for
     * @return the role with the given name, or null if not found
     */
    private Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    /**
     * Saves a new user with a user role.
     *
     * @param user the user to save
     * @return the saved user
     */
    public User saveUser(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = findRoleByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        return repo.save(user);
    }

    /**
     * Saves a new user with both user and admin roles.
     *
     * @param user the user to save
     * @return the saved user
     */
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
