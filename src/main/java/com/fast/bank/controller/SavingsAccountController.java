package com.fast.bank.controller;

import com.fast.bank.api.dto.request.DepositToSavingsRequestDTO;
import com.fast.bank.api.dto.request.SavingsAccountRequestDTO;
import com.fast.bank.api.dto.request.WithdrawFromSavingsRequestDTO;
import com.fast.bank.api.dto.response.SavingsAccountResponseDTO;
import com.fast.bank.service.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping(
        value = "/savings",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class SavingsAccountController {

    @Autowired
    private SavingsAccountService savingsAccountService;

    @RequestMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SavingsAccountResponseDTO create(@RequestBody SavingsAccountRequestDTO request) throws ValidationException {
        return this.savingsAccountService.save(request);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    SavingsAccountResponseDTO retrieveSavingsAccountById(@PathVariable("id") Long id) throws ValidationException {
        return this.savingsAccountService.retrieveSavingsAccountById(id);
    }

    @RequestMapping(value = "/userId/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    SavingsAccountResponseDTO retrieveSavingsAccountByUserId(@PathVariable Long userId) throws ValidationException {
        return this.savingsAccountService.retrieveSavingsAccountByUserId(userId);
    }

    @RequestMapping(
            value = "/deposit",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SavingsAccountResponseDTO deposit(@RequestBody DepositToSavingsRequestDTO request) throws ValidationException {
        return this.savingsAccountService.deposit(request);
    }

    @RequestMapping(
            value = "/interest",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SavingsAccountResponseDTO updateInterest(@RequestBody SavingsAccountRequestDTO request) throws ValidationException {
        return this.savingsAccountService.updateInterest(request);
    }

    @RequestMapping(
            value = "/withdraw",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    SavingsAccountResponseDTO withdraw(@RequestBody WithdrawFromSavingsRequestDTO request) throws ValidationException {
        return this.savingsAccountService.withdraw(request);
    }
}
