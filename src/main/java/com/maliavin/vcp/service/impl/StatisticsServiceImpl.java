package com.maliavin.vcp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.Statistics;
import com.maliavin.vcp.repository.statistics.StatisticsRepository;
import com.maliavin.vcp.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public void save(Statistics statistics) {
        statisticsRepository.save(statistics);
    }

    @Override
    public List<Statistics> list() {
        return (List<Statistics>) statisticsRepository.findAll();
    }

}
