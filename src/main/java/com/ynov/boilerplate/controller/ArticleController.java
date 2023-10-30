package com.ynov.boilerplate.controller;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.services.ArticleService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<Article>> articles(){
        log.info(" -----> : tous les articles ");
        List<Article> articles = articleService.getAllArticle();
        log.info(articles.toString());
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }
    /*@GetMapping("{id}")
    public ResponseEntity<Article> articles(@PathVariable("id") int id){
        log.info(" article ");
        Article art = articles.stream().filter(article -> id == article.getId()).findAny().orElse(null);
        return new ResponseEntity<Article>(art, HttpStatus.OK);
    }
    @GetMapping("{id}/{name}")
    public ResponseEntity<Article> getArticleByIdByName(@PathVariable("id") int id, @PathVariable("name") String name){
        log.info(" article ");
        Article art = articles.stream().filter(article -> id == article.getId() && name.equals(article.getNom()))
                .findAny()
                .orElse(null);
        return new ResponseEntity<Article>(art, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id:\\d+}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") int id) {
        // Vérifiez si l'ID est dans la plage valide des indices de la liste
        if (id < 0 || id >= articles.size()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Supprimez l'article de la liste (ou de la base de données, si utilisée)
        Article articleToDelete = articles.remove(id);

        if (articleToDelete != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Ou un autre code d'erreur approprié
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> addArticle(@RequestBody Article article){
        log.info(" list article ");
        article.setId((int)new Date().getTime());
        articles.add(article);

        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//Put => la modification
    public ResponseEntity<Article> updateArticle(@RequestBody Article article){ //RequestBody => dans le body
        log.info(" list article ");

        article.setId((int)new Date().getTime());
        articles.add(article);

        Article art = articles.stream().filter(myArticle -> article.getId() == myArticle.getId())
                .findAny()
                .orElse(null);

        if(art!=null){
            art.setNom(article.getNom());
        }

        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }*/
}
