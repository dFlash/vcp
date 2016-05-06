package com.maliavin.vcp.repository.storage;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.maliavin.vcp.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByName(String name);
}
