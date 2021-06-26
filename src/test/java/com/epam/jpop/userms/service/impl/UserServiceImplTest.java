package com.epam.jpop.userms.service.impl;

import com.epam.jpop.userms.entity.User;
import com.epam.jpop.userms.exception.UserAlreadyExists;
import com.epam.jpop.userms.exception.UserNotFound;
import com.epam.jpop.userms.model.UserDetail;
import com.epam.jpop.userms.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers() {
        List<User> users = prepareUserMockData();
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserDetail> userDetails = userService.getAllUsers();
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(users.size(), userDetails.size());
        Assertions.assertEquals(users.get(0).getId(), userDetails.get(0).getId());
        Assertions.assertEquals(users.get(0).getName(), userDetails.get(0).getName());
        Assertions.assertEquals(users.get(0).getEmail(), userDetails.get(0).getEmail());
        Assertions.assertEquals(users.get(1).getId(), userDetails.get(1).getId());
        Assertions.assertEquals(users.get(1).getName(), userDetails.get(1).getName());
        Assertions.assertEquals(users.get(1).getEmail(), userDetails.get(1).getEmail());
    }

    @Test
    void getUserById() {
        User user = prepareUserMockData().get(0);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        UserDetail userDetail = userService.getUserById(1L);
        Assertions.assertNotNull(userDetail);
        Assertions.assertEquals(user.getId(), userDetail.getId());
        Assertions.assertEquals(user.getName(), userDetail.getName());
        Assertions.assertEquals(user.getEmail(), userDetail.getEmail());
    }

    @Test
    void saveUser() {
        UserDetail saveUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(prepareUserMockData().get(0));
        UserDetail savedUser = userService.saveUser(saveUserRequest);
        Assertions.assertNotNull(savedUser);
    }

    @Test
    void saveUserWithUserEmailExistsInDb() {
        UserDetail saveUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(prepareUserMockData().get(0)));
        Assertions.assertThrows(UserAlreadyExists.class, () -> userService.saveUser(saveUserRequest));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void updateUser() {
        UserDetail updateUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        User mockUser = prepareUserMockData().get(0);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(prepareUserMockData().get(0));
        UserDetail updatedUser = userService.updateUser(updateUserRequest, 1L);
        Assertions.assertNotNull(updatedUser);
    }

    @Test
    void updateUserWithUserIdNotExists() {
        UserDetail updateUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFound.class, () -> userService.updateUser(updateUserRequest, 1L));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteUserById() {
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        userService.deleteUserById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void deleteUserByIdWithUserNotExists() {
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(false);
        userService.deleteUserById(1L);
        Mockito.verify(userRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    private List<User> prepareUserMockData() {
        User user1 = User.builder().name("user1").email("user@email.com").id(2L).isActive(true).build();
        User user2 = User.builder().name("user2").email("user2@email.com").id(3L).isActive(true).build();
        return List.of(user1, user2);
    }

}