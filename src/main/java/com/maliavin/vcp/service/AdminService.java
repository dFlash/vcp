package com.maliavin.vcp.service;

import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.form.AvatarForm;

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

    void saveUser(@Nonnull String id, @Nonnull User user);

    User gerUser(@Nonnull String userId);

    void deleteUser(@Nonnull String id);

    @Nonnull
    Page<Company> listCompanies(@Nonnull Pageable pageable);

    void addCompany(@Nonnull Company company);

    void deleteCompany(@Nonnull String id);
    
    Company getCompany(String id);
    
    void saveCompany(@Nonnull String id, Company company);

    Map<String, String> uploadAvatar(AvatarForm avatarForm);

}
