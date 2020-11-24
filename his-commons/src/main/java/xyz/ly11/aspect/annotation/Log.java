package xyz.ly11.aspect.annotation;

import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.aspect.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author 29794
 * @date 11/24/2020 21:15
 * 自定义的操作日志的注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;


}
