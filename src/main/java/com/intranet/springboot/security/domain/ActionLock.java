package com.intranet.springboot.security.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class ActionLock {
    public enum Type {OTP, LOGIN_ATTEMPT, RECOVER_ACCOUNT, OTP_RETRY, DHA_CALLS}

    private @Id String id;
    private String idNumber;
    private Type type;
    private Date created;
}
