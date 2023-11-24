package com.ynov.boilerplate.services;

import com.ynov.boilerplate.config.autoincrement.SequenceGeneratorService;
import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public UserService(UserRepository userRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    /**
     * Crée un nouvel utilisateur en lui attribuant un identifiant unique généré automatiquement.
     *
     * @param user L'utilisateur à créer.
     * @return L'utilisateur créé avec l'identifiant unique attribué.
     */
    public User createUser(User user) {
        user.setId(sequenceGeneratorService.getNextSequence("user_sequence"));
        return userRepository.save(user);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs présents dans la base de données.
     */
    public List<User> getAllUser(){return userRepository.findAll();}

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant à l'identifiant spécifié, ou {@code null} si aucun utilisateur n'est trouvé.
     */
    public User findUserbyId(int id){
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Recherche un utilisateur par son identifiant et son prénom.
     *
     * @param id        L'identifiant de l'utilisateur à rechercher.
     * @param firstname Le prénom de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant à l'identifiant et au prénom spécifiés.
     */
    public User findUserByIdAndFirstName(int id, String firstname) {
        return userRepository.findUserByIdAndFirstname(id, firstname);
    }

    /**
     * Supprime un utilisateur en fonction de son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     */
    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }

    /**
     * Met à jour les informations d'un utilisateur existant.
     *
     * @param id           L'identifiant de l'utilisateur à mettre à jour.
     * @param updatedUser  Les nouvelles informations à utiliser pour la mise à jour.
     * @return L'utilisateur mis à jour, ou l'utilisateur original si aucun utilisateur correspondant n'est trouvé.
     */
    public User updateUser(int id, User updatedUser){
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setFirstname(updatedUser.getFirstname());
            user.setLastname(updatedUser.getLastname());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRoles(updatedUser.getRoles());

            userRepository.save(user);

            return user;
        }else{
            return updatedUser;
        }
    }
}