package com.maliavin.vcp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.DateUtils;
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
        String date = DateUtils.getCurrentDate();
        return (List<Statistics>) statisticsRepository.findByDate(date);
    }

    @Override
    public Statistics get(String videoName, String date) {
        List<Statistics> stats = statisticsRepository.findByVideoNameAndDate(videoName, date);

        if (stats.isEmpty()) {
            return null;
        } else {
            return stats.get(0);
        }
    }



}
