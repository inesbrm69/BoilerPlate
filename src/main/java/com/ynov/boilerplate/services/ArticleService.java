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
    private final SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public ArticleService(ArticleRepository articleRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.articleRepository = articleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    /**
     * Crée un nouvel article en lui attribuant un identifiant unique généré automatiquement.
     *
     * @param art L'article à créer.
     * @return L'article créé avec l'identifiant unique attribué.
     */
    public Article createArticle(Article art){
        art.setId(sequenceGeneratorService.getNextSequence("article_sequence"));
        return articleRepository.save(art);
    }

    /**
     * Récupère la liste de tous les articles.
     *
     * @return La liste de tous les articles présents dans la base de données.
     */
    public List<Article> getAllArticle() {
        List<Article> articles = articleRepository.findAll();
        log.info("Récupération de tous les articles : " + articles);
        return articles;
    }

    /**
     * Recherche un article par son identifiant.
     *
     * @param id L'identifiant de l'article à rechercher.
     * @return L'article correspondant à l'identifiant spécifié, ou {@code null} si aucun article n'est trouvé.
     */
    public Article findArticlebyId(int id) {
        return articleRepository.findById(id).orElse(null);
    }

    /**
     * Recherche un article par son identifiant et son nom.
     *
     * @param id   L'identifiant de l'article à rechercher.
     * @param name Le nom de l'article à rechercher.
     * @return L'article correspondant à l'identifiant et au nom spécifiés.
     */
    public Article findArticlebyIdAndName(int id, String name) {
        return articleRepository.findArticleByIdAndName(id, name);
    }

    /**
     * Supprime un article en fonction de son identifiant.
     *
     * @param id L'identifiant de l'article à supprimer.
     */
    public void deleteArticleById(int id){
        articleRepository.deleteById(id);
    }

    /**
     * Met à jour les informations d'un article existant.
     *
     * @param id             L'identifiant de l'article à mettre à jour.
     * @param updatedArticle Les nouvelles informations à utiliser pour la mise à jour.
     * @return L'article mis à jour, ou l'article original si aucun article correspondant n'est trouvé.
     */
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
