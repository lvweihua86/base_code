package com.hivescm.codeid;

import com.hivescm.codeid.service.CodeIdService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan("com.hivescm")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ServletComponentScan("com.hivescm")
@EnableEurekaClient
@EnableFeignClients(clients={CodeIdService.class})
public class CodeIdClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeIdClientApplication.class, args);
	}
}
