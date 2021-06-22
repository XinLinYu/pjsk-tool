package com.projectsekai.thread;

import com.projectsekai.service.impl.FrequencyControlServiceImpl;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.concurrent.CountDownLatch;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PowerRemindThread
 * @description 体力小助手线程
 * @date 2021/6/21 0021 下午 4:15
 **/
public class PowerRemindThread implements Runnable {
    private CountDownLatch cdn;
    private GroupMessageEvent groupMessageEvent;
    private FrequencyControlServiceImpl frequencyControlService;

    public PowerRemindThread(CountDownLatch cdn, GroupMessageEvent groupMessageEvent, FrequencyControlServiceImpl frequencyControlService) {
        this.cdn = cdn;
        this.groupMessageEvent = groupMessageEvent;
        this.frequencyControlService = frequencyControlService;
    }

    @Override
    public void run() {
        frequencyControlService.powerRemindTask(groupMessageEvent);
        cdn.countDown();
    }
}
