package com.ynov.boilerplate.services;

import com.ynov.boilerplate.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }
}
