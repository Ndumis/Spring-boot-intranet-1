package com.intranet.springboot.service;

import com.intranet.springboot.controller.Bean.CarBean;
import com.intranet.springboot.controller.Bean.CarsBean;
import com.intranet.springboot.controller.Bean.HourlyProgressReportMapper;
import com.intranet.springboot.model.HourlyProgressBean;
import com.intranet.springboot.model.TimeProgressBean;
import com.intranet.springboot.util.ObjectToListMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class HourlyService {

    public List<String[]> buildHourlyProgressReportHeader() {
        List<String[]> csvData = new ArrayList<>();
        String[] headers = new String[27];
        headers[0] = "FullName";
        headers[1] = "Date";
        for( int i = 2; i <= 26; i++) {
            if(i < 12)
                headers[i] = "0" + (i-2) + ":00";
            else
                headers[i] = (i-2) + ":00";
        }
        csvData.add(headers);
        return csvData;
    }

    public String[] buildHourlyProgressReportBody() {
        HourlyProgressBean hourlyProgressBean = new HourlyProgressBean();
        hourlyProgressBean.setFullName("Khaya SImelane");
        hourlyProgressBean.setDate(new Date());

        List<TimeProgressBean> timeProgressBeans = new ArrayList<TimeProgressBean>();

        for( int i =0; i <= 24; i++){
            int hour = i; //Hour
            long count = i*3;
            TimeProgressBean timeProgressBean = new TimeProgressBean();
            timeProgressBean.setValue(count);

            timeProgressBeans.add(timeProgressBean);
        }

        hourlyProgressBean.setTimeProgress(timeProgressBeans);

        String[] csvData = new String[1];
        ObjectToListMapper mapper = new HourlyProgressReportMapper();
        csvData = mapper.transform(hourlyProgressBean);
        return csvData;
    }

    public CarsBean findAll()
    {
        CarsBean carsList = new CarsBean();
        carsList.setId(UUID.randomUUID().toString());

        List<CarBean> cars = new ArrayList();
        cars.add(new CarBean(1, "Ford", "Fiesta"));
        cars.add(new CarBean(2, "Ford", "Mustang"));
        cars.add(new CarBean(3, "Ford", "GT40"));
        carsList.setCars(cars);

        return carsList;
    }

}
