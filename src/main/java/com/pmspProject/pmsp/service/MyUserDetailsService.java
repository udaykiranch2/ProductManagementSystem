/**
 * This class represents the MyUserDetailsService, which provides custom user details for Spring Security authentication.
 *
 * The MyUserDetailsService class implements the UserDetailsService interface, which is required by Spring Security for loading user-specific data.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.model.Role;
import com.pmspProject.pmsp.model.User;
import com.pmspProject.pmsp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepository;

    /**
     * Loads the user-specific data by their username.
     *
     * @param username The username of the user to load.
     * @return The loaded user details.
     * @throws UsernameNotFoundException If the user with the given username is not
     *                                   found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = userOptional.get();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Maps the roles of a user to Spring Security authorities.
     *
     * @param roles The roles of the user.
     * @return A collection of granted authorities based on the user's roles.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
