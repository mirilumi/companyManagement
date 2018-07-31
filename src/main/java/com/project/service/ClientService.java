package com.project.service;

import com.project.controller.DataPage;
import com.project.dtos.ClientGetDto;
import com.project.dtos.ClientPatchDto;
import com.project.dtos.ClientPostDto;
import com.project.dtos.StandardQueryParamsWithFilter;
import com.project.entities.Client;
import com.project.exceptions.CustomException;
import com.project.exceptions.ErrorCode;
import com.project.exceptions.MessageKey;
import com.project.mappers.ClientMapper;
import com.project.repository.ClientRepository;
import com.project.service.utils.MakeSpecification;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class ClientService extends BaseService{

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final MakeSpecification makeSpecification;

    private  final EmailService emailService;

    public ClientGetDto create(ClientPostDto clientPostDto){
        Client entity = create(Client.class, clientPostDto, clientMapper::toEntity);
        return clientMapper.fromEntity(entity);
    }
    public void delete(Long clientId) {
        delete(Client.class, clientId);
    }
    public DataPage<ClientGetDto> findAll(StandardQueryParamsWithFilter queryParams){
        return findAll("clients", queryParams,
                pageRequest -> clientRepository.findAll(makeSpecification.makeClient(queryParams.getFilterByExample()),pageRequest),clientMapper::fromEntity);
    }

    public void sendMailToClient(Long clientId) {
        ClientGetDto client = findDto(clientId);
        try {
            emailService.send(client);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }
    private ClientGetDto findDto(Long id) {
        return findOne(Client.class, id, clientMapper::fromEntity);
    }
    public ClientGetDto update(Long id,ClientPatchDto dto){
        Client updated = updateEntity(
                Client.class,
                () ->  clientRepository.findOne(id),
                () -> dto,
                clientMapper::copy);

        return clientMapper.fromEntity(updated);
    }
    public ClientGetDto findOne(Long clientId) {
        return clientMapper.fromEntity(findEntity(clientId));
    }
    private Client findEntity(Long groupId) {
        Client client = clientRepository.findOne(groupId);
        if(client == null){
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND, MessageKey.client_does_not_exist);
        }
        return client;
    }
}
