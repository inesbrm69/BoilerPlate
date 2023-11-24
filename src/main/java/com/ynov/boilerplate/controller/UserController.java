package com.ynov.boilerplate.controller;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur responsable de la gestion des utilisateurs de l'application.
 */
@RestController
@EnableCaching
@RequestMapping(value = "/api/v1/auth/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final UserService userService;

    /**
     * Constructeur du contrôleur UserController.
     *
     * @param userService Le service utilisateur utilisé pour accéder aux fonctionnalités utilisateur.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Une ResponseEntity contenant la liste de tous les utilisateurs ou un message d'erreur.
     */
    @GetMapping()
    @Cacheable("users")
    public ResponseEntity<List<User>> users() {
        log.info(" -----> : tous les utilisateurs ");
        List<User> users = userService.getAllUser();
        log.info(users.toString());
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id                  L'identifiant de l'utilisateur à récupérer.
     * @param authorizationHeader Le jeton d'authentification.
     * @return Une ResponseEntity contenant l'utilisateur demandé ou un message d'erreur.
     */
    @GetMapping("{id}")
    @Cacheable("userById")
    public ResponseEntity<? extends Object> userById(@PathVariable("id") int id, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" L'utilisateur " + id + " : ");
        User user = userService.findUserbyId(id);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Récupère un utilisateur par son identifiant et son prénom.
     *
     * @param id                  L'identifiant de l'utilisateur à récupérer.
     * @param name                Le prénom de l'utilisateur à récupérer.
     * @param authorizationHeader Le jeton d'authentification.
     * @return Une ResponseEntity contenant l'utilisateur demandé ou un message d'erreur.
     */
    @GetMapping("{id}/{name}")
    @Cacheable("userByIdAndName")
    public ResponseEntity<? extends Object> getUserByIdByName(@PathVariable("id") int id, @PathVariable("name") String name, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" L'utilisateur "+ name + " : ");
        User user = userService.findUserByIdAndFirstName(id, name);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id                  L'identifiant de l'utilisateur à supprimer.
     * @param authorizationHeader Le jeton d'authentification.
     * @return Une ResponseEntity indiquant si la suppression a réussi ou un message d'erreur.
     */
    @DeleteMapping("delete/{id:\\d+}")
    public ResponseEntity<? extends Object> deleteUser(@PathVariable("id") int id, @RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            userService.deleteUserById(id);
            return new ResponseEntity<String>("L'utilisateur a bien été supprimé", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("L'utilisateur n'a pas été supprimé. Veuillez réessayer !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Met à jour un utilisateur par son identifiant.
     *
     * @param id                  L'identifiant de l'utilisateur à mettre à jour.
     * @param user                Les nouvelles informations de l'utilisateur.
     * @param authorizationHeader Le jeton d'authentification.
     * @return Une ResponseEntity indiquant si la mise à jour a réussi ou un message d'erreur.
     */
    @PutMapping("update/{id:\\d+}")//Put => la modification
    public ResponseEntity<? extends Object> updateUser(@PathVariable("id") int id, @RequestBody User user, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" Mise à jour de l'utilisateur ");

        userService.updateUser(id, user);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<String>("L'utilisateur a bien été modifié ", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }
    }
}
