package com.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import java.util.Date;


@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Validated
@Setter
public class UserGetDto {


    private Long id;


    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean enabled;

    private Date lastPasswordResetDate;


}
