package com.epam.jpop.userms.controller;

import com.epam.jpop.userms.model.UserDetails;
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
    public List<UserDetails> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDetails getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody UserDetails userDetails) {
        UserDetails savedUserDetails = userService.saveUser(userDetails);
        return ResponseEntity.created(URI.create("/api/v1/users/" + savedUserDetails.getId())).build();
    }

    @PutMapping("/{id}")
    public UserDetails update(@RequestBody UserDetails userDetails, @PathVariable Long id) {
        return userService.updateUser(userDetails, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }


}
