package com.fast.bank.service;

import com.fast.bank.api.dto.request.UserRegistrationDTO;
import com.fast.bank.api.dto.response.UserResponseDTO;

import javax.xml.bind.ValidationException;

public interface UserService {

    UserResponseDTO save(UserRegistrationDTO request) throws ValidationException;

    UserResponseDTO retrieveUserById(Long id) throws ValidationException;

    UserResponseDTO retrieveUserByIdentification(String identification) throws ValidationException;
}
