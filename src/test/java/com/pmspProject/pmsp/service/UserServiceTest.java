package com.pmspProject.pmsp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmspProject.pmsp.model.Role;
import com.pmspProject.pmsp.model.User;
import com.pmspProject.pmsp.repo.RoleRepository;
import com.pmspProject.pmsp.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindUserByUsername() {
        // Given
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);

        // When
        when(userRepo.findByUsername(username)).thenReturn(mockUser);

        // Then
        User user = userService.findUserByUsername(username);
        assertNotNull(user, "User with username " + username + " should not be null");
    }

    @Test
    public void testSaveUser() {
        // Given
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("newPassword");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        // When
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(userRepo.save(newUser)).thenReturn(newUser);

        // Then
        User savedUser = userService.saveUser(newUser);
        assertNotNull(savedUser, "Saved user should not be null");
    }

    @Test
    public void testSaveAdmin() {
        // Given
        User newAdmin = new User();
        newAdmin.setUsername("newAdmin");
        newAdmin.setPassword("newPassword");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        // When
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(adminRole);
        when(userRepo.save(newAdmin)).thenReturn(newAdmin);

        // Then
        User savedAdmin = userService.saveAdmin(newAdmin);
        assertNotNull(savedAdmin, "Saved admin should not be null");
    }
}