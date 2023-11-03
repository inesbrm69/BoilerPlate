package com.ynov.boilerplate.services;

import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(List<User> user){
        userRepository.saveAll(user);
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
