package com.maliavin.vcp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("statistics")
public class Statistics {

    @Id
    private String id;

    private String userName;
    
    private String videoName;

    private String time;

    private String address;

    public Statistics() {
        super();
    }

    public Statistics(String userName, String time, String address, String videoName) {
        super();
        this.userName = userName;
        this.time = time;
        this.address = address;
        this.videoName = videoName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "Statistics [id=" + id + ", userName=" + userName + ", videoName=" + videoName + ", time=" + time
                + ", address=" + address + "]";
    }



}
