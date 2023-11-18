package com.master.bank.repository;

import com.master.bank.model.SalesAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SalesAccountRepository extends JpaRepository<SalesAccount, Long> {
    SalesAccount findByMerchantId(String merchantId);

}
