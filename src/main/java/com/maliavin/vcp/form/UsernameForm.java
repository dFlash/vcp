package com.maliavin.vcp.form;

/**
 * Stores required data for retrieving user name
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class UsernameForm {
    
    private String username;

    public UsernameForm() {
        super();
    }

    public UsernameForm(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

}
