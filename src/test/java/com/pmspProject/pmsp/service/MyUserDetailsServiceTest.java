package com.pmspProject.pmsp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import com.pmspProject.pmsp.model.Role;
import com.pmspProject.pmsp.model.User;
import com.pmspProject.pmsp.repo.UserRepo;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    private User user;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user = new User();
        user.setPassword("testPassword");
        user.setUsername("testUser");
        user.setRoles(roles);

    }

    @Test
    public void loadUserByUsername_whenUserExists_shouldReturnUserDetails() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("testUser");

        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void loadUserByUsername_whenUserDoesNotExist_shouldThrowUsernameNotFoundException() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(Exception.class, () -> myUserDetailsService.loadUserByUsername("testUser"));
    }
}
