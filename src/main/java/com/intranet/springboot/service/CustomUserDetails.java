package com.intranet.springboot.service;

import com.intranet.springboot.model.domain.User;
import com.intranet.springboot.model.domain.Role;
import com.intranet.springboot.repository.RoleRepository;
import com.intranet.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetails implements UserDetailsService {
    private @Autowired UserRepository userRepository;
    private @Autowired RoleRepository roleRepository;
    private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public User saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        user.setLastDateModified(new Date());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }else
            throw  new UsernameNotFoundException("Username not found");
    }

    private  List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return grantedAuthorities;
    }

    private  UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
