package com.ynov.boilerplate.controller;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> articles() {
        log.info(" -----> : tous les utilisateurs ");
        List<User> users = userService.getAllUser();
        log.info(users.toString());
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<? extends Object> articlesById(@PathVariable("id") int id, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" L'utilisateur " + id + " : ");
        User user = userService.findUserbyId(id);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }

    }
    @GetMapping("{id}/{name}")
    public ResponseEntity<? extends Object> getArticleByIdByName(@PathVariable("id") int id, @PathVariable("name") String name, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" L'utilisateur "+ name + " : ");
        User user = userService.findUserByIdAndFirstName(id, name);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }

    }
    @DeleteMapping("delete/{id:\\d+}")
    public ResponseEntity<? extends Object> deleteArticle(@PathVariable("id") int id, @RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            userService.deleteUserById(id);
            return new ResponseEntity<String>("L'utilisateur a bien été supprimé", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("L'utilisateur n'a pas été supprimé. Veuillez réessayer !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("update/{id:\\d+}")//Put => la modification
    public ResponseEntity<? extends Object> updateArticle(@PathVariable("id") int id, @RequestBody User user, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" Mise à jour de l'utilisateur ");

        userService.updateUser(id, user);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<String>("L'utilisateur a bien été modifié ", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }
    }
}
