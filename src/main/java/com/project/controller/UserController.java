package com.project.controller;

import com.project.dtos.StandardQueryParamsWithFilter;
import com.project.dtos.UserGetDto;
import com.project.dtos.UserPostDto;
import com.project.entities.User;
import com.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Api(
        tags = {"User"},
        description = "User Resource"
)
@RestController
@RequestMapping( value = "/api", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("Get specific User/ Accessed only By Admin")
    @RequestMapping( method = GET, value = "/user/{userId}" )
    @PreAuthorize("hasRole('ADMIN')")
    public User loadById( @PathVariable Long userId ) {
        return this.userService.findById( userId );
    }

    @ApiOperation("Get all Users/ Accessed only By Admin")
    @RequestMapping( method = GET, value= "/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() {
        return this.userService.findAll();
    }
    @ApiOperation("Get all users")
    @GetMapping
    public ResponseEntity<DataPage<UserGetDto>> findAll(@Valid StandardQueryParamsWithFilter standardQueryParamsWithFilter){
        return ResponseEntity.ok(userService.findAll(standardQueryParamsWithFilter));
    }

    @ApiOperation("Get Current User")
    @GetMapping("/authMe")
    @PreAuthorize("hasRole('USER')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }


    @ApiOperation("Create new user")
    @PostMapping(value = "user")
    public ResponseEntity<UserGetDto> save(
            @RequestBody @Valid UserPostDto postClient,
            final BindingResult bindingResult) {

        return ResponseEntity.ok(userService.create(postClient));
    }
}
