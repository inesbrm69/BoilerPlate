package com.ynov.boilerplate.controller;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.services.ArticleService;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des articles.
 */
@RestController
@EnableCaching
@RequestMapping(value = "/api/v1/auth/articles")
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Récupère la liste de tous les articles.
     *
     * @param authorizationHeader Le jeton d'authentification dans l'en-tête de la requête.
     * @return Une ResponseEntity contenant la liste des articles ou un message d'erreur d'authentification.
     */
    @GetMapping()
    @Cacheable("articles")
    public ResponseEntity<? extends Object> getAllArticle(@RequestHeader("Authorization") String authorizationHeader){
        List<Article> articles = articleService.getAllArticle();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Récupère un article en fonction de son identifiant.
     *
     * @param id                 L'identifiant de l'article à récupérer.
     * @param authorizationHeader Le jeton d'authentification dans l'en-tête de la requête.
     * @return Une ResponseEntity contenant l'article trouvé ou un message d'erreur d'authentification.
     */
    @GetMapping("{id}")
    @Cacheable("articlesById")
    public ResponseEntity<? extends Object> articlesById(@PathVariable("id") int id, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" article ");
        Article articles = articleService.findArticlebyId(id);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<Article>(articles, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Récupère un article en fonction de son identifiant et de son nom.
     *
     * @param id                 L'identifiant de l'article à récupérer.
     * @param name               Le nom de l'article à récupérer.
     * @param authorizationHeader Le jeton d'authentification dans l'en-tête de la requête.
     * @return Une ResponseEntity contenant l'article trouvé ou un message d'erreur d'authentification.
     */
    @GetMapping("{id}/{name}")
    @Cacheable("articlesByIdAndName")
    public ResponseEntity<? extends Object> getArticleByIdByName(@PathVariable("id") int id, @PathVariable("name") String name, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" article ");
        Article article = articleService.findArticlebyIdAndName(id, name);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Ajoute un nouvel article.
     *
     * @param article            L'article à ajouter.
     * @param authorizationHeader Le jeton d'authentification dans l'en-tête de la requête.
     * @return Une ResponseEntity indiquant si l'ajout a réussi ou un message d'erreur d'authentification.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable("createArticles")
    public ResponseEntity<? extends Object> addArticle(@RequestBody Article article, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" Création d'articles ");
        articleService.createArticle(article);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<String>("L'article a bien été créé : " + article, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Supprime un article en fonction de son identifiant.
     *
     * @param id                 L'identifiant de l'article à supprimer.
     * @param authorizationHeader Le jeton d'authentification dans l'en-tête de la requête.
     * @return Une ResponseEntity indiquant si la suppression a réussi ou un message d'erreur d'authentification.
     */
    @DeleteMapping("delete/{id:\\d+}")
    @Cacheable("deleteArticles")
    public ResponseEntity<? extends Object> deleteArticle(@PathVariable("id") int id, @RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            articleService.deleteArticleById(id);
            return new ResponseEntity<String>("L'article a bien été supprimé", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("L'article n'a pas été supprimé. Veuillez réessayer !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Met à jour les informations d'un article existant.
     *
     * @param id                 L'identifiant de l'article à mettre à jour.
     * @param article            Les nouvelles informations à utiliser pour la mise à jour.
     * @param authorizationHeader Le jeton d'authentification dans l'en-tête de la requête.
     * @return Une ResponseEntity indiquant si la mise à jour a réussi ou un message d'erreur d'authentification.
     */
    @PutMapping("update/{id:\\d+}")//Put => la modification
    @Cacheable("updateArticles")
    public ResponseEntity<? extends Object> updateArticle(@PathVariable("id") int id, @RequestBody Article article, @RequestHeader("Authorization") String authorizationHeader){
        log.info(" Mise à jour de l'article ");

        articleService.updateArticle(id, article);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<String>("L'article a bien été modifié : ", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Token manquant ou invalide", HttpStatus.UNAUTHORIZED);
        }
    }

}
