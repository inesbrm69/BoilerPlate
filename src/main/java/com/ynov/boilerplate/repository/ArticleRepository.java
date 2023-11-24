package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface de repository pour l'entité Article, fournissant des méthodes d'accès aux données dans la base MongoDB.
 */
public interface ArticleRepository extends MongoRepository<Article, Integer> {

    /**
     * Recherche un article par son identifiant et son nom.
     *
     * @param id   L'identifiant de l'article.
     * @param name Le nom de l'article.
     * @return L'article correspondant à l'identifiant et au nom spécifiés.
     */
    Article findArticleByIdAndName(int id, String name);

    /**
     * Recherche un article par son nom.
     *
     * @param name Le nom de l'article.
     * @return L'article correspondant au nom spécifié.
     */
    Article findArticleByName(String name);

    /**
     * Vérifie si un article existe en utilisant son identifiant et son nom.
     *
     * @param id   L'identifiant de l'article.
     * @param name Le nom de l'article.
     * @return true si l'article existe, sinon false.
     */
    default boolean doesArticleExist(int id, String name) {
        Article foundArticle = findArticleByIdAndName(id, name);
        return foundArticle != null;
    }

    /**
     * Vérifie si un article existe en utilisant son nom.
     *
     * @param name Le nom de l'article.
     * @return true si l'article existe, sinon false.
     */
    default boolean doesArticleExistByName(String name) {
        Article foundArticle = findArticleByName(name);
        return foundArticle != null;
    }
}
