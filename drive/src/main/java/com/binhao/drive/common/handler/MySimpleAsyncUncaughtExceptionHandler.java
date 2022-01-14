package com.binhao.drive.common.handler;/*
 * @Author: zeng
 * @Data: 2022/1/11 21:43
 * @Description: TODO
 */

import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MySimpleAsyncUncaughtExceptionHandler extends SimpleAsyncUncaughtExceptionHandler {

    public MySimpleAsyncUncaughtExceptionHandler() {
        super();
    }

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("进入无返回值异常处理-------------------------------------MySimpleAsyncUncaughtExceptionHandler");
        super.handleUncaughtException(ex, method, params);
    }
}
