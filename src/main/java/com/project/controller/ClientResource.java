package com.project.controller;

import com.project.dtos.*;
import com.project.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(
        tags = {"Clients"},
        description = "Client Resource"
)
@RestController
@RequestMapping( value = "/api/client", produces = MediaType.APPLICATION_JSON_VALUE )
public class ClientResource {

    @Autowired
    ClientService clientService;

    @ApiOperation("Get all clients")
    @GetMapping
    public ResponseEntity<DataPage<ClientGetDto>> findAll(@Valid StandardQueryParamsWithFilter standardQueryParamsWithFilter){
        return ResponseEntity.ok(clientService.findAll(standardQueryParamsWithFilter));
    }


    @ApiOperation("Create new client")
    @PostMapping()
    public ResponseEntity<ClientGetDto> save(
            @RequestBody @Valid ClientPostDto postClient,
            final BindingResult bindingResult) {

        return ResponseEntity.ok(clientService.create(postClient));
    }
    @GetMapping(
            value = "{clientId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Find a specific client")
    public ResponseEntity<ClientGetDto> findOne(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(clientService.findOne(clientId));
    }
    @DeleteMapping(value = "{clientId}")
    @ApiOperation("Remove client")
    public ResponseEntity<ClientGetDto> removeUsersFromGroup(
            @PathVariable("clientId") Long clientId) {

        clientService.delete(clientId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping(value = "/{clientId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update an existing client type")
    public ResponseEntity<ClientGetDto> update(
            @PathVariable("clientId") Long clientId,
            @RequestBody  @Valid ClientPatchDto dto,
            final BindingResult bindingResult
    ){
        return ResponseEntity.ok(clientService.update(clientId,dto));
    }
    @ApiOperation("Send email to customer")
    @GetMapping(value = "/{clientId}/sendMail")
    public ResponseEntity<SingleValueHolder> sendMail(
            @PathVariable("clientId") Long clientId){
        clientService.sendMailToClient(clientId);
        return ResponseEntity.ok(SingleValueHolder.of("OK"));
    }

}
