package com.binhao.drive.config;/*
 * @Author: zeng
 * @Data: 2022/1/11 21:13
 * @Description: TODO
 */

import com.binhao.drive.common.handler.SpringAsyncExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;
import java.util.concurrent.*;

@Configuration
@EnableAsync
public class SpringAsyncConfiguration implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(SpringAsyncConfiguration.class);

    //配置线程池
    @Bean
    @Override
    public Executor getAsyncExecutor() {

    AbstractExecutorService abstractExecutorService =new ThreadPoolExecutor(
            2,
            4,
            10L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(4),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
            //策略  当队列已满再来一个线程 不处理 并抛出异常
                );
        return abstractExecutorService;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

}
