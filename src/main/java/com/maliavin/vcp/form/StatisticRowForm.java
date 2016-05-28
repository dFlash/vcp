package com.maliavin.vcp.form;

public class StatisticRowForm {
    
    private String videoName;
    
    private Integer viewCount;
    
    private Integer users;
    
    private Integer addresses;

    public StatisticRowForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StatisticRowForm(String videoName, Integer viewCount, Integer users, Integer addresses) {
        super();
        this.videoName = videoName;
        this.viewCount = viewCount;
        this.users = users;
        this.addresses = addresses;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getAddresses() {
        return addresses;
    }

    public void setAddresses(Integer addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "StatisticRowForm [videoName=" + videoName + ", viewCount=" + viewCount + ", users=" + users
                + ", addresses=" + addresses + "]";
    }

}
