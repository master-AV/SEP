package ftn.sep.webshop.repository;

import ftn.sep.db.RegistrationVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<RegistrationVerification, Long> {

    Optional<RegistrationVerification> getRegistrationVerificationsById(Long id);
    Optional<RegistrationVerification> getRegistrationVerificationsByHashedId(String id);
}
