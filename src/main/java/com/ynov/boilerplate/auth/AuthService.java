package com.ynov.boilerplate.auth;

import com.ynov.boilerplate.config.JwtService;
import com.ynov.boilerplate.entity.Enum.Roles;
import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.repository.UserRepository;
import com.ynov.boilerplate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsable de la gestion de l'authentification des utilisateurs.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    /**
     * Enregistre un nouvel utilisateur avec les informations fournies dans la requête.
     *
     * @param request Les informations d'enregistrement de l'utilisateur.
     * @return Un message indiquant le succès de l'enregistrement de l'utilisateur.
     */
    public String register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Roles.USER)
                .build();

        service.createUser(user);

        return "L'utilisateur a été sauvegardé avec succès.";
    }

    /**
     * Authentifie un utilisateur en utilisant les informations fournies dans la requête.
     *
     * @param request Les informations d'authentification de l'utilisateur.
     * @return Les informations d'authentification réussie, y compris le jeton JWT généré.
     */
    public AuthentificationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthentificationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
