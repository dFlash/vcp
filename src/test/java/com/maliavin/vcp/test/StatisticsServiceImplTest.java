package com.maliavin.vcp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.maliavin.vcp.DateUtils;
import com.maliavin.vcp.domain.Statistics;
import com.maliavin.vcp.repository.statistics.StatisticsRepository;
import com.maliavin.vcp.service.StatisticsService;
import com.maliavin.vcp.service.impl.StatisticsServiceImpl;

public class StatisticsServiceImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsService statisticsService = new StatisticsServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listTest() {
        String date = DateUtils.getCurrentDate();
        List<Statistics> stats = new ArrayList<>();
        String videoName = "videoName";
        Integer viewsCount = 10;
        stats.add(new Statistics(videoName, date, viewsCount));
        Mockito.when(statisticsRepository.findByDate(date)).thenReturn(stats);

        List<Statistics> actualStats = statisticsService.list();

        Assert.assertEquals(stats, actualStats);
    }

    @Test
    public void getNullTest() {
        String date = DateUtils.getCurrentDate();
        List<Statistics> stats = new ArrayList<>();
        String videoName = "videoName";
        Mockito.when(statisticsRepository.findByVideoNameAndDate(videoName, date)).thenReturn(stats);

        Statistics actual = statisticsService.get(videoName, date);

        Assert.assertTrue(actual == null);

    }

    @Test
    public void getNotNullTest() {
        String date = DateUtils.getCurrentDate();
        List<Statistics> stats = new ArrayList<>();
        String videoName = "videoName";
        Integer viewsCount = 10;

        Statistics expected = new Statistics(videoName, date, viewsCount);

        stats.add(expected);
        Mockito.when(statisticsRepository.findByVideoNameAndDate(videoName, date)).thenReturn(stats);

        Statistics actual = statisticsService.get(videoName, date);

        Assert.assertEquals(expected, actual);

    }

}
