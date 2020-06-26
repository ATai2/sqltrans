package com.sqltrans.hikari.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SpringBeanTools implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    //注入ApplicationContext
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(SpringBeanTools.applicationContext)) {
            SpringBeanTools.applicationContext = applicationContext;
        }
    }

    /**
     * 获取指定的bean对象
     *
     * @param name  bean名称
     * @param clazz 类class对象
     * @param <T>
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return clazz.cast(applicationContext.getBean(name));
    }

    /**
     * 获取bean对象实例
     *
     * @param clazz 类class对象
     * @param <T>
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取bean对象实例
     *
     * @param name 实例名称
     * @return
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取 applicationContext 的值
     *
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
