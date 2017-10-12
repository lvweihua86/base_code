package com.hivescm.codeid.job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/7/10.
 */
@Configuration
public class MultiJobStart {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    @Bean("seriaNumberJob")
    public SeriaNumberMapperWriteToDatabaseJob seriaNumberJob() {
        return new SeriaNumberMapperWriteToDatabaseJob();
    }

    private LiteJobConfiguration getLiteJobConfiguration(String cron, int
            shardingTotalCount,Class clazz) {
        return LiteJobConfiguration.newBuilder(
                new SimpleJobConfiguration(
                        JobCoreConfiguration.newBuilder(
                                clazz.getName(), cron,
                                shardingTotalCount).build(),
                                clazz.getCanonicalName()))
                .overwrite(true).build();
    }

    @Bean(initMethod = "init",name ="seriaNumberJobScheduler")
    public JobScheduler createSeriaNumberJobScheduler(final
                                                      SeriaNumberMapperWriteToDatabaseJob
                                                              seriaNumberMapperWriteToDatabaseJob,
                                                      @Value("${seriaNumberJob.cron}") String cron,
                                                      @Value("${seriaNumberJob.shardingTotalCount}") int
                                                              shardingTotalCount) {

        return new SpringJobScheduler(seriaNumberMapperWriteToDatabaseJob,
                zookeeperRegistryCenter,
                getLiteJobConfiguration(cron, shardingTotalCount,
                        SeriaNumberMapperWriteToDatabaseJob.class),jobEventConfiguration);
    }

}
