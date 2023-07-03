package com.example.service;

import com.example.model.PasswordToken;
import com.example.repository.ResetPasswordRepository;
import com.example.repository.UserRepository;
import com.example.service.impl.ResetPasswordTokenServiceImpl;
import com.example.taskmanagementservice.TaskManagementServiceApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TaskManagementServiceApplication.class)
public class PasswordResetRequestTokenUserTest {

    private final ResetPasswordTokenService resetPasswordTokenService;
    private final UserRepository userRepository;

    private final ResetPasswordRepository passwordRepository;


    @Autowired
    public PasswordResetRequestTokenUserTest(ResetPasswordTokenServiceImpl resetPasswordTokenService, UserRepository userRepository, ResetPasswordRepository passwordRepository) {
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }


    @Test
    void generatePasswordTokenForUserTest() {
         String token = resetPasswordTokenService.generatePasswordTokenForUser();
        System.out.println("the token is" + token);
    }

    @Test
    void passwwordCleanUpTest() {
        resetPasswordTokenService.cleanUpExpiredTokens(userRepository.findUserByEmail("laneyij534@akoption.com").get().getUserId());
        String token = resetPasswordTokenService.generatePasswordTokenForUser();
        PasswordToken passwordToken = PasswordToken.builder()
                .token(token)
                .expiryDate(resetPasswordTokenService.calculateExpirationDate(5L))
                .user(userRepository.findUserByEmail("laneyij534@akoption.com").get())
                .build();
        passwordRepository.save(passwordToken);

        Assertions.assertEquals(passwordRepository.findByToken(token).get().getToken(), token);
        resetPasswordTokenService.cleanUpExpiredTokens(passwordRepository.findUserIdByToken(token));
        Assertions.assertTrue(passwordRepository.findByToken(token).isEmpty());
    }
}

