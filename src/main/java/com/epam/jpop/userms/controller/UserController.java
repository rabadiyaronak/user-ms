package com.epam.jpop.userms.controller;

import com.epam.jpop.userms.model.UserDetail;
import com.epam.jpop.userms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDetail> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDetail getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody UserDetail userDetail) {
        UserDetail savedUserDetail = userService.saveUser(userDetail);
        return ResponseEntity.created(URI.create("/api/v1/users/" + savedUserDetail.getId())).build();
    }

    @PutMapping("/{id}")
    public UserDetail update(@RequestBody UserDetail userDetail, @PathVariable Long id) {
        return userService.updateUser(userDetail, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }


}
