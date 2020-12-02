package com.fast.bank.service;

import com.fast.bank.api.dto.request.DepositToSavingsRequestDTO;
import com.fast.bank.api.dto.request.SavingsAccountRequestDTO;
import com.fast.bank.api.dto.request.WithdrawFromSavingsRequestDTO;
import com.fast.bank.api.dto.response.SavingsAccountResponseDTO;

import javax.xml.bind.ValidationException;

public interface SavingsAccountService {
    SavingsAccountResponseDTO save(SavingsAccountRequestDTO request) throws ValidationException;

    SavingsAccountResponseDTO retrieveSavingsAccountById(Long id) throws ValidationException;

    SavingsAccountResponseDTO retrieveSavingsAccountByUserId(Long userId) throws ValidationException;

    SavingsAccountResponseDTO deposit(DepositToSavingsRequestDTO request) throws ValidationException;

    SavingsAccountResponseDTO updateInterest(SavingsAccountRequestDTO request) throws ValidationException;

    SavingsAccountResponseDTO withdraw(WithdrawFromSavingsRequestDTO request) throws ValidationException;
}
