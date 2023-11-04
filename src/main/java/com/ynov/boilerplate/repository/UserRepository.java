package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findUserByIdAndNAndFirstname(int id, String firstname);
}
