package com.polar.android_seed.models.data;

public class LoginParameters {

    public String username;
    public String password;

    public LoginParameters(String nickname, String password) {
        this.username = nickname;
        this.password = password;
    }
}