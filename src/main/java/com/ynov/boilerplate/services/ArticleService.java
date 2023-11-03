package com.ynov.boilerplate.services;

import com.ynov.boilerplate.config.autoincrement.SequenceGeneratorService;
import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
    @Autowired
    private final SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public ArticleService(ArticleRepository articleRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.articleRepository = articleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    public Article createArticle(Article art){
        art.setId(sequenceGeneratorService.getNextSequence("article_sequence"));
        return articleRepository.save(art);
    }

    public List<Article> getAllArticle() {
        List<Article> articles = articleRepository.findAll();
        log.info("Récupération de tous les articles : " + articles);
        return articles;
    }
    public Article findArticlebyId(int id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article findArticlebyIdAndName(int id, String name) {
        return articleRepository.findArticleByIdAndName(id, name);
    }


    public void deleteArticleById(int id){
        articleRepository.deleteById(id);
    }

    public Article updateArticle(int id, Article updatedArticle){
        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            // Mettez à jour les propriétés de l'article avec les nouvelles valeurs de updatedArticle
            article.setName(updatedArticle.getName());
            article.setPrice(updatedArticle.getPrice());
            // Mettez à jour d'autres propriétés selon vos besoins

            // Enregistrez les modifications dans la base de données
            articleRepository.save(article);

            return article; // Renvoie l'article mis à jour
        }else{
            return updatedArticle;
        }
    }
}
