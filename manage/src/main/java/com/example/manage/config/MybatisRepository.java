package com.example.manage.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.config
 * @Description
 * @Date 2017/11/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MybatisRepository {
    String value() default "";
}
