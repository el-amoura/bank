package com.fast.bank.service.impl;

import com.fast.bank.api.dto.request.UserRegistrationDTO;
import com.fast.bank.api.dto.response.UserResponseDTO;
import com.fast.bank.model.User;
import com.fast.bank.repository.UserRepository;
import com.fast.bank.service.UserService;
import com.fast.bank.utils.ResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.ValidationException;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);

    @Override
    public UserResponseDTO save(UserRegistrationDTO request) throws ValidationException {

        User user = new User();

        validateEmail(request.getEmail());
        validateName(request);
        validateIdentification(request.getIdentificationNumber());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setEmail(request.getEmail());
        user.setIdentificationNumber(request.getIdentificationNumber());

        user = this.userRepository.save(user);

        logger.info("Created user " + user.getId());

        return ResponseConverter.convertUserToDTO(user);
    }

    @Override
    public UserResponseDTO retrieveUserById(Long id) throws ValidationException {

        if (id == null) {
            throw new ValidationException("Invalid id provided.");
        }

        User user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ValidationException("No user found for provided id.");
        }

        logger.info("Retrieve by id user " + user.getId());

        return ResponseConverter.convertUserToDTO(user);
    }

    @Override
    public UserResponseDTO retrieveUserByIdentification(String identification) throws ValidationException {

        if (identification == null) {
            throw new ValidationException("Invalid identification provided.");
        }

        User user = this.userRepository.findByIdentificationNumber(identification);

        if (user == null) {
            throw new ValidationException("No user found for provided identification.");
        }

        logger.info("Retrieve by identification user " + user.getId());

        return ResponseConverter.convertUserToDTO(user);
    }

    private void validateIdentification(String identificationNumber) throws ValidationException {
        if (StringUtils.hasLength(identificationNumber)
                && identificationNumber.length() > 8 && identificationNumber.length() <= 20) {
            if (this.userRepository.existsByIdentificationNumber(identificationNumber)) {
                throw new ValidationException("Identification number already exists.");
            }
            return;
        }
        throw new ValidationException("Invalid user identification number.");
    }

    private void validateName(UserRegistrationDTO request) throws ValidationException {

        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String middleName = request.getMiddleName();

        if (firstName == null || lastName == null) {
            throw new ValidationException("Please provide first name and last name.");
        } else if (firstName.length() <= 1 || lastName.length() <= 1) {
            throw new ValidationException("Please provide at lease 2 characters for first and last name");
        } else if (firstName.length() > 20 || lastName.length() > 20 || (middleName != null && middleName.length() > 20)) {
            throw new ValidationException("Please provide a maximum of 20 characters for first, last or middle name");
        }

    }

    private void validateEmail(String email) throws ValidationException {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new ValidationException("Invalid email.");
        }

        if (this.userRepository.existsByEmail(email)) {
            throw new ValidationException("Email already exists.");
        }
    }
}
