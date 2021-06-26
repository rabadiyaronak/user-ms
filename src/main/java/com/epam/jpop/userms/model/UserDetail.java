package com.epam.jpop.userms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDetail {
    private Long id;

    @NotBlank(message = "Name should not be null or empty")
    private String name;

    @NotBlank(message = "Email should not be null or empty")
    @Email(message = "Email is not valid")
    private String email;

    private Boolean isActive = Boolean.FALSE;

}
