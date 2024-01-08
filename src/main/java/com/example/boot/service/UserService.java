package com.example.boot.service;


import com.example.boot.model.User;
import com.example.boot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return  userRepository.findById(id);

    }
    public Optional<User> getUser(String email) {
       return userRepository.findByEmail(email).stream().findAny();

    }
    @Transactional
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }
}
