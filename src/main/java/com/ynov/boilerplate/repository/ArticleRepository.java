package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, Integer> {
}
