package com.pmspProject.pmsp.controller;


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

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup/user")
    public User register(@Valid @RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/signup/admin")
    public User adminRegister(@Valid @RequestBody User user) {
        return service.saveAdmin(user);
    }


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
