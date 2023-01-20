package com.intranet.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;

@SpringBootApplication(exclude={SolrAutoConfiguration.class})
@Slf4j
public class IntranetApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntranetApplication.class, args);
	}
}

