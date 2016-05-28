package com.maliavin.vcp.repository.storage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.maliavin.vcp.domain.User;

/**
 * DAO for entity User
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByLogin(String login);

    List<User> removeByUserCompanyId(String userCompanyId);

    User findByIdAndHash(String id, String hash);

    List<User> findByLoginOrEmail(String login, String email);
}
