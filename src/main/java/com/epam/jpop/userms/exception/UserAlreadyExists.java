package com.epam.jpop.userms.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String s) {
        super(s);
    }
}
