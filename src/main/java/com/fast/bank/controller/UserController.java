package com.fast.bank.controller;

import com.fast.bank.api.dto.request.UserRegistrationDTO;
import com.fast.bank.api.dto.response.UserResponseDTO;
import com.fast.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping(
        value = "/user",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    UserResponseDTO create(@RequestBody final UserRegistrationDTO request) throws ValidationException {

        return this.userService.save(request);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    UserResponseDTO retrieveUserById(@PathVariable("id") Long id) throws ValidationException {
        return this.userService.retrieveUserById(id);
    }

    @RequestMapping(value = "/identification/{identification}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    UserResponseDTO retrieveUserByIdentification(@PathVariable("identification") String identification) throws ValidationException {
        return this.userService.retrieveUserByIdentification(identification);
    }

}
