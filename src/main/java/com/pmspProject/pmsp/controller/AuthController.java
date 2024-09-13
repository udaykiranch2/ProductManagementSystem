package com.pmspProject.pmsp.controller;

/**
 * AuthController is a REST controller for handling authentication-related requests in the PMSP application.
 * @author uday
 * @since 1.0.0
 */

import com.pmspProject.pmsp.model.User;
import com.pmspProject.pmsp.service.JwtService;
import com.pmspProject.pmsp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    /**
     * Autowired UserService for user registration and admin registration.
     */
    @Autowired
    private UserService service;
    /**
     * Autowired AuthenticationManager for managing user authentication.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Autowired JwtService for generating and validating JWT tokens.
     */
    @Autowired
    private JwtService jwtService;

    /**
     * POST request for user registration.
     *
     * @param user The user object containing the user's details.
     * @return The saved user object.
     */
    @PostMapping("/signup/user")
    public User register(@Valid @RequestBody User user) {
        return service.saveUser(user);
    }

    /**
     * POST request for admin registration.
     *
     * @param user The user object containing the admin's details.
     * @return The saved admin object.
     */
    @PostMapping("/signup/admin")
    public User adminRegister(@Valid @RequestBody User user) {
        return service.saveAdmin(user);
    }

    /**
     * POST request for user login.
     *
     * @param user The user object containing the username and password.
     * @return A JWT token if the login is successful, or "failure" if the login
     *         fails.
     */

    @PostMapping("/login")
    public String login(@Valid @RequestBody User user) {
        System.out.println("inside auth ");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()));
        System.out.println("authenticated in auth");
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "failure";
        }
    }

}
