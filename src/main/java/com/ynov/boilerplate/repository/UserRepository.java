package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repository pour l'entité User, fournissant des méthodes d'accès aux données dans la base MongoDB.
 */
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    /**
     * Recherche un utilisateur par son adresse e-mail.
     *
     * @param email L'adresse e-mail de l'utilisateur.
     * @return Un objet Optionnel contenant l'utilisateur correspondant à l'adresse e-mail spécifiée.
     */
    Optional<User> findByEmail(String email);

    /**
     * Recherche un utilisateur par son identifiant et son prénom.
     *
     * @param id        L'identifiant de l'utilisateur.
     * @param firstname Le prénom de l'utilisateur.
     * @return L'utilisateur correspondant à l'identifiant et au prénom spécifiés.
     */
    User findUserByIdAndFirstname(int id, String firstname);
}
