package com.example.service.impl;

import com.example.model.PasswordToken;
import com.example.repository.ResetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class ResetPasswordTokenServiceImpl implements com.example.service.ResetPasswordTokenService {

    private final ResetPasswordRepository resetPasswordRepository;

    @Autowired
    public ResetPasswordTokenServiceImpl(ResetPasswordRepository resetPasswordRepository) {
        this.resetPasswordRepository = resetPasswordRepository;
    }


    @Override
    public String generatePasswordTokenForUser() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Date calculateExpirationDate(long min) {
        return Date.from(Instant.now().plusSeconds(60 * min));
    }


    @Override
    public boolean validateTheToken(String token) {
        if(resetPasswordRepository.findByToken(token).isEmpty()) {
            return false;
        }
        PasswordToken passwordToken = resetPasswordRepository.findByToken(token).get();
//        return passwordToken.getExpiryDate().before(new Date()) && passwordToken.getToken().equals(token);
        return false;
    }

    @Override
    public void cleanUpExpiredTokens(long user) {
        resetPasswordRepository.deleteByUserId(user);
    }

}
