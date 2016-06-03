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

    private Set<String> addresses = new HashSet<>();

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

    public Set<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((statisticsId == null) ? 0 : statisticsId.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((videoName == null) ? 0 : videoName.hashCode());
        result = prime * result + ((viewsCount == null) ? 0 : viewsCount.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Statistics other = (Statistics) obj;
        if (addresses == null) {
            if (other.addresses != null)
                return false;
        } else if (!addresses.equals(other.addresses))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (statisticsId == null) {
            if (other.statisticsId != null)
                return false;
        } else if (!statisticsId.equals(other.statisticsId))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (videoName == null) {
            if (other.videoName != null)
                return false;
        } else if (!videoName.equals(other.videoName))
            return false;
        if (viewsCount == null) {
            if (other.viewsCount != null)
                return false;
        } else if (!viewsCount.equals(other.viewsCount))
            return false;
        return true;
    }
    

}
