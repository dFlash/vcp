package com.maliavin.vcp.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.maliavin.vcp.domain.Token;
import com.maliavin.vcp.repository.storage.TokenRepository;

@Component
public class TokenService implements PersistentTokenRepository {

    @Autowired
    TokenRepository repository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        repository
                .save(new Token(null, token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate()));
    }

    @Override
    public void updateToken(String series, String value, Date lastUsed) {
        Token token = repository.findBySeries(series);
        if (token != null) {
            repository.save(new Token(token.getId(), token.getUsername(), series, value, lastUsed));
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return repository.findBySeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        Token token = repository.findByUsername(username);
        if (token != null) {
            repository.delete(token);
        }
    }
}
