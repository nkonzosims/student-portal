package com.nkonzo.student_portal_api.service;


import com.nkonzo.student_portal_api.entity.BlacklistedToken;
import com.nkonzo.student_portal_api.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenBlacklistService {

    private final JwtService jwtService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public TokenBlacklistService(JwtService jwtService,
                                 BlacklistedTokenRepository blacklistedTokenRepository) {
        this.jwtService = jwtService;
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    public BlacklistedToken blacklistToken(String token) {

        token = token.replace("Bearer ", "");

        Date expiryDate = jwtService.extractExpiration(token);

        BlacklistedToken blacklistedToken = BlacklistedToken
                .builder()
                .token(token)
                .expiryDate(expiryDate)
                .build();

        return blacklistedTokenRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.findByToken(token).isPresent();
    }
}