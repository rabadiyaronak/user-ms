package com.epam.jpop.userms.controller;

import com.epam.jpop.userms.model.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public List<UserDetails> getAll() {
        return Collections.emptyList();
    }

    @GetMapping("/{id}")
    public UserDetails getById(Long id) {
        return UserDetails.builder().build();
    }

    @PostMapping
    public ResponseEntity<Void> add(UserDetails userDetails) {
        return ResponseEntity.created(URI.create("")).build();
    }

    @PutMapping
    public UserDetails update(UserDetails userDetails, Long id) {
        return UserDetails.builder().build();
    }

    @DeleteMapping
    public void delete(Long id) {
    }


}
