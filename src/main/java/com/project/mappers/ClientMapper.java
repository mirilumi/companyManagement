package com.project.mappers;

import com.project.dtos.ClientGetDto;
import com.project.dtos.ClientPatchDto;
import com.project.dtos.ClientPostDto;
import com.project.entities.Client;
import com.project.repository.UserRepository;
import com.project.security.TokenHelper;
import com.project.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = OptionalsMapper.class)
public abstract  class ClientMapper {
    @Autowired
    TokenHelper tokenHelper;
    @Autowired
    UserService userService;

    @Mapping(expression = "java(userService.findUser(client.getCreatedBy()))", target = "createdBy")
    @Mapping(expression = "java(userService.findUser(client.getLastModifiedBy()))", target = "lastModifiedBy")
    public abstract ClientGetDto fromEntity(Client client);

    @Mapping(expression = "java(tokenHelper.getIdFromToken())", target = "createdBy")
    public abstract Client toEntity(ClientPostDto clientPostDto);

    @Mapping(expression = "java(tokenHelper.getIdFromToken())", target = "lastModifiedBy")
    public abstract void copy(ClientPatchDto dto, @MappingTarget Client client);
}
