package com.hivescm.code;

import com.hivescm.tsharding.config.EnableTSharding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan("com.hivescm")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableEurekaClient
@EnableSwagger2
@EnableTSharding(mapperPackage = "com.hivescm.code.mapper", configLocation = "classpath:mybatis-config.xml", mapperLocations
		= "classpath*:sqlmap/*Mapper.xml")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
