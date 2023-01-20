package com.intranet.springboot.controller.Bean;

import lombok.Data;

@Data
public class AuthResponseBean {
    private String accessToken;
    private String accessType = "Bearer ";

    public AuthResponseBean(String token){
        this.accessToken = token;
    }

}
