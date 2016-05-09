package com.maliavin.vcp.form;

import com.maliavin.vcp.domain.Company;

/**
 * Stores required data for companies list response.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class CompanyForm {

    private Iterable<Company> content;

    public CompanyForm() {
        super();
    }

    public CompanyForm(Iterable<Company> content) {
        super();
        this.content = content;
    }

    public Iterable<Company> getContent() {
        return content;
    }

    public void setContent(Iterable<Company> content) {
        this.content = content;
    }

}
