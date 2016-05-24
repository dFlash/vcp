package com.maliavin.vcp.form;

import java.util.List;

import com.maliavin.vcp.domain.Statistics;

/**
 * Stores required data for statistics list response.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class StatisticsForm {
    private List<Statistics> content;

    public StatisticsForm() {
        super();
    }

    public StatisticsForm(List<Statistics> content) {
        super();
        this.content = content;
    }

    public List<Statistics> getContent() {
        return content;
    }

    public void setContent(List<Statistics> content) {
        this.content = content;
    }

}
