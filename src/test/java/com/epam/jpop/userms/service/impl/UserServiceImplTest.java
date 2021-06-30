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
        //Given
        List<User> users = prepareUserMockData();
        Mockito.when(userRepository.findAll()).thenReturn(users);

        //When
        List<UserDetail> userDetails = userService.getAllUsers();

        //Then
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
        //Given
        User user = prepareUserMockData().get(0);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //When
        UserDetail userDetail = userService.getUserById(1L);

        //Then
        Assertions.assertNotNull(userDetail);
        Assertions.assertEquals(user.getId(), userDetail.getId());
        Assertions.assertEquals(user.getName(), userDetail.getName());
        Assertions.assertEquals(user.getEmail(), userDetail.getEmail());
    }

    @Test
    void saveUser() {
        //Given
        UserDetail saveUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(prepareUserMockData().get(0));

        //When
        UserDetail savedUser = userService.saveUser(saveUserRequest);

        //Then
        Assertions.assertNotNull(savedUser);
    }

    @Test
    void saveUserWithUserEmailExistsInDb() {
        //Given
        UserDetail saveUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(prepareUserMockData().get(0)));

        //When
        Assertions.assertThrows(UserAlreadyExists.class, () -> userService.saveUser(saveUserRequest));

        //Then
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void updateUser() {
        //Given
        UserDetail updateUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        User mockUser = prepareUserMockData().get(0);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(prepareUserMockData().get(0));

        //When
        UserDetail updatedUser = userService.updateUser(updateUserRequest, 1L);

        //Then
        Assertions.assertNotNull(updatedUser);
    }

    @Test
    void updateUserWithUserIdNotExists() {
        //Given
        UserDetail updateUserRequest = UserDetail.builder().name("Mock User").email("mock@useremail.com").isActive(true).build();
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //When
        Assertions.assertThrows(UserNotFound.class, () -> userService.updateUser(updateUserRequest, 1L));

        //Then
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteUserById() {

        //Given
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());

        //When
        userService.deleteUserById(1L);

        //Then
        Mockito.verify(userRepository, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void deleteUserByIdWithUserNotExists() {
        //Given
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(false);

        //When
        userService.deleteUserById(1L);

        //Then
        Mockito.verify(userRepository,Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    private List<User> prepareUserMockData() {
        User user1 = User.builder().name("user1").email("user@email.com").id(2L).isActive(true).build();
        User user2 = User.builder().name("user2").email("user2@email.com").id(3L).isActive(true).build();
        return List.of(user1, user2);
    }

}