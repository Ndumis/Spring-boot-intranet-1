package com.intranet.springboot.controller;

import com.intranet.springboot.repository.RoleRepository;
import com.intranet.springboot.model.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/role")
@RestController
@Slf4j
public class RoleController {
    private @Autowired RoleRepository roleRepository;

    @PostMapping("/register")
    public Role saveRole(@RequestBody Role role){
        //role.setId(UUID.randomUUID().toString());
        return roleRepository.save(role);
    }
    @GetMapping("/all")
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
}
