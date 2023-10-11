package com.intranet.springboot.security.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class LoginAttempt {
    @Id
    @NotNull
    private String id;
    @NotNull
    private Date created;
    @NotNull
    private String username;
    @NotNull
    private boolean authSuccessful;
    private boolean mfaSuccessful;
    private Date otpSent;
    private String otp;
    @Indexed
    private String key;
    private String comment;
}
