package com.epam.jpop.userms.service;

import com.epam.jpop.userms.model.UserDetails;

import java.util.List;

public interface UserService {

    List<UserDetails> getAllUsers();

    UserDetails getUserById(Long id);

    UserDetails saveUser(UserDetails userDetails);

    UserDetails updateUser(UserDetails userDetails, Long id);

    void deleteUserById(Long id);
}
