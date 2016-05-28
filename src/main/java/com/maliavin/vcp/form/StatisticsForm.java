package com.maliavin.vcp.form;

import java.util.List;

/**
 * Stores required data for statistics list response.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class StatisticsForm {
    private List<StatisticRowForm> content;

    public StatisticsForm() {
        super();
    }

    public StatisticsForm(List<StatisticRowForm> content) {
        super();
        this.content = content;
    }

    public List<StatisticRowForm> getContent() {
        return content;
    }

    public void setContent(List<StatisticRowForm> content) {
        this.content = content;
    }

}
