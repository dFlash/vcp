package com.maliavin.vcp.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("statistics")
public class Statistics {

    @Id
    private String statisticsId;

    @Indexed
    private String videoName;

    @Indexed
    private String date;

    private Integer viewsCount;

    private Set<String> userName = new HashSet<>();

    public Statistics() {
        super();
    }
    
    public Statistics(String statisticsId) {
        super();
        this.statisticsId = statisticsId;
    }

    public Statistics(String videoName, String date, Integer viewsCount) {
        super();
        this.videoName = videoName;
        this.date = date;
        this.viewsCount = viewsCount;
    }

    public String getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(String statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Set<String> getUserName() {
        return userName;
    }

    public void setUserName(Set<String> userName) {
        this.userName = userName;
    }

}
