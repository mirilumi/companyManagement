package com.project.service.impl;

import com.project.controller.DataPage;
import com.project.dtos.StandardQueryParamsWithFilter;
import com.project.dtos.UserGetDto;
import com.project.dtos.UserPostDto;
import com.project.dtos.UserRegister;
import com.project.entities.User;
import com.project.mappers.UserMapper;
import com.project.repository.UserRepository;
import com.project.service.BaseService;
import com.project.service.UserService;
import com.project.service.utils.MakeSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl  extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final MakeSpecification makeSpecification;
    private final UserMapper userMapper;
    @Override
    public User findByUsername( String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username );
        return u;
    }

    public User findById( Long id ) throws AccessDeniedException {
        User u = userRepository.findOne( id );
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }
    public DataPage<UserGetDto> findAll(StandardQueryParamsWithFilter queryParams){
        return findAll("users", queryParams,
                pageRequest -> {
                    return userRepository.findAll(makeSpecification.makeUser(queryParams.getFilterByExample()), pageRequest);
                },userMapper::fromEntity);
    }
    public UserGetDto create(UserPostDto userPostDto){
        User user = userRepository.save(userMapper.toEntity(userPostDto));
        return userMapper.fromEntity(user);
    }
    public UserGetDto register(UserRegister userRegister){
        User user = userRepository.save(userMapper.toEntity(userRegister));
        return userMapper.fromEntity(user);
    }
    @Override
    public String findUser(Long id) {
        if(id != null){
          User user= userRepository.findOne(id);
          if(user != null){
              return user.getFirstName()+" "+user.getLastName();
          }
        }
        return null;
    }
}
