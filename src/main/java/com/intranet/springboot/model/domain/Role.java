package com.intranet.springboot.model.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Role {
    @Id
    private String id;
    private String name;
}
