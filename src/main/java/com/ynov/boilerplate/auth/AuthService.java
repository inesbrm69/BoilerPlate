package com.ynov.boilerplate.auth;

import com.ynov.boilerplate.config.JwtService;
import com.ynov.boilerplate.entity.Enum.Roles;
import com.ynov.boilerplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthentificationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.get
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthentificationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthentificationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
