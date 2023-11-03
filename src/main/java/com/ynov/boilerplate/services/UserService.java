package com.ynov.boilerplate.services;

import com.ynov.boilerplate.config.autoincrement.SequenceGeneratorService;
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

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        user.setId(sequenceGeneratorService.getNextSequence("user_sequence"));
        return userRepository.save(user);
    }

    public List<User> getAllUser(){return userRepository.findAll();}
    public Optional<User> findUserbyName(int id){
        return userRepository.findById(id);
    }

    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }


}
