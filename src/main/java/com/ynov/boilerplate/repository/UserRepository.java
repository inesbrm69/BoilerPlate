package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
}
