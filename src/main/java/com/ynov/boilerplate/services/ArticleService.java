package com.ynov.boilerplate.services;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createUser(List<Article> art){
        articleRepository.saveAll(art);
    }

    public List<Article> getAllArticle(){
        /*List<Article> articles = new ArrayList<>();
        articles.add(new Article(1,"Chocolat", 25));
        articles.add(new Article(2,"Pantalon", 45));
        articles.add(new Article(3,"Chemise", 100));
        return articles;*/
        return articleRepository.findAll();
    }
    public Optional<Article> findUserbyId(int id){
        return articleRepository.findById(id);
    }

    public void deleteUserById(int id){
        articleRepository.deleteById(id);
    }

    public void updateUser(Article art){
        articleRepository.save(art);
    }
}
