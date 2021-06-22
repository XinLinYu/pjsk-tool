package com.projectsekai.service;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName FrequencyControlService
 * @Description
 * @date 2021/6/21 0021 上午 10:47
 **/
public interface FrequencyControlService {
    /**
     * @param instruction
     * @param expire
     * @return void
     * @Author XinlindeYu
     * @description 命令频率控制
     * @Date 上午 10:50 2021/6/21 0021
     **/
    void instructionFrequencyControl(String instruction, int expire, GroupMessageEvent groupMessageEvent, String message);

    /**
     * @param id
     * @param groupMessageEvent
     * @return void
     * @Author XinlindeYu
     * @description 开启 体力小助手
     * @Date 下午 3:25 2021/6/21 0021
     **/
    void powerRemind(long id, GroupMessageEvent groupMessageEvent);

    /**
     * @param id
     * @param groupMessageEvent
     * @return void
     * @Author XinlindeYu
     * @description 关闭 体力小助手
     * @Date 下午 3:25 2021/6/21 0021
     **/
    void powerRemindStop(long id, GroupMessageEvent groupMessageEvent);
}
