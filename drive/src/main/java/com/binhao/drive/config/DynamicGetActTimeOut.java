package com.binhao.drive.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.binhao.drive.common.service.CacheService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class DynamicGetActTimeOut implements SchedulingConfigurer {

    @Resource
    private CacheService cacheService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                    this.cacheService.cleanTimeout();
                    System.out.println("每分钟清理一次过期缓存session");
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    return new CronTrigger(" 0 0/1 * * * ?").nextExecutionTime(triggerContext);
                }
        );
    }
}
