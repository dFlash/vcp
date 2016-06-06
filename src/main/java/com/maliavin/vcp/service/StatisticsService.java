package com.maliavin.vcp.service;

import java.util.List;

import javax.annotation.Nonnull;

import com.maliavin.vcp.domain.Statistics;
import com.maliavin.vcp.security.CurrentUser;

public interface StatisticsService {
    
    void save(String videoTitle, CurrentUser user, String addr);
    
    @Nonnull
    List<Statistics> list();

}
