package com.hivescm.code;

import com.hivescm.code.mapper.SeriaNumberMapper;
import com.hivescm.generated.api.GeneratedIncrApi;
import com.hivescm.tsharding.config.EnableTSharding;
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
@EnableFeignClients(clients={GeneratedIncrApi.class})
@EnableTSharding(mapperPackage = "com.hivescm.code.mapper",configLocation = "classpath:mybatis-config.xml",enhancedMappers= {SeriaNumberMapper.class})
public class CodeIDServerApplication{

        public static void main(String[] args) {
          SpringApplication.run(CodeIDServerApplication.class,args);
        }
}
