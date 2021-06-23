package com.projectsekai.annotation;

import java.lang.annotation.*;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName InstructionVal
 * @description 自定义注解——指令参数
 * @date 2021/6/18 0018 下午 2:17
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InstructionVal {
    String[] value();

    //初始优先级为0 数值越大优先级越高 会越先执行
    int priority() default 0;

    String type() default "static";
}
