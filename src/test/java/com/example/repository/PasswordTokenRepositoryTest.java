package com.example.repository;

import com.example.model.PasswordToken;
import com.example.service.ResetPasswordTokenService;
import com.example.taskmanagementservice.TaskManagementServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TaskManagementServiceApplication.class)
public class PasswordTokenRepositoryTest {

    private final ResetPasswordRepository passwordTokenRepository;
    private final ResetPasswordTokenService passwordTokenService;

    private final UserRepository userRepository;

    @Autowired
    public PasswordTokenRepositoryTest(ResetPasswordRepository passwordTokenRepository, ResetPasswordTokenService passwordTokenService, UserRepository userRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordTokenService = passwordTokenService;
        this.userRepository = userRepository;
    }

    @Test
    public void testSavePasswordToken() {
        PasswordToken passwordToken = PasswordToken.builder()
                .token(passwordTokenService.generatePasswordTokenForUser())
                .expiryDate(passwordTokenService.calculateExpirationDate(5L))
                .user(userRepository.findUserByEmail("laneyij534@akoption.com").get())
                .build();
        passwordTokenRepository.save(passwordToken);
        System.out.println("Password Token Saved Successfully");
    }

    @Test
    public void testDeletePasswordToken() {
        passwordTokenService.cleanUpExpiredTokens(userRepository.findUserByEmail("laneyij534@akoption.com").get().getUserId());
    }

    @Test
    public void testFindByToken() {
        passwordTokenService.cleanUpExpiredTokens(userRepository.findUserByEmail("laneyij534@akoption.com").get().getUserId());
        String token = passwordTokenService.generatePasswordTokenForUser();
        PasswordToken passwordToken = PasswordToken.builder()
                .token(token)
                .expiryDate(passwordTokenService.calculateExpirationDate(5L))
                .user(userRepository.findUserByEmail("laneyij534@akoption.com").get())
                .build();
        passwordTokenRepository.save(passwordToken);
        System.out.println("Password Token Saved Successfully");
        System.out.println(passwordTokenRepository.findByToken(token));
    }


}
