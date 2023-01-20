package com.intranet.springboot.controller;

import com.intranet.springboot.controller.Bean.AuthResponseBean;
import com.intranet.springboot.controller.Bean.LoginBean;
import com.intranet.springboot.model.domain.User;
import com.intranet.springboot.repository.UserRepository;
import com.intranet.springboot.security.JwtTokenGenerator;
import com.intranet.springboot.service.CustomUserDetails;
import com.intranet.springboot.service.HourlyService;
import com.intranet.springboot.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
@RestController
public class AuthController {
    private @Autowired AuthenticationManager authenticationManager;
    private @Autowired CustomUserDetails userDetails;
    private @Autowired UserRepository userRepository;
    private @Autowired JwtTokenGenerator jwtTokenGenerator;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        User userExist = userDetails.findUserByEmail(user.getEmail());
        if(userExist != null){
            return  new ResponseEntity<>("There is already a user registered with this username", HttpStatus.BAD_REQUEST);
        }else {
            userDetails.saveUser(user);
            return new ResponseEntity<>("User has been registered successfully", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseBean>login(@RequestBody LoginBean loginBean){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginBean.getUsername(),
                        loginBean.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseBean(token), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(){
        userRepository.deleteAll();
        return  new ResponseEntity<>("Users deleted successfully", HttpStatus.OK);
    }




    ////////////////TESTING//////////////////////////////
    private @Autowired HourlyService service;
    private @Autowired CsvUtil csvUtil;
    @GetMapping("/cases/download-hourly-progress/{date}")
    public void downloadHourlyProgressReport(@PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                             HttpServletResponse response) throws IOException {
        List<String[]> csvHeader = new ArrayList<>();
        csvHeader = service.buildHourlyProgressReportHeader();
        csvHeader.add(service.buildHourlyProgressReportBody());
        String content = csvUtil.convertList(csvHeader);
        csvUtil.fileResponse(response, content, "HourlyProgressReport", "text/csv");
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getCars(){
        return ResponseEntity.ok(service.findAll());
    }



    ////////////////TESTING//////////////////////////////
}
