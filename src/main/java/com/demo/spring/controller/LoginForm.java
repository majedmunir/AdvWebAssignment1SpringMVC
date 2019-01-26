package com.demo.spring.controller;

import javax.validation.constraints.NotEmpty;

public class LoginForm {
    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    String accountname;
    @NotEmpty
    String password;

}
