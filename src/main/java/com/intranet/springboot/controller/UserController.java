package com.intranet.springboot.controller;

import com.intranet.springboot.model.User;
import com.intranet.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {
    private @Autowired UserService userService;

    @GetMapping("")
    public User getUsers(Principal principal){
        log.info("Get user..");
        if (principal != null) {
            return userService.getUserByEmail(principal.getName());
        }
        return new User();
    }
    @GetMapping("/all")
    public List<User> getUsers(){
        log.info("Get all users");
        return userService.getUsers().getContent();
    }

    @PostMapping("/by-id/{id}")
    public User getUserById(@PathVariable("id") String id){
        log.info("Get user by id " + id);
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public User saveUser(@RequestBody User user){
        user.setCreatedOn(new Date());
        user.setLastDateModified(new Date());
        return userService.saveUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        log.info("Updating user " + user);
        user.setLastDateModified(new Date());
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") String id){
        log.info("Deleting user by id" + id);
        userService.deleteUserById(id);
        return  new ResponseEntity<>("Users deleted successfully", HttpStatus.OK);
    }


}
