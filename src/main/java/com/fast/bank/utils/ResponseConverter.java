package com.fast.bank.utils;

import com.fast.bank.api.dto.response.SavingsAccountResponseDTO;
import com.fast.bank.api.dto.response.UserResponseDTO;
import com.fast.bank.model.SavingsAccount;
import com.fast.bank.model.User;

public class ResponseConverter {

    public static UserResponseDTO convertUserToDTO(User user) {
        if (user == null) {
            return new UserResponseDTO();
        }
        return new UserResponseDTO(user.getId(),
                user.getIdentificationNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getEmail());
    }

    public static SavingsAccountResponseDTO convertSavingsAccountToDTO(SavingsAccount savingsAccount) {
        if (savingsAccount == null) {
            return new SavingsAccountResponseDTO();
        }
        return new SavingsAccountResponseDTO(savingsAccount.getId(),
                savingsAccount.getUser().getId(),
                savingsAccount.getBalance(),
                savingsAccount.getInterestRate());
    }

}