package com.maliavin.vcp.service;

import java.util.List;

import javax.annotation.Nonnull;

import com.maliavin.vcp.domain.Statistics;

public interface StatisticsService {
    
    void save(Statistics statistics);
    
    @Nonnull
    List<Statistics> list();

}
