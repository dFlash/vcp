package com.maliavin.vcp.service;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.form.AvatarForm;
import com.maliavin.vcp.form.StatisticsForm;

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

    void saveUser(@Nonnull User user);

    void deleteUser(@Nonnull String id);

    @Nonnull
    Page<Company> listCompanies(@Nonnull Pageable pageable);

    void deleteCompany(@Nonnull String id);

    void saveCompany(@Nullable Company company);

    @Nonnull Map<String, String> uploadAvatar(AvatarForm avatarForm);

    @Nonnull
    StatisticsForm statistics();

}
