package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.User;

public interface AdminService {

    @Nonnull
    Page<User> getAccounts(@Nonnull Pageable pageable);

}
