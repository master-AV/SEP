package com.sep.id.service.implementation;


import com.sep.id.dto.VerifyMailDTO;
import com.sep.id.exception.EntityNotFoundException;
import com.sep.id.exception.MailCannotBeSentException;
import com.sep.id.exception.WrongVerifyTryException;
import com.sep.id.repository.VerificationRepository;
import ftn.sep.db.RegistrationVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
            throw new EntityNotFoundException();
        }
    }

    public RegistrationVerification getByHashedId(String id) throws EntityNotFoundException {

        if(verificationRepository.getRegistrationVerificationsByHashedId(id).isPresent()){
            return verificationRepository.getRegistrationVerificationsByHashedId(id).get();
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    public RegistrationVerification update(String verifyId, String securityCode) throws EntityNotFoundException, WrongVerifyTryException {
        RegistrationVerification verify = getByHashedId(verifyId);
        if (!verify.isUsed() && hasTries(verify.getFailedAttempts()) && checkSecurityCode(securityCode, verify.getSecurityCode()) && verify.getExpires().isAfter(LocalDateTime.now())) {
            verify.incrementNumOfTries();
            this.saveChanges(verify, true);

            return verify;
        } else if (!verify.isUsed() && hasTries(verify.getFailedAttempts()) && verify.getExpires().isAfter(LocalDateTime.now())){
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


    public void create(String email) throws IOException, MailCannotBeSentException {
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


    public boolean hasTries(int failedAttempts) {
        return failedAttempts < MAX_NUM_VERIFY_TRIES;
    }


    public boolean checkSecurityCode(String securityCode, String correctSecurityCode){

        return BCrypt.checkpw(securityCode, correctSecurityCode);
    }

}
