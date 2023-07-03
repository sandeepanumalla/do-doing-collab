package com.example.service.impl;

import com.example.repository.ResetPasswordRepository;
import com.example.repository.UserRepository;
import com.example.request.PasswordResetRequest;
import com.example.service.PasswordResetService;
import com.example.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;

    @Autowired
    public PasswordResetServiceImpl(ResetPasswordTokenService resetPasswordTokenService, UserRepository userRepository,
                                    ResetPasswordRepository resetPasswordRepository) {
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.userRepository = userRepository;
        this.resetPasswordRepository = resetPasswordRepository;
    }

    @Override
    public void resetPassword(PasswordResetRequest resetPasswordRequest, String token) {
        Long userId = resetPasswordRepository.findUserIdByToken(token);
        validatePassword(resetPasswordRequest);
        if(!resetPasswordTokenService.validateTheToken(token)) {
            resetPasswordTokenService.cleanUpExpiredTokens(userId);
           throw new IllegalArgumentException("token is invalid");
        }
        userRepository.findById(userId).ifPresent(user -> {
            user.setPassword(resetPasswordRequest.getPassword());
            userRepository.save(user);
        });
        resetPasswordTokenService.cleanUpExpiredTokens(userId);
    }

    private void validatePassword(PasswordResetRequest resetPasswordRequest) {
        if(resetPasswordRequest == null) {
            throw new IllegalArgumentException("resetPasswordRequest cannot be null");
        }
        if(resetPasswordRequest.getPassword() == null || resetPasswordRequest.getConfirmPassword() == null) {
            throw new IllegalArgumentException("password and confirmPassword cannot be null");
        }
    }
}
