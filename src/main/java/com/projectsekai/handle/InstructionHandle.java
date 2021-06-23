package com.projectsekai.handle;

import com.projectsekai.annotation.InstructionTarget;
import com.projectsekai.annotation.InstructionVal;
import com.projectsekai.service.impl.FrequencyControlServiceImpl;
import com.projectsekai.utils.RedisUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.springframework.util.StringUtils;

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

    public static MiraiLogger log = null;

    private static RedisUtil redisUtil = null;

    private static FrequencyControlServiceImpl frequencyControlService = null;

    private boolean powerRemindStarterFlag = false;

    public static FrequencyControlServiceImpl getFrequencyControlServiceInstance() {
        if (frequencyControlService == null) {
            synchronized (FrequencyControlServiceImpl.class) {
                if (frequencyControlService == null) {
                    frequencyControlService = new FrequencyControlServiceImpl();
                }
            }
        }
        return frequencyControlService;
    }

    public static RedisUtil getRedisUtilInstance() {
        if (redisUtil == null) {
            synchronized (RedisUtil.class) {
                if (redisUtil == null) {
                    redisUtil = new RedisUtil();
                }
            }
        }
        return redisUtil;
    }

    @Override
    public void accept(GroupMessageEvent groupMessageEvent) {
        log = groupMessageEvent.getBot().getLogger();
        if (!powerRemindStarterFlag) {
            powerRemindStarter(groupMessageEvent);
        }
        // 获取携带InstructionTarget注解的Class
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(InstructionTarget.class);
        for (Class clazz : typesAnnotatedWith) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                // 获取携带InstructionVal注解的method
                if (method.isAnnotationPresent(InstructionVal.class)) {
                    InstructionVal annotation = method.getAnnotation(InstructionVal.class);
                    String[] value = annotation.value();
                    String type = annotation.type();
                    // 如果自定义注解为空或者为静态则全判断，否则就进行模糊判断
                    if (StringUtils.isEmpty(type) || "static".equals(type)) {
                        for (String val : value) {
                            // 判断注解的值与获得的信息是否一致
                            String miraiCode = groupMessageEvent.getMessage().serializeToMiraiCode();
                            if (miraiCode.trim().equalsIgnoreCase(val)) {
                                try {
                                    // 一致则进入方法
                                    method.invoke(clazz.newInstance(), groupMessageEvent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        for (String val : value) {
                            // 判断注解的值与获得的信息是否一致
                            String miraiCode = groupMessageEvent.getMessage().serializeToMiraiCode();
                            if (miraiCode.trim().startsWith(val)) {
                                try {
                                    String parameter = miraiCode.replace(val, "");
                                    String[] parameterList = parameter.trim().split("\\s+");
                                    // 一致则进入方法
                                    method.invoke(clazz.newInstance(), groupMessageEvent, parameterList);
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

    private void powerRemindStarter(GroupMessageEvent groupMessageEvent) {
        powerRemindStarterFlag = true;
        redisUtil = getRedisUtilInstance();
        Boolean powerRemind = redisUtil.exists("pjsk_powerremind:isstart");
        log.info("是否已经开启体力小助手：" + powerRemind);
        frequencyControlService = getFrequencyControlServiceInstance();
        if (powerRemind) {
            log.info("体力小助手开始……");
            frequencyControlService.powerRemindTask(groupMessageEvent);
        }
    }
}