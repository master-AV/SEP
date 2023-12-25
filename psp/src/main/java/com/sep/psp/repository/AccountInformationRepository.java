package com.sep.psp.repository;

import ftn.sep.db.AccountInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountInformationRepository extends JpaRepository<AccountInformation, Long> {
    @Query("select a from AccountInformation a where a.userId = ?1")
    Optional<AccountInformation> findByUserId(long userId);


    @Override
    Optional<AccountInformation> findById(Long aLong);
}
