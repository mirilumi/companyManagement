package com.project.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "CLIENTS")
public class Client extends BaseEntity{


    @NotBlank
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "municipality")
    private String municipality;

    @NotBlank
    @Column(name = "email",nullable = false,unique = true)
    @Email
    private String email;

    @Column(name = "location")
    private String location;

    @Column(name = "province")
    private String province;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;
}
