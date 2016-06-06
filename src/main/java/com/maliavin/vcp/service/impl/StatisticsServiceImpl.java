package com.maliavin.vcp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.DateUtils;
import com.maliavin.vcp.domain.Statistics;
import com.maliavin.vcp.repository.statistics.StatisticsRepository;
import com.maliavin.vcp.security.CurrentUser;
import com.maliavin.vcp.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    
    private static final String UNKNOWN_USER = "Unknown";

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public void save(String videoTitle, CurrentUser user, String addr) {
        Statistics statistics = createStatistics(videoTitle, user, addr);
        statisticsRepository.save(statistics);
    }

    private Statistics createStatistics(String videoTitle, CurrentUser user, String addr){
        String date = DateUtils.getCurrentDate();
        Statistics statistics = get(videoTitle, date);

        if (statistics == null){
            statistics = new Statistics(videoTitle, date, 0);
        }

        String userName = UNKNOWN_USER;
        if (user != null){
            userName = user.getUser().getName() + " " + user.getUser().getSurname();
        }
        statistics.getUserName().add(userName);
        statistics.getAddresses().add(addr);
        statistics.setViewsCount(statistics.getViewsCount() + 1);
        return statistics;
    }

    @Override
    public List<Statistics> list() {
        String date = DateUtils.getCurrentDate();
        return (List<Statistics>) statisticsRepository.findByDate(date);
    }

    private Statistics get(String videoName, String date) {
        List<Statistics> stats = statisticsRepository.findByVideoNameAndDate(videoName, date);

        if (stats.isEmpty()) {
            return null;
        } else {
            return stats.get(0);
        }
    }



}
