package com.hivescm.codeid.job;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/7/10.
 */
@Configuration
public class RegistryCenterConfig {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter  createZookeeperRegistryCenter(@Value("${regCenter.serverList}") final String serverLists, @Value("${regCenter.namespace}") final String namespace){
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration
                (serverLists,namespace);
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}
