package com.intranet.springboot.controller;

import com.intranet.springboot.controller.Bean.LoginBean;
import com.intranet.springboot.model.User;
import com.intranet.springboot.repository.AuthenticationRepository;
import com.intranet.springboot.repository.UserRepository;
import com.intranet.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController{
    private @Autowired AuthenticationManager authenticationManager;
    private @Autowired AuthenticationRepository authenticationRepository;
    private @Autowired UserRepository userRepository;
    private @Autowired UserService userService;
    @Value("${server.servlet.session.timeout}")
    private int sessionTimeout;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
         Optional<User> userExist = userRepository.findByEmail(user.getEmail());
        if(userExist.isPresent()){
            return  new ResponseEntity<>("There is already a user registered with this email " + user.getEmail(), HttpStatus.BAD_REQUEST);
        }else {
            User user1 =  userService.saveUser(user);
            if(user1 == null){
                return new ResponseEntity<>(user.getEmail() + " User has not been registered", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(user.getEmail() + " User has been registered successfully", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public LoginBean login(@RequestBody LoginBean loginBean, Principal principal, HttpServletRequest request){
        log.info("successfully logged in: [{}]", loginBean.getUsername());
        try {
            logout();
        } catch (Throwable e) {
            log.error("failed to logout : " + e.getMessage(), e);
        }
        LoginBean bean = new LoginBean();
        return bean;
    }

    protected String getIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    }

    @GetMapping(value = "/loggedin")
    public boolean loggedIn() {

        return true;

    }

    @GetMapping(value = "/roles")
    public User.Role[] getRoles() throws Exception {
        return User.Role.values();
    }

    @GetMapping(value = "/logout")
    public boolean logout() {

        return true;

    }

}
