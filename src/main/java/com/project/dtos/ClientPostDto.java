package com.project.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Validated
@Setter
public class ClientPostDto {
    @ApiModelProperty(required = true)
    @NotBlank
    private String firstName;

    @ApiModelProperty(required = true)
    @NotBlank
    private String lastName;

    private String municipality;

    @Email
    @ApiModelProperty(required = true)
    @NotBlank
    private String email;

    private String location;

    private String province;

    private String address;

    private String note;
}
