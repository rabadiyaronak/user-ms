package com.epam.jpop.userms.service.impl;

import com.epam.jpop.userms.entity.User;
import com.epam.jpop.userms.exception.UserAlreadyExists;
import com.epam.jpop.userms.exception.UserNotFound;
import com.epam.jpop.userms.model.UserDetail;
import com.epam.jpop.userms.repository.UserRepository;
import com.epam.jpop.userms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDetail> getAllUsers() {
        var users = userRepository.findAll();

        return users.stream()
                .map(user -> UserDetail.builder()
                        .email(user.getEmail())
                        .id(user.getId())
                        .name(user.getName())
                        .isActive(user.getIsActive())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public UserDetail getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        var user = userOptional.orElseThrow(() -> new UserNotFound("User not found with id - " + id));
        return UserDetail.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isActive(user.getIsActive()).build();
    }

    @Override
    public UserDetail saveUser(UserDetail userDetail) {

        var existingUser = userRepository.findByEmail(userDetail.getEmail());

        existingUser.ifPresent(user -> {
            throw new UserAlreadyExists("User with given email  is already exists");
        });

        var user = User.builder()
                .email(userDetail.getEmail())
                .name(userDetail.getName())
                .isActive(userDetail.getIsActive()).build();

        var savedUser = userRepository.save(user);
        return UserDetail.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .isActive(savedUser.getIsActive()).build();
    }

    @Override
    public UserDetail updateUser(UserDetail userDetail, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        var userFromDb =  userOptional.orElseThrow(() -> new UserNotFound("User not found with id - " + id));
        var user = User.builder()
                .id(id)
                .name(userDetail.getName())
                .email(userFromDb.getEmail())
                .isActive(userDetail.getIsActive()).build();
        var updatedUser = userRepository.save(user);
        return UserDetail.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .isActive(updatedUser.getIsActive()).build();
    }

    @Override
    public void deleteUserById(Long id) {
        boolean isExists = userRepository.existsById(id);
        if (isExists) {
            userRepository.deleteById(id);
        }
    }


}
