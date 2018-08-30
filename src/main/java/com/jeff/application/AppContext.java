package com.jeff.application;

import com.jeff.algorithm.DataCollator;
import com.jeff.algorithm.DataProcessor;
import com.jeff.clients.email.EmailClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.concurrent.Executor;

@Configuration
public class AppContext extends WebMvcConfigurationSupport {

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public DataCollator dataCollator() {
        return new DataCollator();
    }

    @Bean
    public DataProcessor dataProcessor() {
        return new DataProcessor();
    }

    @Bean
    public EmailClient emailClient() {
        return new EmailClient();
    }

}