package com.sep.psp.repository;

import ftn.sep.db.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> getPaymentMethodBySubscribedIsTrue();

    @Query("UPDATE PaymentMethod p SET p.subscribed = :subscribed WHERE p.name = :name")
    void updatePaymentMethodByNameAndSubscribed(@Param("name") String name, @Param("subscribed") boolean subscribed);

}
