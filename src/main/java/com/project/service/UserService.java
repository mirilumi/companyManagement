package com.project.service;

import com.project.controller.DataPage;
import com.project.dtos.StandardQueryParamsWithFilter;
import com.project.dtos.UserGetDto;
import com.project.dtos.UserPostDto;
import com.project.dtos.UserRegister;
import com.project.entities.User;

import java.util.List;



public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();

    String findUser(Long id);

    public DataPage<UserGetDto> findAll(StandardQueryParamsWithFilter queryParams);

    UserGetDto create(UserPostDto userPostDto);

    UserGetDto register(UserRegister userRegister);
}
