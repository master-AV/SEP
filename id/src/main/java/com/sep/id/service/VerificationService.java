package com.sep.id.service;

import com.sep.id.dto.VerifyMailDTO;
import com.sep.id.exception.EntityNotFoundException;
import com.sep.id.exception.MailCannotBeSentException;
import com.sep.id.exception.WrongVerifyTryException;
import com.sep.id.repository.VerificationRepository;
import ftn.sep.db.RegistrationVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.sep.id.utils.Constants.*;
import static com.sep.id.utils.Helper.*;


@Service
public class VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private EmailService emailService;

    public RegistrationVerification get(Long id) throws EntityNotFoundException {

        if(verificationRepository.getRegistrationVerificationsById(id).isPresent()){
            return verificationRepository.getRegistrationVerificationsById(id).get();
        }
        else{
            throw new EntityNotFoundException(id);
        }
    }

    public RegistrationVerification getByHashedId(String id) throws EntityNotFoundException {

        if(verificationRepository.getRegistrationVerificationsByHashedId(id).isPresent()){
            return verificationRepository.getRegistrationVerificationsByHashedId(id).get();
        }
        else{
            throw new EntityNotFoundException(id);
        }
    }
    public boolean checkSecurityCode(String securityCode, String trueSecurityCode){
        return BCrypt.checkpw(securityCode, trueSecurityCode);
    }

    public boolean canVerify(RegistrationVerification registrationVerification, String securityCode) {
        return registrationVerification.isNotUsed() &&
                registrationVerification.hasTries(MAX_NUM_VERIFY_TRIES) &&
                checkSecurityCode(securityCode, registrationVerification.getSecurityCode()) &&
                registrationVerification.notExpired();
    }

    public RegistrationVerification update(String verifyId, int securityCode) throws EntityNotFoundException, WrongVerifyTryException {
        RegistrationVerification verify = getByHashedId(verifyId);
        if (canVerify(verify, String.valueOf(securityCode))) {
            verify.incrementNumOfTries();
            this.saveChanges(verify, true);
            return verify;
        } else if (verify.wrongCodeButHasTries(MAX_NUM_VERIFY_TRIES)){
            this.saveChanges(verify, verify.incrementNumOfTries() >= MAX_NUM_VERIFY_TRIES);
            throw new WrongVerifyTryException("Your security code is not accepted. Try again.");
        } else {
            saveChanges(verify, true);
            throw new WrongVerifyTryException("Your verification code is either expired or typed wrong 3 times. Reset code.");
        }
    }

    private void saveChanges(final RegistrationVerification verify, final boolean used) {
        verify.setUsed(used);
        verificationRepository.save(verify);
    }

    public boolean create(String email) throws IOException, MailCannotBeSentException {
        String salt = generateRandomString(SALT_LENGTH);
        int securityCode = generateSecurityCode();
        RegistrationVerification registrationVerification = new RegistrationVerification(
                getHash(String.valueOf(securityCode)),
                ZERO_FAILED_ATTEMPTS,
                email,
                LocalDateTime.now().plusMinutes(10),
                salt,
                generateHashForURL(salt + email)
        );

        verificationRepository.save(registrationVerification);
        this.sendVerificationEmail(new VerifyMailDTO(securityCode, registrationVerification.getHashedId()));

        return true;
    }

    public void generateNewSecurityCode(String verifyHash)
            throws EntityNotFoundException, IOException, MailCannotBeSentException
    {
        RegistrationVerification verify = getByHashedId(verifyHash);
        create(verify.getUserEmail());
        verificationRepository.delete(verify);
    }

    public void sendVerificationEmail(VerifyMailDTO verifyMailDTO)
            throws IOException, MailCannotBeSentException
    {
        emailService.sendVerificationMail(
                verifyMailDTO.getSecurityCode(),
                String.format("%s%s", FRONT_VERIFY_URL, verifyMailDTO.getHashId())
        );
    }
}
