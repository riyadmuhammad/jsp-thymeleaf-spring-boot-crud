package com.ibcsprimax.hrm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
 * @EnableAutoConfiguration
 * 
 * @ComponentScan(basePackages={"com.erp.hrm"})
 * 
 * @EnableJpaRepositories(basePackages="com.erp.hrm.repository")
 * 
 * @EnableTransactionManagement
 * 
 * @EntityScan(basePackages="com.erp.hrm.entity")
 */

@SpringBootApplication
public class HrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmApplication.class, args);
	}

}
