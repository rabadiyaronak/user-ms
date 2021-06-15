package com.epam.jpop.userms.service.impl;

import com.epam.jpop.userms.model.UserDetails;
import com.epam.jpop.userms.service.UserService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDetails> getAllUsers() {
        return null;
    }

    @Override
    public UserDetails getUserById(Long id) {
        return null;
    }

    @Override
    public UserDetails saveUser(UserDetails userDetails) {
        return null;
    }

    @Override
    public UserDetails updateUser(UserDetails userDetails, Long id) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }
}
