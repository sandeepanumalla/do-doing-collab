package com.example.service;

import com.example.request.PasswordResetRequest;


public interface PasswordResetService {

    void resetPassword(PasswordResetRequest resetPasswordRequest, String token);

}
