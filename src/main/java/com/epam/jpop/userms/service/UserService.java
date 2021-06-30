package com.epam.jpop.userms.service;

import com.epam.jpop.userms.model.UserDetail;

import java.util.List;

public interface UserService {

    List<UserDetail> getAllUsers();

    UserDetail getUserById(Long id);

    UserDetail saveUser(UserDetail userDetail);

    UserDetail updateUser(UserDetail userDetail, Long id);

    void deleteUserById(Long id);
}
