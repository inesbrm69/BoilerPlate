package com.ynov.boilerplate.auth;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final ArticleService articleService;
    @PostMapping("/register")
    public ResponseEntity<AuthentificationResponse> register(
            @RequestBody RegisterRequest request
    ){
       return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthentificationResponse> authentificate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/articles")
    public ResponseEntity<? extends Object> getAllArticleWithToken(@RequestHeader("Authorization") String authorizationHeader){
        List<Article> articles = articleService.getAllArticle();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }
    }
}
