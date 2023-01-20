package com.intranet.springboot.controller;

import com.intranet.springboot.repository.UserRepository;
import com.intranet.springboot.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {
    private @Autowired UserRepository userRepository;

    @PostMapping()
    public User saveUser(@RequestBody User user){
        //user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }
    @GetMapping("/all")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
