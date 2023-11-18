package com.sep.pcc.repository;

import com.sep.pcc.model.PccRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PccRequestRepository extends JpaRepository<PccRequest, Long> {

    PccRequest findByAcquirerOrderIdAndAcquirerTimestamp(long acquirerOrderId, LocalDateTime acquirerTimestamp);


}
