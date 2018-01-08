package com.hivescm.code;

import com.hivescm.common.listener.SpringBootAdminListener;
import com.hivescm.common.listener.SpringBootPreparedEventListener;
import com.hivescm.common.listener.SpringBootStartedEventListener;
import com.hivescm.tsharding.config.EnableTSharding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.hivescm")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableSwagger2
@EnableTSharding(mapperPackage = "com.hivescm.code.mapper", configLocation = "classpath:mybatis-config.xml",
        mapperLocations = "classpath*:sqlmap/*Mapper.xml")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new SpringBootStartedEventListener());
        app.addListeners(new SpringBootPreparedEventListener());
        app.addListeners(new SpringBootAdminListener());
        app.run(args);
    }
}
