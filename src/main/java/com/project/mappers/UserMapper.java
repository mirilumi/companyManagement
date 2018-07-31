package com.project.mappers;

import com.project.dtos.UserGetDto;
import com.project.dtos.UserPostDto;
import com.project.dtos.UserRegister;
import com.project.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = OptionalsMapper.class)
public abstract class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;

    public abstract UserGetDto fromEntity(User user);

    public abstract User toEntity(UserPostDto clientPostDto);

    @Mapping(expression = "java(passwordEncoder.encode(userRegister.getPassword()))", target = "password")
    public abstract User toEntity(UserRegister userRegister);
}
