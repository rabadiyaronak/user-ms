package com.epam.jpop.userms.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFound extends RuntimeException {
    public UserNotFound(String s) {
        super(s);
    }



}
