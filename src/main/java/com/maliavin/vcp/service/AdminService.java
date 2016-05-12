package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;

/**
 * Admin service interface which specify operations for administrators.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface AdminService {

    @Nonnull
    Page<User> getAccounts(@Nonnull Pageable pageable);

    @Nonnull
    Iterable<Company> listCompanies();

    void addUser(@Nonnull User user);

    void saveUser(@Nonnull User user);

    User gerUser(@Nonnull String userId);

    void deleteUser(@Nonnull String id);

    @Nonnull
    Page<Company> listCompanies(@Nonnull Pageable pageable);

    void addCompany(@Nonnull Company company);

    void deleteCompany(@Nonnull String id);
    
    Company getCompany(String id);
    
    void saveCompany(Company company);

}
