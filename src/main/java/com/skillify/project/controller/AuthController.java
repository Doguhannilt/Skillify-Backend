package com.skillify.project.controller;

import com.skillify.project.config.JwtTokenUtil;
import com.skillify.project.model.Role;
import com.skillify.project.model.User;
import com.skillify.project.repository.UserRepository;
import com.skillify.project.request.SignupRequest;
import com.skillify.project.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(PasswordEncoder passwordEncoder, LoginRequest loginRequest, UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return jwtTokenUtil.generateToken(username);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));  // Şifreyi encode et
        user.setRole(Role.valueOf(signupRequest.getRole()));  // Role enum'ını al

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}