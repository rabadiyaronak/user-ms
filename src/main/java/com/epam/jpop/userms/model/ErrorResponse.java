package com.epam.jpop.userms.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private List<String> detail;
}
