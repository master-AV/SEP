package com.sep.psp.repository;

import ftn.sep.db.Webshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebshopRepository extends JpaRepository<Webshop, Long> {
    Webshop findByMerchantId(String merchantId);

}
