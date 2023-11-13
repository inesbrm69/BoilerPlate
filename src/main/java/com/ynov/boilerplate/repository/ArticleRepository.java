package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, Integer> {
    Article findArticleByIdAndName(int id, String name);

    default boolean doesArticleExist(int id, String name) {
        Article foundArticle = findArticleByIdAndName(id, name);
        return foundArticle != null;
    }
}
