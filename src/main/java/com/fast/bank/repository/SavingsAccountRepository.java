package com.fast.bank.repository;

import com.fast.bank.model.SavingsAccount;
import com.fast.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    public boolean existsByUser(final User user);

    public SavingsAccount findByUser(final User user);

}
