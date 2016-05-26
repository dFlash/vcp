package com.maliavin.vcp.repository.statistics;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.maliavin.vcp.domain.Statistics;

public interface StatisticsRepository extends CrudRepository<Statistics, String> {

    List<Statistics> findByVideoNameAndDate(String videoName, String date);

}
