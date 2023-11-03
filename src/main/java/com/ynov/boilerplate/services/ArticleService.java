package com.ynov.boilerplate.services;

import com.ynov.boilerplate.controller.ArticleController;
import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(List<Article> art){
        articleRepository.saveAll(art);
    }

    public List<Article> getAllArticle() {
        List<Article> articles = articleRepository.findAll();
        log.info("Récupération de tous les articles : " + articles);
        return articles;
    }
    public Optional<Article> findArticlebyId(int id){
        return articleRepository.findById(id);
    }

    public void deleteArticleById(int id){
        articleRepository.deleteById(id);
    }

    public void updateArticle(Article art){
        articleRepository.save(art);
    }
}
