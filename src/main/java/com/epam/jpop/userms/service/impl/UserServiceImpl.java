package com.epam.jpop.userms.service.impl;

import com.epam.jpop.userms.entity.User;
import com.epam.jpop.userms.exception.UserAlreadyExists;
import com.epam.jpop.userms.exception.UserNotFound;
import com.epam.jpop.userms.model.UserDetails;
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
    public List<UserDetails> getAllUsers() {
        var users = userRepository.findAll();

        return users.stream()
                .map(user -> UserDetails.builder()
                        .email(user.getEmail())
                        .id(user.getId())
                        .name(user.getName())
                        .isActive(user.getIsActive())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public UserDetails getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        var user = userOptional.orElseThrow(() -> new UserNotFound("User not found with id - " + id));
        return UserDetails.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isActive(user.getIsActive()).build();
    }

    @Override
    public UserDetails saveUser(UserDetails userDetails) {

        var existingUser = userRepository.findByEmail(userDetails.getEmail());

        existingUser.ifPresent(user -> {
            throw new UserAlreadyExists("User with given email  is already exists");
        });

        var user = User.builder()
                .email(userDetails.getEmail())
                .name(userDetails.getName())
                .isActive(userDetails.getIsActive()).build();

        var savedUser = userRepository.save(user);
        return UserDetails.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .isActive(savedUser.getIsActive()).build();
    }

    @Override
    public UserDetails updateUser(UserDetails userDetails, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        var userFromDb =  userOptional.orElseThrow(() -> new UserNotFound("User not found with id - " + id));
        var user = User.builder()
                .id(id)
                .name(userDetails.getName())
                .email(userFromDb.getEmail())
                .isActive(userDetails.getIsActive()).build();
        var updatedUser = userRepository.save(user);
        return UserDetails.builder()
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
