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
public class ClientGetDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String municipality;

    private String email;

    private String location;

    private String province;

    private String address;

    private String note;

    private Date lastModifiedDate;

    private Date createdDate;

    private String lastModifiedBy;

    private String createdBy;
}
