package com.intranet.springboot.service;

import com.intranet.springboot.config.ApplicationConfig;
import com.intranet.springboot.exception.UserNotFoundException;
import com.intranet.springboot.model.User;
import com.intranet.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private @Autowired UserRepository userRepository;
    private @Autowired ApplicationConfig applicationConfig;

    public User saveUser(User user){
        if(!user.getPassword().isEmpty()) {
            user.setPassword(applicationConfig.passwordEncoder().encode(user.getPassword()));
        }
        user.setCreatedOn(new Date());
        return userRepository.save(user);
    }
    public Page<User> getUsers(){
        return userRepository.findAll(PageRequest.of(0, 10));
    }

    public User getUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User by id "+ id + " was not found"));
    }

    public String deleteUserById(String id){
        userRepository.deleteById(id);
        return "Users deleted successfully";
    }
}
