package com.intranet.springboot.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class HourlyProgressBean {
    private String userId;
    private String fullName;
    private Date date;
    private List<TimeProgressBean> timeProgress = new ArrayList<TimeProgressBean>();
}
