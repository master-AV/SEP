package com.sep.psp.repository;

import com.sep.psp.model.Webshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebshopRepository extends JpaRepository<Webshop, Long> {
    Webshop findByMerchantId(String merchantId);

}
