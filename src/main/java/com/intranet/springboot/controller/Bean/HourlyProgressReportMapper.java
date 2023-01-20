package com.intranet.springboot.controller.Bean;

import com.intranet.springboot.model.HourlyProgressBean;
import com.intranet.springboot.model.TimeProgressBean;

import com.intranet.springboot.util.ObjectToListMapper;

import java.util.ArrayList;
import java.util.List;

public class HourlyProgressReportMapper implements ObjectToListMapper {
    @Override
    public String[] transform(Object mappable) {
        HourlyProgressBean record = (HourlyProgressBean)mappable;
        List<String> list = new ArrayList<>();
        list.add(record.getFullName());
        list.add(String.valueOf(record.getDate()));
        for(TimeProgressBean bean:record.getTimeProgress()){ //TODO confirm this function needs to be sorted by...
            list.add(String.valueOf(bean.getValue()));
        }
        String[] myArray = new String[list.size()];
        return list.toArray(myArray);
    }
}
