package com.example.boot.service;



import com.example.boot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);

    List<User> getUsers();

    User getUser(Long id);
    Optional<User> getUser(String email);

    void removeUser(Long id);

    void updateUser(Long id, User user);


}
