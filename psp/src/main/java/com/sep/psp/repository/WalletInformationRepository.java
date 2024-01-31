package com.sep.psp.repository;

import ftn.sep.db.WalletInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletInformationRepository extends JpaRepository<WalletInformation, Long> {
    Optional<WalletInformation> findByUserId(long userId);

}
