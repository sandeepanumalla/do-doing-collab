package com.example.service;

import java.util.Date;

public interface ResetPasswordTokenService {

    String generatePasswordTokenForUser();

    Date calculateExpirationDate(long mins);

    boolean validateTheToken(String token);

    void cleanUpExpiredTokens(long user);

}
