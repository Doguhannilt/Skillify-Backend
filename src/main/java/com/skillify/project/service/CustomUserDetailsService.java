package com.skillify.project.service;

import com.skillify.project.dto.UserDTO;
import com.skillify.project.model.Role;
import com.skillify.project.model.User;
import com.skillify.project.repository.UserRepository;
import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public CustomUserDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                getAuthorities(user.get().getRole())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public ResponseEntity<String> createUser(User user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Optional<User> isNameAvailable = userRepository.findByName(user.getName());
        if(isNameAvailable.isPresent()) {
            return ResponseEntity.badRequest().body("Name is not available");
        }

        Boolean isEmailAvailable = userRepository.existsByEmail(user.getEmail());
        if(isEmailAvailable) {
            return ResponseEntity.badRequest().body("Email has been taken");
        }

        User savedUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
        return ResponseEntity.ok(userDTO.toString());
    }

}
