package com.hivescm.codeid.job;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2017/7/10.
 */
@Configuration
public class JobEventConfig {

    @Autowired
    private DataSource dataSource;

    @Bean("jobEventConfiguration")
    public JobEventConfiguration createJobEventConfiguration(){
        return new JobEventRdbConfiguration(dataSource);
    }
}
