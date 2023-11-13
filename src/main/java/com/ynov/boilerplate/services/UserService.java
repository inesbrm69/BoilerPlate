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
    public User findUserbyId(int id){
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByIdAndFirstName(int id, String firstname) {
        return userRepository.findUserByIdAndFirstname(id, firstname);
    }
    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }

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
