package com.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Validated
@Setter
public class ClientPatchDto {

    private String firstName;


    private String lastName;

    private String municipality;

    @Email
    private String email;

    private String location;

    private String province;

    private String address;

    private String note;

}
