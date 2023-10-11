package com.intranet.springboot.controller;

import com.intranet.springboot.controller.Bean.LoginBean;
import com.intranet.springboot.model.User;
import com.intranet.springboot.repository.AuthenticationRepository;
import com.intranet.springboot.repository.UserRepository;
import com.intranet.springboot.security.AuthToken;
import com.intranet.springboot.service.UserService;
import com.intranet.springboot.util.AuthTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
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
            user.setAccountStatus(User.ACCOUNT_STATUS.ACTIVE);
            User user1 =  userService.saveUser(user);
            if(user1 == null){
                return new ResponseEntity<>(user.getEmail() + " User has not been registered", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(user.getEmail() + " User has been registered successfully", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public LoginBean login(@RequestBody LoginBean loginBean, Principal principal, HttpServletRequest request){
        try {
            if (principal != null) {
                log.info("successfully logged in: [{}]", loginBean.getUsername());
                User user = userService.getUserByEmail(principal.getName());
                //Optional<AuthToken> token = AuthTokenUtil.getAuthToken(authenticationRepository.findByUserId(new ObjectId(user.getId())));
                //token.get().setOriginIp(request.getRemoteAddr());
                //authenticationRepository.save(token.get());
                request.getSession(false).setMaxInactiveInterval(sessionTimeout);
            }
        } catch (Throwable e) {
            log.error("failed to logout : " + e.getMessage(), e);
        }
        LoginBean bean = new LoginBean();
        return bean;
    }

    protected String getIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(Principal principal, HttpServletRequest request) {
        if (principal != null) {
            User user = userService.getUserByEmail(principal.getName());

            /**Optional<AuthToken> token = AuthTokenUtil.getAuthToken(authenticationRepository.findByUserId(new ObjectId(user.getId())));
            if (token.isPresent()) {
                token.get().setValid(false);
                authenticationRepository.delete(token.get());
            }*/
        }
        principal = null;
        request.getSession().removeAttribute("Authorized");
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();

    }

}
