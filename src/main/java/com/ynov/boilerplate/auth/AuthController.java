package com.ynov.boilerplate.auth;

import com.ynov.boilerplate.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST responsable de la gestion de l'authentification.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final ArticleService articleService;

    /**
     * Enregistre un nouvel utilisateur avec les informations fournies dans la requête.
     *
     * @param request Les informations d'enregistrement de l'utilisateur.
     * @return Une ResponseEntity contenant un message indiquant le succès de l'enregistrement.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ){
       return ResponseEntity.ok(service.register(request));
    }

    /**
     * Authentifie un utilisateur en utilisant les informations fournies dans la requête.
     *
     * @param request Les informations d'authentification de l'utilisateur.
     * @return Une ResponseEntity contenant les informations d'authentification réussie.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthentificationResponse> authentificate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
