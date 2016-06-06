package com.maliavin.vcp.form;

import java.util.List;

/**
 * Stores required data for statistics list response.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class StatisticsForm {
    private List<StatisticRow> content;

    public StatisticsForm() {
        super();
    }

    public StatisticsForm(List<StatisticRow> content) {
        super();
        this.content = content;
    }

    public List<StatisticRow> getContent() {
        return content;
    }

    public void setContent(List<StatisticRow> content) {
        this.content = content;
    }

}
