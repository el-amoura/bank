package com.fast.bank.repository;

import com.fast.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(final String email);

    public boolean existsByEmail(final String email);

    public User findByIdentificationNumber(final String identificationNumber);

    public boolean existsByIdentificationNumber(final String identificationNumber);

}
