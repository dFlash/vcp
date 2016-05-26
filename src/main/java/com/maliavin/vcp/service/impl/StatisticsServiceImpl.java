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
        try {
        statisticsRepository.save(statistics);
        } catch (Exception e) {
            String str = e.toString();
            System.out.println("d");
        }
    }

    @Override
    public List<Statistics> list() {
        return (List<Statistics>) statisticsRepository.findAll();
    }

    @Override
    public Statistics get(String videoName, String date) {
        try {
            
        
        List<Statistics> stats = statisticsRepository.findByVideoNameAndDate(videoName, date);
        
        if (stats.isEmpty()) {
            return null;
        } else {
            return stats.get(0);
        }
        } catch (Exception e) {
            String str = e.toString();
            System.out.println("d");
        }
        return null;
    }



}
