package com.intranet.springboot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class User implements UserDetails {
    public enum Role {Admin, ItManager, ItEmployee, HrManager, HrEmployee, User}
    public enum ACCOUNT_STATUS {ACTIVE, LOCKED, BLOCKED}
    @Id
    private  String id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String phone;
    private  String idNumber;
    private Date lastLoginDate;
    private int loginRetries;
    private ACCOUNT_STATUS accountStatus;
    private  String imageUrl;
    private  String password;
    private List<Role> roles;
    private Date createdOn;
    private Date LastDateModified;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map((role)-> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
