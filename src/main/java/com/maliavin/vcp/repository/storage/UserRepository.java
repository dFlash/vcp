package com.maliavin.vcp.repository.storage;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.maliavin.vcp.domain.User;

/**
 * DAO for entity User
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByName(String name);
}
