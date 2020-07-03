package com.sqltrans.hikari.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BeanRegisterUtils {

    /**
     * 向Spring容器中注入bean(构造器注入)
     * @param beanName bean名称
     * @param beanClass bean Class对象
     * @param constructorArgs 参数
     * @param <T>
     */
    public static <T> void registerBean(String beanName, Class<T> beanClass, Object ... constructorArgs) {
        if (Objects.isNull(beanClass)) {
            if (log.isDebugEnabled()) {
                log.debug("beanClass为空，无法注入:", beanName);
            }
            return;
        }

        //构建BeanDefinitionBuilder
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);

        //添加Bean对象构造函数的参数
        Optional.ofNullable(constructorArgs).ifPresent(argArr ->
                Arrays.stream(argArr).forEach(builder::addConstructorArgValue));

        //从builder中获取到BeanDefinition对象
        BeanDefinition beanDefinition = builder.getRawBeanDefinition();

        //获取spring容器中的IOC容器
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) SpringBeanTools.getApplicationContext().getAutowireCapableBeanFactory();

        //向IOC容器中注入bean对象
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public static <T> void removeBean(ConfigurableApplicationContext applicationContext, Class<T> cls){
        String name = cls.getSimpleName();
        name = name.replaceFirst(name.substring(0,1), name.substring(0,1).toLowerCase());
        removeBean(applicationContext, name);
    }

    public static void removeBean(ConfigurableApplicationContext applicationContext, String beanName){
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        beanFactory.removeBeanDefinition(beanName);
    }


}
