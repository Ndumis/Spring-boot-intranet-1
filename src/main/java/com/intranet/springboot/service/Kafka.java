package com.intranet.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Kafka {
    private @Autowired KafkaTemplate<String,String> kafkaTemplate;
    private String kafkaTopic = "testTopic";
    private void send(String message){
        kafkaTemplate.send(kafkaTopic, message);
    }
}
