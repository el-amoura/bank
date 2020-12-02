package com.fast.bank.service.impl;

import com.fast.bank.api.dto.request.DepositToSavingsRequestDTO;
import com.fast.bank.api.dto.request.SavingsAccountRequestDTO;
import com.fast.bank.api.dto.request.WithdrawFromSavingsRequestDTO;
import com.fast.bank.api.dto.response.SavingsAccountResponseDTO;
import com.fast.bank.model.PublicHoliday;
import com.fast.bank.model.SavingsAccount;
import com.fast.bank.model.User;
import com.fast.bank.repository.SavingsAccountRepository;
import com.fast.bank.repository.UserRepository;
import com.fast.bank.service.SavingsAccountService;
import com.fast.bank.utils.ResponseConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.*;
import java.util.List;

@Service
public class DefaultSavingsAccountService implements SavingsAccountService {

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(DefaultSavingsAccountService.class);

    @Override
    public SavingsAccountResponseDTO save(SavingsAccountRequestDTO request) throws ValidationException {

        User user = validateAndGetUser(request.getUserId());
        if (userHasSavingsAccount(user)) {
            throw new ValidationException("User already has a savings account.");
        }
        validateDate();

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setUser(user);
        savingsAccount.setInterestRate(request.getInterestRate());
        savingsAccount.setBalance(request.getBalance());

        savingsAccount = this.savingsAccountRepository.save(savingsAccount);

        logger.info("Created new savings account for user " + user.getId());

        return ResponseConverter.convertSavingsAccountToDTO(savingsAccount);
    }

    @Override
    public SavingsAccountResponseDTO retrieveSavingsAccountById(Long id) throws ValidationException {

        if (id == null) {
            throw new ValidationException("Invalid id provided.");
        }

        SavingsAccount savingsAccount = this.savingsAccountRepository.findById(id).orElse(null);

        if (savingsAccount == null) {
            throw new ValidationException("No savings account found for provided id.");
        }
        logger.info("Retrieve by id savings account " + savingsAccount.getId());

        return ResponseConverter.convertSavingsAccountToDTO(savingsAccount);
    }

    @Override
    public SavingsAccountResponseDTO retrieveSavingsAccountByUserId(Long userId) throws ValidationException {

        User user = validateAndGetUser(userId);

        SavingsAccount savingsAccount = this.savingsAccountRepository.findByUser(user);

        logger.info("Retrieving savings account for user " + user.getId());

        return ResponseConverter.convertSavingsAccountToDTO(savingsAccount);
    }

    @Override
    public SavingsAccountResponseDTO deposit(DepositToSavingsRequestDTO request) throws ValidationException {

        User user = validateAndGetUser(request.getUserId());

        SavingsAccount savingsAccount = validateSavingsAccountAndGet(user);

        if (request.getAmount() < 0.1) {
            throw new ValidationException("Invalid balance value: " + request.getAmount());
        }

        savingsAccount.setBalance((BigDecimal.valueOf(savingsAccount.getBalance()).add(BigDecimal.valueOf(request.getAmount()))).doubleValue());

        logger.info("Deposit successful for user " + user.getId());

        return ResponseConverter.convertSavingsAccountToDTO(this.savingsAccountRepository.save(savingsAccount));

    }

    @Override
    public SavingsAccountResponseDTO updateInterest(SavingsAccountRequestDTO request) throws ValidationException {

        User user = validateAndGetUser(request.getUserId());

        SavingsAccount savingsAccount = validateSavingsAccountAndGet(user);

        if (request.getInterestRate() < 0.0) {
            throw new ValidationException("Invalid interest value: " + request.getInterestRate());
        }

        savingsAccount.setInterestRate(request.getInterestRate());

        logger.info("Interest updated for user " + user.getId());

        return ResponseConverter.convertSavingsAccountToDTO(this.savingsAccountRepository.save(savingsAccount));
    }

    @Override
    public SavingsAccountResponseDTO withdraw(WithdrawFromSavingsRequestDTO request) throws ValidationException {

        User user = validateAndGetUser(request.getUserId());

        SavingsAccount savingsAccount = validateSavingsAccountAndGet(user);

        if (request.getAmount() < 0.1) {
            throw new ValidationException("Invalid withdraw value: " + request.getAmount());
        }

        if (savingsAccount.getBalance() < request.getAmount()) {
            throw new ValidationException("Insufficient funds.");
        }

        savingsAccount.setBalance((BigDecimal.valueOf(savingsAccount.getBalance()).subtract(BigDecimal.valueOf(request.getAmount()))).doubleValue());

        logger.info("Withdraw successful for user " + user.getId());

        return ResponseConverter.convertSavingsAccountToDTO(this.savingsAccountRepository.save(savingsAccount));
    }

    private SavingsAccount validateSavingsAccountAndGet(User user) throws ValidationException {
        SavingsAccount savingsAccount = this.savingsAccountRepository.findByUser(user);

        if (savingsAccount == null) {
            throw new ValidationException("User does not have a savings account.");
        }
        return savingsAccount;
    }

    private void validateDate() throws ValidationException {
        final LocalDateTime openDate = LocalDateTime.now(ZoneId.of("Europe/Bucharest"));

        switch (openDate.getDayOfWeek()) {
            case SATURDAY:
            case SUNDAY: {
                throw new ValidationException("Unable to open savings account outside working days.");
            }
        }

        LocalDateTime start = LocalDateTime.now(ZoneId.of("Europe/Bucharest")).with(LocalTime.of(9, 0));
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Europe/Bucharest")).with(LocalTime.of(18, 0));

        if (!openDate.isAfter(start) || !openDate.isBefore(end)) {
            throw new ValidationException("Unable to open savings account outside working hours.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int year = openDate.getYear();
            List<PublicHoliday> publicHolidays = objectMapper.readValue(new URL("https://date.nager.at/api/v1/get/RO/" + year), new TypeReference<List<PublicHoliday>>() {
            });
            PublicHoliday now = new PublicHoliday();

            //set only the date because we implemented the equals and hashcode methods to only verify the date
            now.setDate(openDate.toLocalDate().toString());

            if (publicHolidays.contains(now)) {
                throw new ValidationException("Unable to open savings account during public holiday.");
            }
        } catch (IOException e) {
            logger.error("Failed to read public holidays. Will continue without checking.", e);
        }
    }

    private User validateAndGetUser(Long userId) throws ValidationException {
        if (userId == null) {
            throw new ValidationException("Invalid user id provided.");
        }

        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new ValidationException("Provided user id does not exist.");
        }

        return user;
    }

    private boolean userHasSavingsAccount(User user) {
        return this.savingsAccountRepository.existsByUser(user);
    }
}
