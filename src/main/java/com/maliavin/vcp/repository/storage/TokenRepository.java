package com.maliavin.vcp.repository.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.maliavin.vcp.domain.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

    Token findBySeries(String series);

    Token findByUsername(String username);
}
