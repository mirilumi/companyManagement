package com.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;


@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Validated
@Setter
public class UserRegister {

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
