package com.intranet.springboot.security;

import com.intranet.springboot.model.User;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class AuthToken {
    @Indexed
    private String id;
    private Date date;
    @DBRef
    private User user;
    private String originIp;
    private boolean valid;
    private User.Role role;
    private List<User.Role> roles;
    private List<String> permissions = new LinkedList<String>();
}
