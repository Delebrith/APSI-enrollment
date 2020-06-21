package edu.pw.apsienrollment.common.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class JobSchedulerConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext,
                                                 DataSource dataSource) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        factoryBean.setJobFactory(jobFactory);
        factoryBean.setDataSource(dataSource);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        return factoryBean;
    }

    @Bean
    public Scheduler scheduler(ApplicationContext applicationContext, DataSource dataSource) throws SchedulerException {
        Scheduler scheduler = schedulerFactory(applicationContext, dataSource).getScheduler();
        scheduler.start();
        return scheduler;
    }
}
