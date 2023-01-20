package com.intranet.springboot.model.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.Set;

@Data
public class User {
    @Id
    private  String Id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;

    @DBRef
    private Set<Role> roles;
    private Date LastDateModified;
}
