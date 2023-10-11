package com.intranet.springboot.controller.Bean;

import lombok.Data;

import java.util.List;

@Data
public class LoginBean {
    private String username;
    private String provider;
    private String uid;
    private String firstName;
    private String surname;
    private String password;
    private String token;
    private boolean admin;
    private String deviceId;
    private List<String> permissions;
    private final boolean checkActivation = true;
}
