package com.projectsekai.handle;

import com.projectsekai.annotation.InstructionTarget;
import com.projectsekai.annotation.InstructionVal;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName InstructionHandle
 * @description 从参数到进入方法此过程的拦截器
 * @date 2021/6/18 0018 下午 2:33
 **/
public class InstructionHandle implements Consumer<GroupMessageEvent> {
    // 扫描的包
    private static Reflections reflections = new Reflections("com.projectsekai.controller");

    @Override
    public void accept(GroupMessageEvent groupMessageEvent) {
        // 获取携带InstructionTarget注解的Class
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(InstructionTarget.class);
        for (Class clazz : typesAnnotatedWith) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                // 获取携带InstructionVal注解的method
                if (method.isAnnotationPresent(InstructionVal.class)) {
                    InstructionVal annotation = method.getAnnotation(InstructionVal.class);
                    String[] value = annotation.value();
                    for (String val : value) {
                        // 判断注解的值与获得的信息是否一致
                        if (groupMessageEvent.getMessage().toString().contains(val)) {
                            try {
                                // 一致则进入方法
                                method.invoke(clazz.newInstance(), groupMessageEvent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}