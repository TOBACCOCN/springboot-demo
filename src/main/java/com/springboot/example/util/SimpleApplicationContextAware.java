package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SimpleApplicationContextAware.applicationContext = applicationContext;
        log.debug(">>>>> APPLICATION_CONTEXT_INJECTION_SUCCESS");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
